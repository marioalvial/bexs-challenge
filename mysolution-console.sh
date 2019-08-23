#!/usr/bin/env bash

input=$1

./gradlew :bex-console-interface:clean :bexs-console-interface:run --args=$1 --console=plain