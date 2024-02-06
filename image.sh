echo Building Dockerfile described image
docker build -t certification .
echo Launching docker compose services for upstanding certification:latest image
docker-compose up