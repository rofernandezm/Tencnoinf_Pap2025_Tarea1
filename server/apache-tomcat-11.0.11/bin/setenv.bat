@echo off

IF NOT DEFINED DB_PORT SET "DB_PORT=9001"
IF NOT DEFINED DB_NAME SET "DB_NAME=turismoUyDB"

FOR %%I IN ("%~dp0..") DO SET "CATALINA_BASE_DIR=%%~fI"
IF NOT DEFINED DB_PATH SET "DB_PATH=%CATALINA_BASE_DIR%\data\%DB_NAME%"

IF NOT EXIST "%DB_PATH%" mkdir "%DB_PATH%"

set "CATALINA_OPTS=%CATALINA_OPTS% -Ddb.port=%DB_PORT% -Ddb.name=%DB_NAME% -Ddb.path=%DB_PATH%"

echo [setenv.bat] db.port=%DB_PORT%
echo [setenv.bat] db.name=%DB_NAME%
echo [setenv.bat] db.path=%DB_PATH%
