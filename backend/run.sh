#!/bin/bash

echo "Starting Sparklet Backend..."
echo "Make sure you have Java 17+ and Maven installed"
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Java is not installed. Please install Java 17 or later."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Maven is not installed. Please install Maven 3.6 or later."
    exit 1
fi

# Clean and compile
echo "Cleaning and compiling..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "Build failed. Please check the errors above."
    exit 1
fi

echo ""
echo "Starting Spring Boot application..."
echo "API will be available at: http://localhost:8080"
echo ""
echo "Test endpoints:"
echo "- GET  http://localhost:8080/api/                     (API welcome page)"
echo "- GET  http://localhost:8080/api/test/public          (Public test endpoint)"
echo "- POST http://localhost:8080/api/auth/signin          (Login endpoint)"
echo ""
echo "Sample login credentials:"
echo "Email: tom@example.com"
echo "Password: password123"
echo ""
echo "After login, you can test protected endpoints like:"
echo "- GET  http://localhost:8080/api/dashboard/stats      (requires auth token)"
echo "- GET  http://localhost:8080/api/applications         (requires auth token)"
echo ""

# Run the application
mvn spring-boot:run
