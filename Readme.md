In Dockerfile folder:
docker build -t juustimage .
docker run --name juustcontainer -d -p 8081:8081 juustimage

Create DB:
docker run --name juustdb -p 5431:5432 -e POSTGRES_PASSWORD=pass -d postgres:9.6

Install java on AWS:
sudo apt install default-jre

Create jar:
gradle clean assemble

Run jar (stops when ssh connection ends):
java -jar juustuunelm-0.1.0.jar

psql -h localhost -p 5573 -U postgres -c "create user juust with password 'pass';"
psql -h localhost -p 5573 -U postgres -c "create database juustdb;"
psql -h localhost -p 5573 -U postgres -c "grant all privileges on database juustdb to juust;"
