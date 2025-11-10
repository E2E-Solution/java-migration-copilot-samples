#!/bin/bash

echo "=========================================="
echo "Stopping Assets Manager DEV environment"
echo "=========================================="

# Stop PostgreSQL container
echo "Stopping PostgreSQL..."
docker stop assets-manager-postgres-dev 2>/dev/null || echo "PostgreSQL container not running"
docker rm assets-manager-postgres-dev 2>/dev/null || echo "PostgreSQL container already removed"

echo ""
echo "=========================================="
echo "DEV environment stopped"
echo "=========================================="
echo ""
echo "To start again, run: ./run-local-dev.sh"
