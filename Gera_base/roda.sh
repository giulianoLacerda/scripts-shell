#!/bin/bash
kotlinc $1 -include-runtime -d executavel.jar
java -jar executavel.jar
