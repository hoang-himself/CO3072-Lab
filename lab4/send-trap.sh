#!/usr/bin/env sh

while $true; do
  zabbix_sender -z 10.89.1.23 -p 10051 -s 'Zabbix server' -k 'second.custom' -o $(shuf -i 0-499 -n 1 | xargs sh -c 'echo $(($1 * 2))' args)
  sleep 60
done
