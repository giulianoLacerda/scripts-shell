#!/bin/bash

for each in $(ls $1); do
	cd $1$each
	a=0
	echo $each
	for file in $(ls $1$each); do
#		echo $file
		cd $1$each/$file
		b=$(ls -1 | wc -l)
		a=$((a+b))
	done
	echo $a
done
