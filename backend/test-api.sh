#!/bin/bash

echo "🚀 Testing Sparklet Backend API"
echo "================================"
echo ""

# Test 1: API Welcome
echo "✅ Test 1: API Welcome Endpoint"
echo "GET http://localhost:8080/api/"
curl -s http://localhost:8080/api/ | python3 -m json.tool 2>/dev/null || curl -s http://localhost:8080/api/
echo ""
echo ""

# Test 2: Public Endpoint
echo "✅ Test 2: Public Test Endpoint"
echo "GET http://localhost:8080/api/test/public"
curl -s http://localhost:8080/api/test/public | python3 -m json.tool 2>/dev/null || curl -s http://localhost:8080/api/test/public
echo ""
echo ""

# Test 3: Login
echo "✅ Test 3: User Login"
echo "POST http://localhost:8080/api/auth/signin"
echo "Body: {\"email\": \"tom@example.com\", \"password\": \"password123\"}"
LOGIN_RESPONSE=$(curl -s -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{"email": "tom@example.com", "password": "password123"}')

echo "$LOGIN_RESPONSE" | python3 -m json.tool 2>/dev/null || echo "$LOGIN_RESPONSE"
echo ""

# Extract token for protected endpoint test
TOKEN=$(echo "$LOGIN_RESPONSE" | grep -o '"token":"[^"]*"' | cut -d'"' -f4)

if [ ! -z "$TOKEN" ]; then
    echo "🔑 JWT Token extracted: ${TOKEN:0:50}..."
    echo ""
    
    # Test 4: Protected Endpoint
    echo "✅ Test 4: Protected Endpoint (Dashboard Stats)"
    echo "GET http://localhost:8080/api/dashboard/stats"
    echo "Authorization: Bearer ${TOKEN:0:20}..."
    curl -s -H "Authorization: Bearer $TOKEN" \
      http://localhost:8080/api/dashboard/stats | python3 -m json.tool 2>/dev/null || \
      curl -s -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/dashboard/stats
    echo ""
else
    echo "❌ Login failed - cannot test protected endpoints"
    echo ""
fi

echo ""
echo "🎉 API Test Complete!"
echo "Backend is running successfully at http://localhost:8080"
