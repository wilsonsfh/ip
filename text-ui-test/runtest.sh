#!/usr/bin/env bash

# define paths
SRC_PATH="../src/main/java"
BIN_PATH="../bin"
MAIN_CLASS="caviar.Caviar"

# create bin directory if it doesn't exist
if [ ! -d "$BIN_PATH" ]
then
    mkdir ../bin
fi

# delete output from previous run
if [ -e "./ACTUAL.TXT" ]
then
    rm ACTUAL.TXT
fi

# compile all Java files into bin directory, terminate if error occurs
if ! find "$SRC_PATH" -name "*.java" | xargs javac -cp "$SRC_PATH" -Xlint:none -d "$BIN_PATH"; then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

## compile the code into the bin folder, terminates if error occurred
#if ! javac -cp ../src/main/java -Xlint:none -d ../bin ../src/main/java/*.java
#then
#    echo "********** BUILD FAILURE **********"
#    exit 1
#fi

# reset storage (delete previous tasks.txt)
if [ -e "../data/tasks.txt" ]; then
    rm ../data/tasks.txt
fi

# run the program, feed commands from input.txt, and redirect output to ACTUAL.TXT
java -classpath "$BIN_PATH" "$MAIN_CLASS" < input.txt > ACTUAL.TXT

# convert to UNIX format
cp EXPECTED.TXT EXPECTED-UNIX.TXT
dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT

# compare the output to the expected output
# ignore task count differences
diff <(grep -v "Now you have [0-9]\+ tasks" ACTUAL.TXT) <(grep -v "Now you have [0-9]\+ tasks" EXPECTED-UNIX.TXT)
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi