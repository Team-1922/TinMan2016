SWAPNAME="/home/remote/PiScript.py"
SCRIPTNAME="/home/pi/vision/PiScript.py"
PARAMSWAP="/home/remote/parameters"
PARAMFILE="/home/pi/vision/parameters"
PARAMETERS=""

if [ -f "$SWAPNAME" ]; 
then
	echo "File Found, Swapping..."
	
	#wait to make sure the file is completely uploaded before copying, but still make sure there is a file before waiting
	sleep 5
	
	echo "Stopping $SCRIPTNAME"
	killall python
	
	echo "Deleting $SCRIPTNAME"
	rm "$SCRIPTNAME"
	
	echo "Moving $SWAPNAME"
	mv "$SWAPNAME" "$SCRIPTNAME"
	
	if [-f "$PARAMSWAP"];
	then
		echo "Deleting $PARAMFILE"
		rm "$PARAMFILE"
		
		echo "Moving Parameter File: $PARAMSWAP"
		mv "$PARAMSWAP" "$PARAMFILE"
		
		echo "Loading Parameters From File: $PARAMFILE"
		PARAMETERS="`cat $PARAMFILE`"
	fi

	if ["$1"=="-r"];
	then
		echo "Restarting $SCRIPTNAME with parameters: $PARAMETERS"
		python "$SCRIPTNAME $PARAMETERS" &
	fi
	exit 0
else
	echo "$SCRIPTNAME does not exist"
	exit 1
fi
