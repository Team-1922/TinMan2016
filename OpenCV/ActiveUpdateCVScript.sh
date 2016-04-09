#assume sudo privaleges
UPDATESCRIPT="$1"

LONGWAIT=0
while :
do
	eval $UPDATESCRIPT

	if [$?==0];
	then
		echo "Updated File Successfully!"
		LONGWAIT=0
	else
		if [$LONGWAIT==0];
		then
			echo "Swap File Does Not Exist!"
			echo "Waiting..."
			LONGWAIT=1
		fi
	fi
	
	if [$LONGWAIT==0];
	then
		echo "Waiting..."
	fi
	sleep 5
done
