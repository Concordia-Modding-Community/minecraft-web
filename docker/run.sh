#!/bin/bash

if [ ! -z $EULA ] ; then
    echo "eula=$EULA" > /usr/server/eula.txt
fi

if [ ! -z $OP ] ; then
    userJSON=$(curl -s https://api.mojang.com/users/profiles/minecraft/$OP)

    if [[ $userJSON =~ \"id\"\:\"(.{32})\" ]]
    then
        UUID=${BASH_REMATCH[1]}
        UUID="${UUID:0:8}-${UUID:8:4}-${UUID:12:4}-${UUID:16:4}-${UUID:20:12}"
        userJSON="${userJSON//\"id\"\:\"[a-zA-Z0-9]*?\"/\"uuid\":\"$UUID\"}"
        userJSON="[${userJSON::-1},\"level\":4}]"

        echo "${userJSON}" > /usr/server/ops.json
    fi
fi

java -Xms${INIT_MEMORY} -Xmx${MAX_MEMORY} -XX:+UseG1GC -jar spigot.jar nogui