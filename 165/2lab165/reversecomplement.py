#! /usr/bin/env python

import sys
import re

inFasta = sys.argv[1]
outFasta = sys.argv[2]

#part 1 Find complement. Output to fasta
inFile = open(inFasta, 'r')
outFile = open(outFasta, 'w')
line = inFile.readline()
while line != "":
	line = line.strip()
	temp = ""
	i = (len(line))
	if line[0] == ">":
		outFile.write(line+'\n')
	else:              
		line = line.upper()
#		for c in line:
		while i > 0:
			i -=1
			if line[i] == 'A':	
				temp = temp+"T"
			elif line[i]== 'T':
				temp = temp+"A"
			elif line[i] == 'G':
				temp = temp+"C"
			elif line[i]== 'C':
				temp = temp+"G"
			else:
				temp2=""
				print "hello"
				print 'There is problem', line[i]
		line = "".join(temp)
		outFile.write(line+'\n')
	line = inFile.readline()
	

inFile.close()
outFile.close()
