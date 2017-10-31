import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;
public class Insight {

private static HashMap<String,Val_sum> hm=new HashMap<>();
private static TreeMap<String,TreeMap<Date,Val_sum>> tm=new TreeMap<>();

public static void main(String [] args) throws IOException, ParseException
{	
	FileInputStream fstream = new FileInputStream(args[0]);
	File file = new File(args[1]);
	PrintWriter printWriter = new PrintWriter(file);
	File file1 = new File(args[2]);
	PrintWriter printWriter1 = new PrintWriter(file1);
	BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
	String strLine;
	while((strLine=br.readLine())!=null)
	{	String line[]=strLine.split("\\|");
	 	if(line[15].equals("") && line[10].length()>=5 && line[10].substring(0,5).matches("\\d+"))
	 	{
	 		fill_zip(line,printWriter); 
	 	}
	 	if(line[15].equals("") && !line[13].equals("") && line[13].matches("\\d+") && Validatedate(line[13]))
	 	{
	 		get_date(line);
	 	}
	}
    fill_date(printWriter1);	
	br.close();
	printWriter.close();
	printWriter1.close();
}
//Date Validator
private static boolean Validatedate(String date) {
	// TODO Auto-generated method stub
	if(date.length()!=8)
		return false;
	int month=Integer.parseInt(date.substring(0,2));
	int day=Integer.parseInt(date.substring(2,4));
	int year=Integer.parseInt(date.substring(4,8));
	if(month<1 || month>12 || day<1 || day>31 || (year<1900 || year >=2018))
		return false;
	if((month==4 || month==6 || month==9 || month==11) && day>30)
		return false;
	if(month==2)
	{
		if(year%4!=0 && day>=29)
			return false;
		if(((year%4==0 && year%100!=0) || (year%400==0)) && (day>29) )
			return false;
	}
	
	return true;
}
// Write medianbydate to file
private static void fill_date(PrintWriter printWriter1) {
	// TODO Auto-generated method stub
	DateFormat df = new SimpleDateFormat("MMddyyyy");
	for (String id:tm.keySet())
	{	TreeMap<Date,Val_sum> t1=tm.get(id);
			for (Date date:t1.keySet())
			{
				String new_date=df.format(date);
				Val_sum t2=t1.get(date);
			                                           // id-date-median-count-sum
				StringBuilder sb=new StringBuilder();
				sb.append(id);
				sb.append("|");
				sb.append(new_date);
				sb.append("|");
				sb.append(get_median(t2.get_list()));
				sb.append("|");
				sb.append(t2.get_list().size());
				sb.append("|");
				sb.append(t2.get_sum());
				printWriter1.println(sb.toString());
		}
	}
}
// parsing medianvals by date
private static void get_date(String[] line) throws ParseException {
	// TODO Auto-generated method stub      #0,10-zipcode,13-data,14-amount,15
	DateFormat df = new SimpleDateFormat("MMddyyyy");
	Date date = (Date) df.parse(line[13]);
	if(tm.containsKey(line[0]))
	{	TreeMap<Date,Val_sum> temp=tm.get(line[0]);
		if(temp.containsKey(date))
		{
			temp.get(date).put_value(Integer.parseInt(line[14]));
		}
		else
		{
			Val_sum z=new Val_sum();
			z.put_value(Integer.parseInt(line[14]));
			temp.put(date,z);
			tm.put(line[0],temp);
		}
	}
	else
	{	TreeMap<Date,Val_sum> temp1=new TreeMap<>();
		Val_sum z=new Val_sum();
		z.put_value(Integer.parseInt(line[14]));
		temp1.put(date, z);
		tm.put(line[0],temp1);
	}		
}
// To process medianvals by  zip codes
private static void fill_zip(String[] line,PrintWriter printWriter) {  
	// TODO Auto-generated method stub
	String record=line[0]+","+line[10].substring(0,5);
	if(hm.containsKey(record))
	{
		hm.get(record).put_value(Integer.parseInt(line[14]));
	}
	else
	{   Val_sum z=new Val_sum();
		z.put_value(Integer.parseInt(line[14]));
		hm.put(record, z);
	}
	Val_sum value=hm.get(record);
	StringBuilder sb=new StringBuilder();
	sb.append(line[0]);      
	sb.append("|");
	sb.append(line[10].substring(0,5));       
	sb.append("|");
	sb.append(get_median(value.get_list()));
	sb.append("|");
	sb.append(value.get_list().size());
	sb.append("|");
	sb.append(value.get_sum());	
	printWriter.println(sb.toString());
}
//get median for the input list
private static String get_median(ArrayList<Integer> ar) {
	// TODO Auto-generated method stub
	long median=0;
	Collections.sort(ar);
	if(ar.size()%2==0)
	{
		median=Math.round((double)(ar.get(ar.size()/2)+ar.get((ar.size()/2)-1))/2.0);
	}
	else
		median=ar.get(ar.size()/2);	
	return String.valueOf(median).split("\\.")[0];
}		
}
// class definition to maintain running sum and 
class Val_sum{
	ArrayList<Integer> ar;
	int sum;
	public Val_sum()
	{
		ar=new ArrayList<>();
		sum=0;
	}
	void put_value(int value)
	{
		ar.add(value);
		sum+=value;
	}
	int get_sum()
	{
		return this.sum;	
	}
	ArrayList<Integer> get_list()
	{
		return ar;
	}	
}
