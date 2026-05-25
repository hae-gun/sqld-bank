# 🗄️ SQLD 기출 문제 은행

SQLD 시험 대비 기출 + 변형 문제 랜덤 풀기 웹 앱

---

## 📱 화면 구성 (모바일 최적화)

| 탭 | 내용 |
|---|---|
| 📝 풀기 | 랜덤 1문제씩 출제, 4지선다, 해설 보기 |
| 📋 기록 | 맞춘/틀린 문제 이력, 정확도 |
| 📊 통계 | 문제 은행 현황, 내 성적 |

## 🗂 문제 카테고리 (60문제)

- SELECT/WHERE (NULL, LIKE, CASE, NVL, COALESCE)
- GROUP BY / HAVING
- JOIN (INNER, OUTER, CROSS, NATURAL, SELF)
- 서브쿼리 (스칼라, 상관, IN/EXISTS, ALL)
- 윈도우 함수 (RANK, DENSE_RANK, ROW_NUMBER, LAG, LEAD, NTILE)
- DDL (CREATE, ALTER, DROP, TRUNCATE, 제약조건)
- DML (INSERT, UPDATE, DELETE, MERGE)
- TCL (COMMIT, ROLLBACK, SAVEPOINT)
- 정규화 (1NF~BCNF, 이상현상, 반정규화)
- 인덱스 (B-Tree, 복합 인덱스, 함수 인덱스)
- 뷰(View)
- 집합 연산자 (UNION, INTERSECT, MINUS)
- SQL 최적화 (옵티마이저, 풀 테이블 스캔)
- 데이터 모델링 (엔터티, 관계, 카디널리티, ERD)

---

## 🚀 실행 방법

### 빠른 시작
```bash
chmod +x start.sh
./start.sh
```

### 수동 실행

**백엔드 (Spring Boot)**
```bash
cd backend
mvn spring-boot:run
# → http://localhost:8080
```

**프론트엔드 (React)**
```bash
cd frontend
npm install
npm start
# → http://localhost:3000
```

---

## 🔌 API 엔드포인트

| Method | URL | 설명 |
|--------|-----|------|
| GET | `/api/questions` | 전체 문제 목록 |
| GET | `/api/questions/random` | 랜덤 1문제 |
| GET | `/api/questions/random?category=JOIN` | 카테고리별 랜덤 |
| GET | `/api/questions/random?difficulty=상` | 난이도별 랜덤 |
| GET | `/api/questions/random?type=기출` | 유형별 랜덤 |
| GET | `/api/questions/random/batch?count=10` | 랜덤 N문제 |
| GET | `/api/questions/{id}` | 특정 문제 |
| GET | `/api/questions/categories` | 카테고리 목록 |
| GET | `/api/questions/stats` | 통계 |

---

## 🛠 기술 스택

- **Frontend**: React 18, Axios, CSS Variables
- **Backend**: Spring Boot 3.2, Java 17
- **Design**: 모바일 퍼스트, 다크 테마

---

## 📋 요구사항

- Java 17+
- Maven 3.6+
- Node.js 18+
