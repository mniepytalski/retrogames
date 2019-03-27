@echo off

SET FILENAME=target\snake-1.0-SNAPSHOT.jar

IF EXIST %FILENAME% (
    echo %FILENAME% exist
) ELSE (
    mvn install
)

java -jar %FILENAME%