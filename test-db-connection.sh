#!/bin/bash

echo "Testing PostgreSQL Database Connection..."
echo ""

# Test connection using psql
export PGPASSWORD='cc8fa453452e27ea46edae80f5f650f8077efde0'

echo "Attempting to connect..."
psql -U SocialApp_ourluckme -h apli13.h.filess.io -p 61006 -d SocialApp_ourluckme -c "SELECT version();" 2>&1

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Connection successful!"
    echo ""
    echo "Checking existing tables..."
    psql -U SocialApp_ourluckme -h apli13.h.filess.io -p 61006 -d SocialApp_ourluckme -c "\dt" 2>&1
else
    echo ""
    echo "❌ Connection failed!"
fi

