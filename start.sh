#!/bin/bash
echo "🗄️  SQLD 문제은행 시작..."

# 백엔드 시작
echo "⚙️  Spring Boot 백엔드 시작 중 (포트 9000)..."
cd "$(dirname "$0")/backend"
mvn spring-boot:run -q &
BACKEND_PID=$!

# 잠시 대기
sleep 5

# 프론트엔드 시작
echo "⚛️   React 프론트엔드 시작 중 (포트 3002)..."
cd "$(dirname "$0")/frontend"
npm install --silent && npm start &
FRONTEND_PID=$!

echo ""
echo "✅ 실행 중!"
echo "   프론트엔드: http://localhost:3002"
echo "   백엔드 API: http://localhost:9000/api/questions"
echo ""
echo "종료하려면 Ctrl+C를 누르세요."

trap "kill $BACKEND_PID $FRONTEND_PID 2>/dev/null; echo '종료됨'" INT
wait
