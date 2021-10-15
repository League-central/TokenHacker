#!/bin/bash
cd java-modules
shopt  -s dotglob
for FILE in Level*; do
	cd $FILE
	cp ~/Desktop/TokenHacker.jar TokenHacker.jar
	awk '/\<classpathentry kind=\"src\" path=\"src\"\/\>/ { print; print "        \<classpathentry kind=\"lib\" path=\"TokenHacker.jar\"\/\>"; next }1' .classpath > temp.txt
	awk '{ print }' temp.txt > .classpath
	rm temp.txt
	cd src
	cp -r ~/Desktop/league_token league_token
	cd ~/Desktop/java-modules
done
