#!/bin/bash
classes=("Ardido" "Brocado" "Casca" "Chocho" "Coco" "Concha" "Marinheiro" "Pau" "Pedra" "Preto" "Quebrado" "Verde")
for each in "${classes[@]}" 
do
	cd ~
	cd ./Documents/Digits/bases/normal-defeito-novaCorrecaoCor/val/Defeito/$each
	for file in $(ls | shuf -n 20); do
		cd ~
		#echo $file
		cp ./Documents/Digits/bases/normal-defeito-novaCorrecaoCor/val/Defeito/$each/$file ./Documents/Digits/bases/tudo100/val/$each
	done
done