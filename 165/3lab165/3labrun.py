#! /usr/bin/env python

import sys
import re

def mapIt(inCodons):
        codonDict = {}#create dictionary
        infile = open(inCodons, 'r')#open codon file for reading
        for line in infile: #for loop grabs lines from codon file
                line = line.strip()
                line = line.replace("U", "T")
                fields = line.split()   #breaks the lines up into list of words
                codonDict[fields[0]] = fields[1] #first word is nucleotide sequence, used as key, second is protein, value
        infile.close()
        return codonDict


#main function
inCodons = sys.argv[1]
codons = mapIt(inCodons)
inFasta = sys.argv[2] #the fasta file
infile = open(inFasta, 'r') #open the fasta
oFasta = sys.argv[3] #the output
oFile = open(oFasta, 'w') #open the outfile
for line in infile:
        if line[0] == ">":
                oFile.write(line)
        else:
                line = line.upper()
                fields = line.split()
                line = "".join(fields)
                for char in line:
                        if (char != 'A' and char != "G" and char != "T" and char != "C"):
                                line = "ERROR\n"
                if line == "ERROR\n":
                    oFile.write(line)
                else:
                     start = 0
                     end = 3
                     aminoA = ''
                     while end <= len(line):
                             TriBase = line[start:end]
                             if TriBase in codons:
                                     letter = codons[TriBase]
                                     if letter != "*":
                                         aminoA += letter
                                     else:
                                         end = len(line)+1
                             end += 3
                             start += 3
                     aminoA += "\n"
                     oFile.write(aminoA)
infile.close()
oFile.close()
