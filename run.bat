@echo off
echo Starting UG Campus Navigator...
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

echo Building application...
.\mvnw.cmd clean install -q

if %errorlevel% neq 0 (
    echo Error: Build failed
    pause
    exit /b 1
)

echo.
echo Starting application server...
echo Access the application at: http://localhost:8080
echo Press Ctrl+C to stop the server
echo.

.\mvnw.cmd spring-boot:run
