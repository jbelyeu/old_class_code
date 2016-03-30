#! /usr/bin/env python
import os
import sys
import re

rootdir = "/users/guest/j/jbelyeu/public_html/360/3lab360"
apachedir = rootdir + "/apachePerf"
mydir = rootdir + "/myPerf"

apache = open("myResults.csv", "w")
currPath = apache
threadNum = ""
for subdir, dirs, files in os.walk(mydir):
	ending = subdir.split("/")[-1]
	if re.search("thread", ending):
		threadNum = ending
	for ifile in files:
		fullpath = os.path.join(subdir, ifile)
		infile = open(fullpath)
		apache.write("Threads: " + threadNum + "\n") 
		for line in infile:
			linearray = line.split(" ")
			if linearray[0] == "Document" and linearray[1] == "Path:":
#				apache.write(linearray[2] + "\n")
				apache.write(line)
			if linearray[0] == "Concurrency":
#				apache.write(linearray[2] + "\n")
				apache.write(line)
			if linearray[0] == "Time" and linearray[1] == "taken":
#				apache.write(linearray[4] + " " + linearray[5] + "\n")
				apache.write(line)
			if linearray[0] == "Failed":
#				apache.write(linearray[2] + "\n")
				apache.write(line)
			if linearray[0] == "Requests":
				apache.write(line)
			if linearray[0] == "Time" and linearray[1] == "per":
				apache.write(line)
		apache.write("\n\n")

			
