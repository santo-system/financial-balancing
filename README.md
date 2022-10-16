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

## Project structure
```
.
├── application            # Application module for Rest API (Adapters)
│   └── src                # Source files from Application module
│       ├── main   
│       │   └── resources  # Application configurations
│       └── test           # Unit Tests from Application module
├── domain                 # Domain module for Business rules (Ports)
│   └── src                # Source files from Domain module
│       ├── main           
│       └── test           # Unit Tests from Domain module
└── infra                  # Infrastructure module for connectors (Adapters)
    └── src                # Source files from Infrastructure module
        ├── main           
        │   └── resources  
        │       └── db     # Migrations files for Flyway
        └── test           # Unit Tests from Infrastructure module
```
