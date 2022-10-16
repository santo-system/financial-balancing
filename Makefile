#!/usr/bin/env make

# Instruct make that these are phony targets
.PHONY : *

# Include file .env if exists
ENV := $(PWD)/.env
ifneq ("$(wildcard $(ENV))","")
	include $(ENV)
endif
export

## SETUP
setup:
	@echo "#make setup started"
	@cp sample.env .env
	@chmod a+x .\gradlew

## BUILD AND REFRESH DEPENDENCIES
build-r: clean
	@echo "#make build-refresh started"
	@.\gradlew build --refresh-dependencies

## BUILD AND RUN IN BACKGROUND
run-d: build
	@echo "#make run-d started"
	@docker-compose up --build -d

## BUILD AND RUN
run: build
	@echo "#make run started"
	@docker-compose up --build

build: clean
	@echo "#make build started"
	@.\gradlew build

verify: clean
	@echo "#make verify started"
	@.\gradlew check
	@.\gradlew test

clean:
	@echo "#make clean started"
	@.\gradlew clean

start: build
	@echo "#make start-dependencies started"
	@docker-compose up postgres -d
	@docker-compose up pgadmin -d

stop:
	@echo "#make stop-dependencies started"
	@docker-compose down postgres
	@docker-compose down pgadmin

## DOCKER DOWN
down:
	@echo "#make down started"
	@docker-compose down --volumes
