#!/usr/bin/env bash

check_location () {
   stat -c "%a %n" $1
}

locations="$HOME
           $HOME/.ssh
           $HOME/.ssh/authorized_keys
           $HOME/.ssh/config
           $HOME/.ssh/known_hosts"

for location in $locations ; 
do
   check_location $location
done
