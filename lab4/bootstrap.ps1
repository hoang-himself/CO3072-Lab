podman-compose -f compose.yaml up -d timescaledb
Start-Sleep -Seconds 10
podman exec -it timescaledb psql -U postgres `
  -c 'CREATE DATABASE student04' `
  -c "CREATE USER student04 WITH ENCRYPTED PASSWORD 'student04'" `
  -c 'GRANT ALL PRIVILEGES ON DATABASE student04 TO student04'

podman-compose -f compose.yaml up -d
