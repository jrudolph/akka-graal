#!/bin/sh

set -e

GRAAL_HOME=~/opt/graalvm-ce-1.0.0-rc5

$GRAAL_HOME/bin/native-image \
  -H:IncludeResources=.*conf \
  -H:ReflectionConfigurationFiles=reflectconf.json \
  --report-unsupported-elements-at-runtime \
  -jar target/scala-2.12/akka-graal-tester-assembly-0.1.0-SNAPSHOT.jar