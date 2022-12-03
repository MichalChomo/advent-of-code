#!/bin/zsh

if [ $1 -lt 10 ]; then
	day="Day0$1"
else
	day="Day$1"
fi

cd src
cp DayTemplate.kt "$day.kt"
touch "${day}_test.txt"
touch "${day}.txt"