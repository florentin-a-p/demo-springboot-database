#!/bin/bash

./gradlew generateLock # assuming there's only 1 module
diff ./build/dependencies.lock global.lock
status=$?
if [[ $status != 0 ]];
then
  echo "Lock is not updated!"
  exit 1
fi
