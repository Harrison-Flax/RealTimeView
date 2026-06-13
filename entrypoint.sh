#!/bin/sh
export SPRING_DATASOURCE_PASSWORD=$(cat /run/secrets/db-password)
exec java org.springframework.boot.loader.launch.JarLauncher