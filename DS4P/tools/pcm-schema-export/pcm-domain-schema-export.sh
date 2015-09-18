#!/bin/bash
##
#Configure this location to run this tool in any location
#Environment_Location=C:/\workspace/\ds4p-workspace/\ds4p-prod/\DS4P/\tools/\pcm-schema-export
#cd $Environment_Location

generateSchema() {
	mvn clean install
}

dropSchema() {
	mysqladmin -u root -padmin drop consent2share
	mysqladmin -u root -padmin create consent2share
}

if [ ! -d $Environment_Location ];then
		echo -e "\e[1;31mEnvironment Location not found\e[0m"
		sleep 3
        exit 1
fi

current_branch=$(git symbolic-ref --short HEAD)

echo -e "\e[1;32mPlease type which branch you work\e[0m"
read branch_name

if [[ $branch_name != $current_branch ]];then
		echo -e "\e[1;31mCurrent branch is not the branch that you want to work for,please checkout to the target branch\e[0m"
		sleep 5
        exit 1
fi

dropSchema
generateSchema


