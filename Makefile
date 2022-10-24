#!/usr/bin/env make

# Instruct make that these are phony targets
.PHONY : *

# Include file .env if not exists
ENV := $(PWD)/.env
ifneq ("$(wildcard $(ENV))","")
	include $(ENV)
endif
export

## SETUP
setup:
	@echo "#make setup starting..."
	@cp sample.env .env
	@chmod a+x ./gradlew

## BUILD AND REFRESH DEPENDENCIES
build-r: clean
	@echo "#make build-refresh starting..."
	@bash ./gradlew build --refresh-dependencies

## BUILD AND RUN IN BACKGROUND
run-d: build
	@echo "#make run-d starting..."
	@docker-compose up --build -d

## BUILD AND RUN
run: build
	@echo "#make run starting..."
	@docker-compose up --build

build: clean
	@echo "#make build starting..."
	@bash ./gradlew build

verify: clean
	@echo "#make verify starting..."
	@bash ./gradlew check
	@bash ./gradlew test

clean:
	@echo "#make clean starting..."
	@bash ./gradlew clean

start: build
	@echo "#make start-dependencies starting..."
	@docker-compose up postgres -d
	@docker-compose up pgadmin -d

stop:
	@echo "#make stop-dependencies starting..."
	@docker-compose down postgres
	@docker-compose down pgadmin

## DOCKER DOWN
down:
	@echo "#make down starting..."
	@docker-compose down --volumes
