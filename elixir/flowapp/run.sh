 RUNFILE=$1

 ts=$(date +%s%N)

 mix run $RUNFILE

 tt=$((($(date +%s%N) - $ts)/1000000))
 echo "Time taken: $tt milliseconds"
