#! /usr/bin/env python

import sys
import re


inFasta = sys.argv[1]
outFasta = sys.argv[2]

#part 2 convert to RNA. Output to fasta, don't forget title line
inFile2 = open(inFasta, 'r')
outFile2 = open(outFasta, 'w')

line2 = inFile2.readline()
while line2 != "":
	line2 = line2.strip()
	if line2[0] == ">":
		outFile2.write(line2+'\n')
	else:		
		line2 = line2.upper()
		reg = r"\T"
		rep = r"U"
		line2 = re.sub(reg, rep, line2)
		outFile2.write(line2 + '\n')

	line2 = inFile2.readline()

inFile2.close()
outFile2.close()


