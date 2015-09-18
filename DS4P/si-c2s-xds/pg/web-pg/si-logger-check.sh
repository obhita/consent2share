#!/bin/bash
##
#Configure this location to run this tool in any location
Config_Location=C:/\eclipse-workspace/\ds4p-workspace/\ds4p-prod/\PropertyTemplate/\si/\pg/\config
if [ ! -d $Config_Location ];then
		echo -e "\e[1;31mEnvironment Location not found\e[0m"
		sleep 3
        exit 1
fi

cd $Config_Location

Key=$(sed -n 1p instrumentation.properties)
Value=${Key#*=}

start chrome https://localhost:8446/si-c2s-xds-pg/api/instrumentation/loggerCheck?instrumentationKey=$Value