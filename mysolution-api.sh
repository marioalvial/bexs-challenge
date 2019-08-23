#!/usr/bin/env bash

input=$1

./gradlew :bexs-api-interface:clean :bexs-api-interface:run --args=$1 --console=plain