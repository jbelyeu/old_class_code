#! /usr/bin/env python
import sys

infile = open(sys.argv[1])
textStr = infile.readline()
num = int(infile.readline())
infile.close()

maxFreq = 0
kmersDict = {}
for i in range (0, len(textStr) - num):
	temp = textStr[i:i+num]
	if temp in kmersDict:
		kmersDict[temp] += 1
	else:
		kmersDict[temp] = 1
	if kmersDict[temp] > maxFreq:
		maxFreq = kmersDict[temp]

sepVals = []
for kmer in kmersDict:
	if kmersDict[kmer] == maxFreq:
		sepVals.append(kmer)
print " ".join(sepVals)
