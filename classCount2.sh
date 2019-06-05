#!/bin/bash

for each in $(ls $1); do
	cd $1$each
	a=0
	echo $each
	b=$(ls -1 | wc -l)
	a=$((a+b))
	echo $a
done
