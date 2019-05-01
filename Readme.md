In Dockerfile folder:
docker build -t juustimage .
docker run --name juustcontainer -d -p 8081:8081 juustimage

Create DB:
docker run --name juustdb -p 5431:5432 -e POSTGRES_PASSWORD=pass -d postgres:9.6

