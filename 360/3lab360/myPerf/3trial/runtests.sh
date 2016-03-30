#! /bin/bash

ab -n 100 -c 1 janice.cs.byu.edu:3060/large.html >> 100_requests_1_concurrent_largeHTML.txt
ab -n 100 -c 10 janice.cs.byu.edu:3060/large.html >> 100_requests_10_concurrent_largeHTML.txt
ab -n 100 -c 20 janice.cs.byu.edu:3060/large.html >> 100_requests_20_concurrent_largeHTML.txt

ab -n 100 -c 1 janice.cs.byu.edu:3060/medium.html >> 100_requests_1_concurrent_mediumHTML.txt
ab -n 100 -c 10 janice.cs.byu.edu:3060/medium.html >> 100_requests_10_concurrent_mediumHTML.txt
ab -n 100 -c 20 janice.cs.byu.edu:3060/medium.html >> 100_requests_20_concurrent_mediumHTML.txt

ab -n 100 -c 1 janice.cs.byu.edu:3060/small.html >> 100_requests_1_concurrent_smallHTML.txt
ab -n 100 -c 10 janice.cs.byu.edu:3060/small.html >> 100_requests_10_concurrent_smallHTML.txt
ab -n 100 -c 20 janice.cs.byu.edu:3060/small.html >> 100_requests_20_concurrent_smallHTML.txt






