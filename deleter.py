#! /usr/bin/env python
import sys
import os 

for subdir, dirs, files in os.walk(sys.argv[1]):
	for currfile in files:
		try:
			filename = subdir + "/" + currfile
			new_filename = filename.replace(" ", "_")
			new_filename = new_filename.replace("'", "")
			print new_filename
			os.rename(filename, new_filename)
		except:
			print currfile
