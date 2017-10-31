 # Program to extract median values by zip and median values by date from lists campaign contributions by individual donors and distill into two output files.


**Description**
```
Program takes input file and emits two files:

    1. medianvals_by_zip.txt:contains a calculated running median, total dollar amount and total number of contributions by recipient and zip code

    2.medianvals_by_date.txt: has the calculated median, total dollar amount and total number of contributions by recipient and date.
```
**Input file format**

```
The input file is pipe delimited similar to the format shown below.

C00629618|N|TER|P|201701230300133512|15C|IND|PEREZ, JOHN A|LOS ANGELES|CA|90017|PRINCIPAL|DOUBLE NICKEL ADVISORS|01032017|40|H6CA34245|SA01251735122|1141239|||2012520171368850783 

Input data is available at [source](http://classic.fec.gov/finance/disclosure/ftpdet.shtml)

sample input file is present in ./input folder - Sample file itcont.txt 
```
**Output file format**
```
Output files are emitted to ./output folder by the names medianvals_by_zip.txt and medianvals_by_date.txt
```

**Compiling and running Program**

```
javac ./src/*.java
java -cp ./src Insight ./input/itcont.txt ./output/medianvals_by_zip.txt ./output/medianvals_by_date.txt
This is included in ./run.sh
```
