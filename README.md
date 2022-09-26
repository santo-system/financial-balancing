# financial-balancing

## IGTI - MBA em Engenharia de Software Ágil

Projeto Aplicado: Balanceamento de uma carteira de investimentos

## How to run the local project

### Configure Project

```shell
# configure initial setup
# copy sample.env to .env
make setup

# edit .env if needed
```

### Run the project
```shell
# it will start docker and run project on http://localhost:8080/financial-balancing
make run

## or if you don't use make, use the following command
./gradlew clean build test
docker-compose up --build -d
```

### Stop running the project
```shell
# it will stop running the project and down docker
make down

## or if you don't use make, use the following command
docker-compose down --volumes
```
