#! /usr/bin/env python

import sys
import re

# main function
if len(sys.argv) != 3:
    print "Thou shalt not input any number of command line args except it be three, and three shall be the number thereof. Thou shalt not input four, and thou shalt not input two unless thou then inputtest another, for a total of three. And these shall include the .py, the .fasta, and the .txt. No more or less shalt thou include."
    sys.exit()
argIn = sys.argv[1]
argOut = sys.argv[2]
fileIn = open(argIn, 'r')
fileOut = open(argOut, 'w')
fileOut.write("ID\tLength\tA(%A)\tT(%T)\tG(%G)\tC(%C)")
line = ''
for line in fileIn:
    line = line.strip()
    if (line[0] == ">"):
        line = re.sub(">", "", line)
        fileOut.write("\n"+line+'\t')
    else:  
        line = line.upper()
        fields = line.split()
        line = "".join(fields)
        for char in line:
            if (char != 'A' and char != "G" and char != "T" and char != "C"):
                line = "ERROR\n"
        if line == "ERROR\n":
            fileOut.write("ERROR")
        else:
            nucs = 0
            A = 0
            C = 0
            G = 0
            T = 0
            for char in line:
                nucs += 1
                if char == "A":
                    A += 1
                elif char == "C":
                    C += 1
                elif char == "G":
                    G += 1
                elif char == "T":
                    T += 1
            a = str(100*A/nucs)
	    c = str(100*C/nucs)
	    g = str(100*G/nucs)
	    t = str(100*T/nucs)
	    fileOut.write(str(nucs) +'\t'+str(A)+'('+ (a)+'%)'+'\t'+str(T))
            fileOut.write('('+(t)+ '%)\t'+ str(G)+'('+(g)+'%)\t'+ str(C)+ '('+ (c)+ '%)')
fileOut.write('\n')
fileOut.close()
fileIn.close()
