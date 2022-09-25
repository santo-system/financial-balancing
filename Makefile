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
build-refresh: clean
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
	@.\gradlew build test

verify: clean
	@echo "#make verify started"
	@.\gradlew ktlintFormat
	@.\gradlew check
	@.\gradlew test

clean:
	@echo "#make clean started"
	@.\gradlew clean

start-dependencies: build
	@echo "#make start-dependencies started"
	@docker-compose up postgres
	@docker-compose up pgadmin

stop-dependencies:
	@echo "#make stop-dependencies started"
	@docker-compose down postgres
	@docker-compose down pgadmin

## DOCKER DOWN
down:
	@echo "#make down started"
	@docker-compose down --volumes
