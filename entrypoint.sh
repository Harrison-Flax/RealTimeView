#!/bin/sh
export SPRING_DATASOURCE_PASSWORD=$(cat /run/secrets/db-password)
export COINMARKETCAP_API_KEY=$(cat /run/secrets/coinmarketcap-key)
export NEWSAPI_API_KEY=$(cat /run/secrets/newsapi-key)
export ALPHAVANTAGE_API_KEY=$(cat /run/secrets/alphavantage-key)
exec java org.springframework.boot.loader.launch.JarLauncher