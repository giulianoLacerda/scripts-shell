#!/bin/bash
classes=("Normal")
for each in "${classes[@]}" 
do
	cd ~
	cd ./Documents/Digits/bases/normal-defeito-novaCorrecaoCor/test/$each
	a=0
	echo $each
	for file in $(ls | shuf -n 10); do
		cd ~
		cp ./Documents/Digits/bases/normal-defeito-novaCorrecaoCor/test/$each/$file ./Documents/Digits/bases/tudo100/test/$each
		a=$((ls -1 | wc -l) + a)
	done
	echo $a
done