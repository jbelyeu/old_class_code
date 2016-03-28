#! /usr/bin/env python
import sys
import re

sequ1 = raw_input("Type first sequence: ")
sequ1 = sequ1.upper()
if re.search('[^AGTC ]',sequ1) or sequ1 == "":
	print "invalid"
	sys.exit()
sequ2 = raw_input("Type second sequence: ")
sequ2 = sequ2.upper()
if (re.search('[^AGT C]', sequ2)) or sequ2 == "":
	print "invalid"
	sys.exit()
sequ3 = raw_input("Type third sequence: ")
sequ3 = sequ3.upper()
if (re.search('[^AGT C]', sequ3)) or sequ3 == "":
	print "invalid"
	sys.exit()
sequ1 = sequ1.replace(" ", "")
sequ2 = sequ2.replace(" ", "")
sequ3 = sequ3.replace(" ", "")

print "Length of sequence 1 is:", len(sequ1)
print "Length of sequence 2 is:", len(sequ2)
print "Length of sequence 3 is:", len(sequ3)

sequence = sequ1 + sequ2 + sequ3
gcPercent = float(sequence.count('G') + sequence.count('C'))/len(sequence)*100

print sequence
print gcPercent
