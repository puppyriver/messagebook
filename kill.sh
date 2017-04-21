ps -ef|awk '$1 ~/root/ && /messagebook/ && $0 !~/awk/ {system("kill 9 " $2)}'
