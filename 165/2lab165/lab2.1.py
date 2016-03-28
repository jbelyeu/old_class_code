#! /usr/bin/env python

import sys
import re

inFasta = sys.argv[1]

#part 1 Find complement. Output to fasta
inFile = open(inFasta, 'r')
outFile = open('outOne.fasta', 'w')
line = inFile.readline()
while line != "":
	line = line.strip()
	line = line.upper()
	if line[0] == ">":
		outFile.write(line+'\n')
	else:
		for i in range(0, len(line)):
			llist = list(line)
			if llist[i] == 'A':	
				llist[i] = 'T'
			elif llist[i] == 'T':
				llist[i] = 'A'
			elif llist[i] == 'G':
				llist[i] = 'C'
			elif llist[i] == 'C':
				llist[i] = 'G'
			else:
				print 'There is problem', llist[i]
	 		llist.reverse()
			line = ''.join(llist)
		outFile.write(line+'\n')
	line = inFile.read()
	

inFile.close()
outFile.close()

#part 2 convert to RNA. Output to fasta, don't forget title line
inFile2 = open('outOne.fasta', 'r')
outFile2 = open('outTwo.fasta', 'w')

line2 = inFile2.readline()
while line2 != "":
	line2 = line2.strip()
	line2 = line2.upper()
	if line2[0] == ">":
		outFile2.write(line2+'\n')
	else:
		reg = r"\T"
		rep = r"U"
		line2 = re.sub(reg, rep, line2)
		outFile2.write(line2 + '\n')

	line2 = inFile2.readline()

inFile2.close()
outFile2.close()

