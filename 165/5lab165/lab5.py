#! /usr/bin/env python

import sys
import re

def checkArgs(argList):
    commands = ["-g", "-r", "-s", "-t","-c"]
    if len(argList) != 4:
        print("Error: USAGE: python lab5.py: option input.fasta output.txt\nAvailable options:")
        print("-g GC%\n-r reverse complement\n-s transcription\n-t translation\n-c count nucleotides")
        sys.exit()
    elif argList[1] not in commands:
        print("Error: USAGE: python lab5.py: option input.fasta output.txt\nAvailable options:")
        print("-g GC%\n-r reverse complement\n-s transcription\n-t translation\n-c count nucleotides")
        sys.exit()
    return

def checkNucs(line):
    line = line.replace(" ", "")
    line = line.upper()
    if (re.search('[^AGCTagct]', line)):
        line = "ERROR"
    return line    

def GCPercent(argList):
    ifileName = argList[2]
    inFile = open(ifileName, "r")
    ofileName = argList[3]
    outFile = open(ofileName, "w")
    outFile.write("ID\tGC%\n")
    for line in inFile:
        line = line.strip()
        if line[0] == ">":
            line = line.replace(">", "") + "\t"
            outFile.write(line)
        else:   
            line = checkNucs(line)
            if  line != "ERROR":
                gcPercent = float(line.count('G') +line.count('c')+ line.count('g')+ line.count('C'))/len(line)*100
                outFile.write("%.1f" % gcPercent + "%\n")
            else:
                outFile.write(line+"\n")
    inFile.close()
    outFile.close()
    return

def reverseComp(argList):
    ifileName = argList[2]
    inFile = open(ifileName, "r")
    ofileName = argList[3]
    outFile = open(ofileName, "w")
    complimentMap = {"A":"T", 'a': 't', 't':'a', 'T':'A', 'G':'C', 'g':'c', 'C':'G', 'c':'g'}
    for line in inFile:
        line = line.strip()
        if line[0] == ">":
            outFile.write(line+'\n')
        else:
            temp = ""
            line = checkNucs(line)
            if line == "ERROR":
                outFile.write(line+'\n')
            else:
                i = len(line)
                while i > 0:
                    i -=1
                    temp += complimentMap[line[i]]
                line = "".join(temp)
                line = line.upper()
                outFile.write(line+'\n')
    inFile.close()
    outFile.close()
    return

def transcription(argList):
    ifileName = argList[2]
    inFile = open(ifileName, "r")
    ofileName = argList[3]
    outFile = open(ofileName, "w")
    line = inFile.readline()
    while line != "":
	line = line.strip()
	if line[0] == ">":
		outFile.write(line+'\n')
	else:
                line = checkNucs(line)
                if (line == "ERROR"):
                    outFile.write(line+'\n')
                else:
                    reg = r"\T"
                    rep = r"u"
                    line = re.sub(reg, rep, line)
                    reg = r"t"
                    rep = r"u"
                    line = re.sub(reg, rep, line)
                    line = line.upper()
                    outFile.write(line + '\n')
	line = inFile.readline()
    inFile.close()
    outFile.close()
    return

def mapIt():
    codonDict = {}#create dictionary
    infile = open("codons.txt", 'r')#open codon file for reading
    for line in infile: #for loop grabs lines from codon file
        line = line.strip()
        line = line.replace("U", "T")
        fields = line.split()   #breaks the lines up into list of words
        codonDict[fields[0]] = fields[1] #first word is nucleotide sequence, used as key, second is protein, value
    infile.close()
    return codonDict

def translate(argList):
    codonDict = mapIt()
    ifileName = argList[2]
    inFile = open(ifileName, "r")
    ofileName = argList[3]
    outFile = open(ofileName, "w")
    for line in inFile:
        if line[0] == ">":
            outFile.write(line)
        else:
            line = line.strip()
            line = checkNucs(line)
            if (line == "ERROR"):
                outFile.write(line+"\n")
            else:
                line = line.upper()
                start = 0
                end = 3
                aminoA = ''
                while end <= len(line):
                    TriBase = line[start:end]
                    if TriBase in codonDict:
                        letter = codonDict[TriBase]
                        if letter != "*":
                            aminoA += letter
                        else:
                                end = len(line)+1
                    end += 3
                    start += 3
                aminoA += "\n"
                outFile.write(aminoA)
    outFile.close()
    inFile.close()
    return

def countNucs(argList):
    ifileName = argList[2]
    inFile = open(ifileName, "r")
    ofileName = argList[3]
    outFile = open(ofileName, "w")
    outFile.write("ID\tLength\tA(%A)\tT(%T)\tG(%G)\tC(%C)\n")
    line = ''
    for line in inFile:
        line = line.strip()
        if (line[0] == ">"):
            line = re.sub(">", "", line)
            outFile.write(line+"\t")
        else:
            line = line.upper()
            line = checkNucs(line)
            if line == "ERROR":
                outFile.write("ERROR\n")
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
                outFile.write(str(nucs) +'\t'+str(A)+'('+ (a)+'%)'+'\t'+str(T))
                outFile.write('('+(t)+ '%)\t'+ str(G)+'('+(g)+'%)\t'+ str(C)+ '('+ (c)+ '%)\n')
    outFile.close()
    inFile.close()
    return
            

#main function
checkArgs(sys.argv)
if sys.argv[1] == "-g":
    #call find GC% function
    GCPercent(sys.argv)
elif sys.argv[1] == "-r":
    #call reverse compliment function
    reverseComp(sys.argv)
elif sys.argv[1] == "-s":
    #call transcription function
    transcription(sys.argv)
elif sys.argv[1] == "-t":
    #call translation function
    translate(sys.argv)
elif sys.argv[1] == "-c":
    #call count nucleotides function
    countNucs(sys.argv)
