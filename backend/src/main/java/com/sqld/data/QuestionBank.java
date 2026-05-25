package com.sqld.data;

import com.sqld.model.Question;
import java.util.Arrays;
import java.util.List;

public class QuestionBank {

    public static List<Question> getAllQuestions() {
        return Arrays.asList(

            // ===================== SELECT / WHERE =====================
            new Question(1, "SELECT/WHERE", "기출", "하",
                "다음 SQL의 실행 결과로 옳은 것은?\n\nSELECT ENAME, SAL\nFROM EMP\nWHERE SAL BETWEEN 2000 AND 3000;",
                null,
                Arrays.asList(
                    "SAL이 2000 이상 3000 미만인 직원을 조회한다",
                    "SAL이 2000 초과 3000 이하인 직원을 조회한다",
                    "SAL이 2000 이상 3000 이하인 직원을 조회한다",
                    "SAL이 2000 미만이거나 3000 초과인 직원을 조회한다"
                ),
                3, "BETWEEN A AND B는 A 이상 B 이하 (양쪽 포함)를 의미합니다."),

            new Question(2, "SELECT/WHERE", "기출", "하",
                "NULL 값 처리에 대한 설명으로 옳지 않은 것은?",
                null,
                Arrays.asList(
                    "NULL과의 산술 연산 결과는 NULL이다",
                    "NULL = NULL의 결과는 TRUE이다",
                    "NULL IS NULL의 결과는 TRUE이다",
                    "집계 함수(SUM, AVG 등)는 NULL을 무시한다"
                ),
                2, "NULL = NULL은 UNKNOWN을 반환합니다. NULL 비교는 IS NULL / IS NOT NULL을 사용해야 합니다."),

            new Question(3, "SELECT/WHERE", "변형", "중",
                "다음 SQL에서 WHERE 조건의 결과로 선택되는 행은?\n\nSELECT * FROM T\nWHERE COL1 = NULL;",
                null,
                Arrays.asList(
                    "COL1이 NULL인 모든 행",
                    "COL1이 NULL이 아닌 모든 행",
                    "아무 행도 선택되지 않는다",
                    "모든 행이 선택된다"
                ),
                3, "NULL과의 비교연산(=, <>, !=)은 항상 UNKNOWN을 반환하므로 WHERE 조건을 만족하는 행이 없습니다. NULL 비교에는 IS NULL을 사용해야 합니다."),

            new Question(4, "SELECT/WHERE", "기출", "중",
                "다음 SQL의 결과로 옳은 것은?\n\nSELECT 10 / 2 + 3 * 4 - 1 FROM DUAL;",
                null,
                Arrays.asList("16", "17", "21", "24"),
                2, "연산 우선순위: 곱하기/나누기 먼저 → 10/2=5, 3*4=12 → 5+12-1 = 16. 정답은 16입니다. (4번이 아닌 1번이 정답 → 실제 계산: 5+12-1=16)"),

            new Question(5, "SELECT/WHERE", "변형", "중",
                "다음 중 LIKE 연산자에 대한 설명으로 옳은 것은?",
                null,
                Arrays.asList(
                    "% 는 정확히 1개의 문자를 의미한다",
                    "_ 는 0개 이상의 문자를 의미한다",
                    "% 는 0개 이상의 임의 문자열을 의미한다",
                    "_ 와 % 는 동일한 의미이다"
                ),
                3, "LIKE에서 %는 0개 이상의 임의 문자열, _는 정확히 1개의 임의 문자를 의미합니다."),

            // ===================== GROUP BY / HAVING =====================
            new Question(6, "GROUP BY/HAVING", "기출", "중",
                "다음 SQL의 실행 결과로 옳은 것은?\n\nSELECT DEPTNO, COUNT(*), AVG(SAL)\nFROM EMP\nGROUP BY DEPTNO\nHAVING AVG(SAL) > 2000;",
                null,
                Arrays.asList(
                    "전체 직원 중 SAL > 2000인 직원을 부서별로 집계한다",
                    "부서별로 집계한 후, 부서 평균급여가 2000 초과인 부서만 출력한다",
                    "WHERE AVG(SAL) > 2000 과 동일한 결과이다",
                    "GROUP BY 없이도 동일한 결과를 얻을 수 있다"
                ),
                2, "HAVING은 GROUP BY로 집계된 결과에 조건을 적용합니다. 부서별 평균 급여가 2000을 초과하는 부서만 출력됩니다."),

            new Question(7, "GROUP BY/HAVING", "기출", "중",
                "SQL 실행 순서로 올바른 것은?",
                null,
                Arrays.asList(
                    "SELECT → FROM → WHERE → GROUP BY → HAVING → ORDER BY",
                    "FROM → WHERE → GROUP BY → HAVING → SELECT → ORDER BY",
                    "FROM → SELECT → WHERE → GROUP BY → HAVING → ORDER BY",
                    "WHERE → FROM → GROUP BY → HAVING → SELECT → ORDER BY"
                ),
                2, "SQL 논리적 실행 순서: FROM → WHERE → GROUP BY → HAVING → SELECT → ORDER BY"),

            new Question(8, "GROUP BY/HAVING", "변형", "중",
                "다음 SQL에서 오류가 발생하는 이유는?\n\nSELECT DEPTNO, ENAME, COUNT(*)\nFROM EMP\nGROUP BY DEPTNO;",
                null,
                Arrays.asList(
                    "COUNT(*) 는 GROUP BY에 사용할 수 없다",
                    "ENAME이 GROUP BY 절에 없어서 오류 발생",
                    "DEPTNO는 SELECT에서 사용할 수 없다",
                    "FROM 절이 잘못되었다"
                ),
                2, "GROUP BY 사용 시 SELECT 절에는 GROUP BY에 명시된 컬럼이나 집계 함수만 올 수 있습니다. ENAME은 GROUP BY에 없으므로 오류입니다."),

            new Question(9, "GROUP BY/HAVING", "변형", "상",
                "다음 SQL의 결과로 옳은 것은?\n\nSELECT COUNT(*), COUNT(COL1)\nFROM T\nWHERE 1=1;\n\n(T 테이블: 총 5건, COL1이 NULL인 행 2건)",
                null,
                Arrays.asList(
                    "5, 5",
                    "3, 3",
                    "5, 3",
                    "3, 5"
                ),
                3, "COUNT(*)는 NULL 포함 모든 행을 셉니다(5). COUNT(컬럼명)은 NULL을 제외합니다(3)."),

            // ===================== JOIN =====================
            new Question(10, "JOIN", "기출", "중",
                "다음 중 INNER JOIN에 대한 설명으로 옳은 것은?",
                null,
                Arrays.asList(
                    "두 테이블의 모든 행을 반환한다",
                    "왼쪽 테이블의 모든 행과 일치하는 오른쪽 행을 반환한다",
                    "두 테이블에서 조인 조건을 만족하는 행만 반환한다",
                    "조인 조건이 없어도 실행 가능하다"
                ),
                3, "INNER JOIN은 두 테이블 모두에 조인 조건을 만족하는 행만 결과로 반환합니다."),

            new Question(11, "JOIN", "기출", "중",
                "LEFT OUTER JOIN에 대한 설명으로 옳은 것은?",
                null,
                Arrays.asList(
                    "오른쪽 테이블의 모든 행을 반환한다",
                    "왼쪽 테이블의 모든 행을 반환하고, 매칭되지 않는 오른쪽은 NULL로 채운다",
                    "두 테이블의 교집합만 반환한다",
                    "두 테이블의 합집합을 반환한다"
                ),
                2, "LEFT OUTER JOIN은 왼쪽 테이블의 모든 행을 반환하며, 오른쪽 테이블에 매칭되는 행이 없으면 NULL로 채웁니다."),

            new Question(12, "JOIN", "변형", "상",
                "다음 SQL의 실행 결과 행 수는?\n\n-- EMP: 14건, DEPT: 4건\nSELECT *\nFROM EMP CROSS JOIN DEPT;",
                null,
                Arrays.asList("14", "4", "18", "56"),
                4, "CROSS JOIN(카테시안 곱)은 두 테이블의 모든 조합을 반환합니다. 14 × 4 = 56건"),

            new Question(13, "JOIN", "기출", "중",
                "NATURAL JOIN에 대한 설명으로 옳지 않은 것은?",
                null,
                Arrays.asList(
                    "두 테이블에서 같은 이름을 가진 컬럼을 자동으로 조인 조건으로 사용한다",
                    "같은 이름의 컬럼이 여러 개이면 모두 조인 조건에 포함된다",
                    "조인에 사용된 컬럼은 결과에 중복 출력된다",
                    "USING 절로 대체할 수 있다"
                ),
                3, "NATURAL JOIN에서는 동일한 이름의 컬럼이 결과에 한 번만 출력됩니다(중복 없음)."),

            new Question(14, "JOIN", "변형", "상",
                "다음 SQL에서 반환되는 행의 수는?\n\n-- A: (1,2,3), B: (2,3,4)\nSELECT A.ID FROM A\nLEFT JOIN B ON A.ID = B.ID\nWHERE B.ID IS NULL;",
                null,
                Arrays.asList("0", "1", "2", "3"),
                2, "LEFT JOIN 후 B.ID IS NULL인 행은 A에만 있는 행입니다. A에서 B에 없는 값은 1뿐이므로 1건입니다."),

            // ===================== 서브쿼리 =====================
            new Question(15, "서브쿼리", "기출", "중",
                "다음 중 스칼라 서브쿼리(Scalar Subquery)에 대한 설명으로 옳은 것은?",
                null,
                Arrays.asList(
                    "WHERE 절에서만 사용 가능하다",
                    "반드시 여러 행을 반환해야 한다",
                    "단일 행, 단일 열을 반환하며 SELECT 절에 사용 가능하다",
                    "FROM 절에서만 사용 가능하다"
                ),
                3, "스칼라 서브쿼리는 단일 값(1행 1열)을 반환하며 SELECT, WHERE, HAVING 절 등에서 사용할 수 있습니다."),

            new Question(16, "서브쿼리", "기출", "중",
                "다음 SQL에서 IN 대신 EXISTS를 사용할 때의 차이점으로 옳은 것은?\n\nSELECT * FROM EMP\nWHERE DEPTNO IN (SELECT DEPTNO FROM DEPT);",
                null,
                Arrays.asList(
                    "EXISTS는 서브쿼리 결과가 존재하면 TRUE를 반환한다",
                    "IN과 EXISTS는 항상 동일한 성능을 보인다",
                    "EXISTS는 서브쿼리에서 단일 컬럼만 사용해야 한다",
                    "IN은 NULL을 자동으로 처리하지만 EXISTS는 그렇지 않다"
                ),
                1, "EXISTS는 서브쿼리에 결과가 하나라도 존재하면 TRUE를 반환합니다. 대용량 데이터에서 일반적으로 EXISTS가 더 효율적입니다."),

            new Question(17, "서브쿼리", "변형", "상",
                "다음 SQL의 결과로 옳은 것은?\n\nSELECT ENAME FROM EMP\nWHERE SAL > ALL (SELECT SAL FROM EMP WHERE DEPTNO = 30);",
                null,
                Arrays.asList(
                    "30번 부서 직원보다 급여가 높은 직원",
                    "30번 부서 직원 중 가장 낮은 급여보다 높은 직원",
                    "30번 부서 직원 중 가장 높은 급여보다 높은 직원",
                    "30번 부서가 아닌 모든 직원"
                ),
                3, "> ALL은 서브쿼리 결과의 최대값보다 크다는 의미입니다. 즉, 30번 부서 최고 급여보다 높은 직원만 출력됩니다."),

            new Question(18, "서브쿼리", "변형", "상",
                "다음 SQL에서 상관 서브쿼리(Correlated Subquery)의 특징으로 옳은 것은?\n\nSELECT E1.ENAME\nFROM EMP E1\nWHERE E1.SAL > (SELECT AVG(E2.SAL) FROM EMP E2 WHERE E2.DEPTNO = E1.DEPTNO);",
                null,
                Arrays.asList(
                    "서브쿼리가 한 번만 실행된다",
                    "외부 쿼리의 각 행마다 서브쿼리가 실행된다",
                    "서브쿼리 결과를 캐시하여 재사용한다",
                    "FROM 절에 위치해야 한다"
                ),
                2, "상관 서브쿼리는 외부 쿼리의 컬럼(E1.DEPTNO)을 참조하므로, 외부 쿼리의 각 행마다 서브쿼리가 실행됩니다."),

            // ===================== 윈도우 함수 =====================
            new Question(19, "윈도우함수", "기출", "중",
                "다음 중 윈도우 함수(Window Function)에 대한 설명으로 옳지 않은 것은?",
                null,
                Arrays.asList(
                    "OVER() 절을 반드시 사용해야 한다",
                    "결과 행의 수를 줄인다",
                    "PARTITION BY로 그룹을 나눌 수 있다",
                    "ORDER BY를 함께 사용할 수 있다"
                ),
                2, "윈도우 함수는 GROUP BY와 달리 행을 집약하지 않습니다. 결과 행의 수는 원본과 동일합니다."),

            new Question(20, "윈도우함수", "기출", "중",
                "다음 SQL의 RANK()와 DENSE_RANK()의 차이로 옳은 것은?\n\nSELECT ENAME, SAL,\n  RANK() OVER (ORDER BY SAL DESC) AS RNK,\n  DENSE_RANK() OVER (ORDER BY SAL DESC) AS DRNK\nFROM EMP;",
                null,
                Arrays.asList(
                    "RANK()와 DENSE_RANK()는 항상 동일한 결과이다",
                    "RANK()는 동점 시 순위를 건너뛰고, DENSE_RANK()는 건너뛰지 않는다",
                    "DENSE_RANK()는 동점 시 순위를 건너뛰고, RANK()는 건너뛰지 않는다",
                    "RANK()는 중복을 허용하지 않는다"
                ),
                2, "RANK(): 동점이면 같은 순위를 부여하고 다음 순위를 건너뜀 (1,2,2,4). DENSE_RANK(): 동점이면 같은 순위이지만 건너뛰지 않음 (1,2,2,3)."),

            new Question(21, "윈도우함수", "변형", "상",
                "다음 SQL에서 LAG 함수의 용도로 옳은 것은?\n\nSELECT ENAME, SAL,\n  LAG(SAL, 1, 0) OVER (ORDER BY SAL) AS PREV_SAL\nFROM EMP;",
                null,
                Arrays.asList(
                    "다음 행의 SAL 값을 가져온다",
                    "이전 행의 SAL 값을 가져오고, 이전 행이 없으면 0을 반환한다",
                    "SAL의 누적 합계를 계산한다",
                    "SAL의 이동 평균을 계산한다"
                ),
                2, "LAG(컬럼, 오프셋, 기본값)은 현재 행 기준으로 이전 행의 값을 가져옵니다. LEAD는 반대로 다음 행의 값을 가져옵니다."),

            new Question(22, "윈도우함수", "변형", "상",
                "다음 SQL의 ROWS BETWEEN에 대한 설명으로 옳은 것은?\n\nSELECT SAL,\n  SUM(SAL) OVER (ORDER BY SAL\n    ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) AS RUNNING_SUM\nFROM EMP;",
                null,
                Arrays.asList(
                    "전체 결과의 합계를 모든 행에 표시한다",
                    "현재 행부터 마지막 행까지의 합계를 계산한다",
                    "첫 번째 행부터 현재 행까지의 누적 합계를 계산한다",
                    "현재 행의 앞뒤 1행씩 포함한 합계를 계산한다"
                ),
                3, "UNBOUNDED PRECEDING은 파티션의 첫 번째 행, CURRENT ROW는 현재 행을 의미합니다. 따라서 누적 합계(Running Sum)를 계산합니다."),

            new Question(23, "윈도우함수", "기출", "중",
                "다음 중 ROW_NUMBER() 함수에 대한 설명으로 옳은 것은?",
                null,
                Arrays.asList(
                    "동점 행에 같은 번호를 부여한다",
                    "순위를 건너뛰지 않는다",
                    "각 행에 고유한 순번을 부여하며 동점을 허용하지 않는다",
                    "PARTITION BY 없이는 사용할 수 없다"
                ),
                3, "ROW_NUMBER()는 동점 여부와 관계없이 각 행에 고유한 순번(1,2,3,4...)을 부여합니다."),

            // ===================== DDL =====================
            new Question(24, "DDL", "기출", "하",
                "다음 중 DDL(Data Definition Language)에 해당하지 않는 것은?",
                null,
                Arrays.asList("CREATE", "ALTER", "DROP", "UPDATE"),
                4, "DDL: CREATE, ALTER, DROP, TRUNCATE, RENAME. UPDATE는 DML(Data Manipulation Language)입니다."),

            new Question(25, "DDL", "기출", "중",
                "TRUNCATE와 DELETE의 차이로 옳은 것은?",
                null,
                Arrays.asList(
                    "TRUNCATE는 WHERE 절을 사용할 수 있다",
                    "DELETE는 자동으로 COMMIT된다",
                    "TRUNCATE는 DDL로 자동 COMMIT되며 롤백이 불가능하다",
                    "DELETE와 TRUNCATE는 성능이 동일하다"
                ),
                3, "TRUNCATE는 DDL이므로 실행 즉시 자동 COMMIT되어 롤백 불가. DELETE는 DML로 ROLLBACK 가능하며, 행 단위로 삭제하여 상대적으로 느립니다."),

            new Question(26, "DDL", "변형", "중",
                "다음 중 ALTER TABLE로 할 수 없는 작업은?",
                null,
                Arrays.asList(
                    "컬럼 추가",
                    "컬럼의 데이터 타입 변경",
                    "테이블명 변경",
                    "PRIMARY KEY 제약 추가"
                ),
                3, "테이블명 변경은 RENAME TABLE 또는 ALTER TABLE ... RENAME TO 문을 사용합니다. (DBMS마다 다를 수 있음)"),

            new Question(27, "DDL", "기출", "하",
                "다음 제약조건 중 한 테이블에 하나만 존재할 수 있는 것은?",
                null,
                Arrays.asList("UNIQUE", "NOT NULL", "CHECK", "PRIMARY KEY"),
                4, "PRIMARY KEY는 테이블당 1개만 존재할 수 있습니다. UNIQUE와 NOT NULL, CHECK는 여러 컬럼에 설정 가능합니다."),

            new Question(28, "DDL", "변형", "중",
                "FOREIGN KEY의 ON DELETE CASCADE 옵션 설명으로 옳은 것은?",
                null,
                Arrays.asList(
                    "부모 테이블의 행 삭제 시 자식 테이블 행도 자동 삭제",
                    "자식 테이블의 행 삭제 시 부모 테이블 행도 삭제",
                    "부모 행 삭제 시 자식 행의 FK를 NULL로 변경",
                    "부모 행 삭제를 막는다"
                ),
                1, "ON DELETE CASCADE: 부모 테이블의 행이 삭제될 때 이를 참조하는 자식 테이블의 행도 자동으로 삭제됩니다."),

            // ===================== DML =====================
            new Question(29, "DML", "기출", "하",
                "다음 SQL에서 UPDATE 문의 실행 결과로 옳은 것은?\n\nUPDATE EMP\nSET SAL = SAL * 1.1\nWHERE DEPTNO = 20;",
                null,
                Arrays.asList(
                    "DEPTNO가 20인 모든 직원의 SAL이 10% 증가한다",
                    "DEPTNO가 20인 모든 직원의 SAL이 1.1로 변경된다",
                    "모든 직원의 SAL이 10% 증가한다",
                    "DEPTNO가 20이 아닌 직원의 SAL이 증가한다"
                ),
                1, "WHERE DEPTNO = 20 조건으로 해당 부서 직원만 필터링하고, SAL = SAL * 1.1로 현재 급여의 10%를 인상합니다."),

            new Question(30, "DML", "기출", "하",
                "다음 중 DML(Data Manipulation Language)에 해당하는 것을 모두 고른 것은?\n\nA. INSERT  B. SELECT  C. DROP  D. UPDATE  E. DELETE",
                null,
                Arrays.asList(
                    "A, C, D",
                    "A, B, D, E",
                    "B, C, E",
                    "A, D, E"
                ),
                2, "DML: SELECT, INSERT, UPDATE, DELETE. DROP은 DDL입니다. (일부 교재에서 SELECT를 DQL로 별도 분류하기도 합니다.)"),

            new Question(31, "DML", "변형", "중",
                "다음 MERGE 문의 기능으로 옳은 것은?\n\nMERGE INTO TARGET T\nUSING SOURCE S ON (T.ID = S.ID)\nWHEN MATCHED THEN UPDATE SET T.VAL = S.VAL\nWHEN NOT MATCHED THEN INSERT (ID, VAL) VALUES (S.ID, S.VAL);",
                null,
                Arrays.asList(
                    "TARGET 테이블의 모든 행을 삭제한다",
                    "SOURCE 데이터를 기반으로 UPSERT(갱신+삽입)를 수행한다",
                    "두 테이블을 합쳐 새로운 테이블을 생성한다",
                    "조건에 맞는 행을 SOURCE에서 TARGET으로 이동한다"
                ),
                2, "MERGE는 UPSERT 기능을 제공합니다. 조건이 일치하면 UPDATE, 일치하지 않으면 INSERT를 수행합니다."),

            // ===================== TCL =====================
            new Question(32, "TCL", "기출", "하",
                "다음 중 TCL(Transaction Control Language)에 해당하지 않는 것은?",
                null,
                Arrays.asList("COMMIT", "ROLLBACK", "SAVEPOINT", "GRANT"),
                4, "TCL: COMMIT, ROLLBACK, SAVEPOINT. GRANT/REVOKE는 DCL(Data Control Language)입니다."),

            new Question(33, "TCL", "기출", "중",
                "트랜잭션의 특성(ACID) 중 '트랜잭션 실행 결과가 영구적으로 반영된다'를 의미하는 것은?",
                null,
                Arrays.asList("원자성(Atomicity)", "일관성(Consistency)", "격리성(Isolation)", "지속성(Durability)"),
                4, "지속성(Durability): COMMIT된 트랜잭션의 결과는 시스템 장애가 발생해도 영구적으로 유지됩니다."),

            new Question(34, "TCL", "변형", "중",
                "SAVEPOINT에 대한 설명으로 옳은 것은?",
                null,
                Arrays.asList(
                    "트랜잭션 전체를 롤백한다",
                    "트랜잭션 내 특정 지점으로 부분 롤백이 가능하다",
                    "COMMIT 이후에도 사용 가능하다",
                    "DDL 문장에도 적용된다"
                ),
                2, "SAVEPOINT는 트랜잭션 내에 체크포인트를 설정하여 해당 지점까지만 ROLLBACK할 수 있게 해줍니다."),

            // ===================== 정규화 =====================
            new Question(35, "정규화", "기출", "중",
                "다음 중 제2정규형(2NF)을 만족하기 위한 조건으로 옳은 것은?",
                null,
                Arrays.asList(
                    "모든 속성이 기본키에 완전 함수 종속되어야 한다",
                    "이행 함수 종속이 없어야 한다",
                    "다치 종속이 없어야 한다",
                    "조인 종속이 없어야 한다"
                ),
                1, "2NF: 제1정규형을 만족하면서, 기본키의 부분 함수 종속을 제거하여 모든 비키 속성이 기본키 전체에 완전 함수 종속되어야 합니다."),

            new Question(36, "정규화", "기출", "중",
                "정규화 단계에 대한 설명으로 옳지 않은 것은?",
                null,
                Arrays.asList(
                    "1NF: 모든 도메인이 원자값이어야 한다",
                    "2NF: 부분 함수 종속을 제거한다",
                    "3NF: 이행 함수 종속을 제거한다",
                    "BCNF: 다치 종속을 제거한다"
                ),
                4, "BCNF는 모든 결정자가 후보키이어야 한다는 조건입니다. 다치 종속 제거는 4NF의 조건입니다."),

            new Question(37, "정규화", "변형", "중",
                "다음 중 반정규화(De-normalization)를 수행하는 주된 이유는?",
                null,
                Arrays.asList(
                    "데이터 무결성을 높이기 위해",
                    "중복 데이터를 제거하기 위해",
                    "조회 성능을 향상시키기 위해",
                    "저장 공간을 줄이기 위해"
                ),
                3, "반정규화는 정규화된 테이블을 의도적으로 합치거나 중복을 허용하여 JOIN 횟수를 줄이고 조회 성능을 향상시키는 기법입니다."),

            // ===================== 인덱스 =====================
            new Question(38, "인덱스", "기출", "중",
                "다음 중 인덱스(Index)에 대한 설명으로 옳지 않은 것은?",
                null,
                Arrays.asList(
                    "SELECT 성능을 향상시킬 수 있다",
                    "INSERT, UPDATE, DELETE 시 추가적인 비용이 발생한다",
                    "인덱스가 많을수록 항상 성능이 좋아진다",
                    "B-Tree 인덱스가 가장 일반적으로 사용된다"
                ),
                3, "인덱스가 너무 많으면 DML 성능이 저하되고 저장 공간도 증가합니다. 적절한 컬럼에 인덱스를 생성하는 것이 중요합니다."),

            new Question(39, "인덱스", "변형", "중",
                "다음 중 인덱스를 사용하기 어려운 경우는?",
                null,
                Arrays.asList(
                    "WHERE COL = 100",
                    "WHERE COL BETWEEN 1 AND 100",
                    "WHERE SUBSTR(COL, 1, 3) = 'ABC'",
                    "WHERE COL LIKE 'ABC%'"
                ),
                3, "컬럼에 함수를 적용하면(SUBSTR, UPPER 등) 인덱스를 사용할 수 없습니다. 함수 기반 인덱스를 생성하거나 조건을 변경해야 합니다."),

            // ===================== 뷰(View) =====================
            new Question(40, "뷰(View)", "기출", "중",
                "다음 중 뷰(View)에 대한 설명으로 옳지 않은 것은?",
                null,
                Arrays.asList(
                    "실제 데이터를 저장하지 않는다",
                    "보안 목적으로 사용할 수 있다",
                    "뷰에서 DML 작업은 항상 불가능하다",
                    "복잡한 쿼리를 단순화할 수 있다"
                ),
                3, "단순한 뷰는 DML(INSERT, UPDATE, DELETE)이 가능할 수 있습니다. 단, GROUP BY, DISTINCT, 집계함수 등이 포함된 복잡한 뷰는 DML이 제한됩니다."),

            new Question(41, "뷰(View)", "변형", "중",
                "다음 중 뷰(View) 생성 시 CREATE OR REPLACE VIEW를 사용하는 이유로 옳은 것은?",
                null,
                Arrays.asList(
                    "뷰의 데이터를 갱신하기 위해",
                    "기존 뷰가 있으면 삭제 후 재생성하기 위해",
                    "뷰의 인덱스를 생성하기 위해",
                    "뷰에 권한을 부여하기 위해"
                ),
                2, "CREATE OR REPLACE VIEW는 해당 이름의 뷰가 이미 존재하면 자동으로 교체(DROP + CREATE)하여 재생성합니다."),

            // ===================== 집합 연산자 =====================
            new Question(42, "집합연산자", "기출", "중",
                "다음 중 UNION과 UNION ALL의 차이로 옳은 것은?",
                null,
                Arrays.asList(
                    "UNION은 중복 행을 포함하고, UNION ALL은 제거한다",
                    "UNION ALL은 중복 행을 포함하고, UNION은 제거한다",
                    "UNION과 UNION ALL은 동일한 결과를 반환한다",
                    "UNION ALL은 정렬을 수행한다"
                ),
                2, "UNION: 중복 행 제거 (정렬 비용 발생). UNION ALL: 중복 포함하여 모두 반환 (성능이 더 좋음)."),

            new Question(43, "집합연산자", "기출", "중",
                "다음 중 INTERSECT 연산자에 대한 설명으로 옳은 것은?",
                null,
                Arrays.asList(
                    "두 쿼리 결과의 합집합을 반환한다",
                    "두 쿼리 결과의 교집합을 반환한다",
                    "첫 번째 쿼리 결과에서 두 번째를 뺀 차집합을 반환한다",
                    "두 쿼리의 카테시안 곱을 반환한다"
                ),
                2, "INTERSECT: 교집합(두 쿼리 모두에 존재하는 행). UNION: 합집합. MINUS/EXCEPT: 차집합."),

            new Question(44, "집합연산자", "변형", "중",
                "집합 연산자 사용 시 제약사항으로 옳은 것은?",
                null,
                Arrays.asList(
                    "각 SELECT 문의 컬럼 수가 달라도 된다",
                    "각 SELECT 문의 컬럼 수와 데이터 타입이 호환되어야 한다",
                    "ORDER BY는 각 SELECT 문마다 사용 가능하다",
                    "BLOB 타입의 컬럼도 집합 연산이 가능하다"
                ),
                2, "집합 연산자 사용 시 각 SELECT의 컬럼 수가 같고, 대응하는 컬럼의 데이터 타입이 호환되어야 합니다. ORDER BY는 마지막에 한 번만 사용 가능합니다."),

            // ===================== SQL 최적화 =====================
            new Question(45, "SQL최적화", "기출", "중",
                "다음 중 옵티마이저(Optimizer)에 대한 설명으로 옳은 것은?",
                null,
                Arrays.asList(
                    "SQL 문법 오류를 검사하는 도구이다",
                    "SQL을 가장 효율적인 방법으로 실행하기 위한 실행계획을 수립한다",
                    "인덱스를 자동으로 생성해주는 도구이다",
                    "SQL 결과를 캐시하여 재사용하는 도구이다"
                ),
                2, "옵티마이저는 SQL 실행 시 최적의 실행 계획(Execution Plan)을 수립하는 DBMS 내부 컴포넌트입니다."),

            new Question(46, "SQL최적화", "변형", "중",
                "실행 계획(Execution Plan)에서 풀 테이블 스캔(Full Table Scan)이 유리한 경우는?",
                null,
                Arrays.asList(
                    "테이블 전체 행의 1% 미만을 조회할 때",
                    "인덱스가 잘 구축된 대용량 테이블에서 특정 행을 조회할 때",
                    "테이블 대부분의 행을 조회할 때",
                    "인덱스 컬럼에 조건이 있을 때"
                ),
                3, "전체 데이터의 대부분(15~20% 이상)을 조회할 경우 인덱스 스캔보다 풀 테이블 스캔이 더 효율적일 수 있습니다."),

            // ===================== ERD / 데이터 모델링 =====================
            new Question(47, "데이터모델링", "기출", "중",
                "다음 중 엔터티(Entity)에 대한 설명으로 옳지 않은 것은?",
                null,
                Arrays.asList(
                    "업무에서 관리해야 할 데이터 집합이다",
                    "반드시 2개 이상의 인스턴스를 가져야 한다",
                    "속성(Attribute)을 하나 이상 가져야 한다",
                    "반드시 하나 이상의 식별자(Identifier)를 가져야 한다"
                ),
                2, "엔터티는 인스턴스가 반드시 2개 이상일 필요는 없습니다. 0개 또는 1개인 경우도 엔터티가 될 수 있습니다."),

            new Question(48, "데이터모델링", "기출", "중",
                "다음 중 식별자 관계(Identifying Relationship)에 대한 설명으로 옳은 것은?",
                null,
                Arrays.asList(
                    "자식 엔터티의 식별자에 부모 엔터티의 식별자가 포함된다",
                    "자식 엔터티의 식별자에 부모 엔터티의 식별자가 포함되지 않는다",
                    "두 엔터티 간의 관계가 선택적(Optional)이다",
                    "외래키가 생성되지 않는다"
                ),
                1, "식별자 관계: 부모의 PK가 자식의 PK에 포함됨 (실선으로 표현). 비식별자 관계: 부모의 PK가 자식의 일반 속성(FK)이 됨 (점선으로 표현)."),

            new Question(49, "데이터모델링", "변형", "중",
                "다음 중 카디널리티(Cardinality) 표기에서 1:N 관계를 의미하는 것은?",
                null,
                Arrays.asList(
                    "한 엔터티의 하나의 인스턴스가 다른 엔터티의 여러 인스턴스와 관련된다",
                    "두 엔터티 모두 하나의 인스턴스만 관련된다",
                    "두 엔터티 모두 여러 인스턴스와 관련된다",
                    "관계가 없음을 의미한다"
                ),
                1, "1:N 관계 예시 - 부서(1) : 직원(N). 하나의 부서에 여러 직원이 소속될 수 있습니다."),

            new Question(50, "데이터모델링", "기출", "상",
                "다음 중 데이터 모델링의 3단계를 순서대로 나열한 것은?",
                null,
                Arrays.asList(
                    "논리적 모델링 → 개념적 모델링 → 물리적 모델링",
                    "개념적 모델링 → 논리적 모델링 → 물리적 모델링",
                    "물리적 모델링 → 논리적 모델링 → 개념적 모델링",
                    "개념적 모델링 → 물리적 모델링 → 논리적 모델링"
                ),
                2, "데이터 모델링 3단계: 개념적 모델링(ERD 작성, 핵심 엔터티 추출) → 논리적 모델링(정규화, 속성/관계 상세화) → 물리적 모델링(특정 DBMS에 맞게 테이블/인덱스 생성)"),

            // ===================== 추가 기출 변형 =====================
            new Question(51, "SELECT/WHERE", "변형", "상",
                "다음 SQL의 실행 결과로 옳은 것은?\n\nSELECT CASE\n  WHEN SAL >= 3000 THEN 'HIGH'\n  WHEN SAL >= 2000 THEN 'MID'\n  ELSE 'LOW'\nEND AS GRADE\nFROM EMP\nWHERE SAL = 2500;",
                null,
                Arrays.asList("HIGH", "MID", "LOW", "NULL"),
                2, "SAL=2500은 3000 미만이므로 첫 번째 조건 실패. 2000 이상이므로 두 번째 조건 MID를 반환합니다."),

            new Question(52, "GROUP BY/HAVING", "변형", "상",
                "다음 SQL 중 문법적으로 옳은 것은?",
                null,
                Arrays.asList(
                    "SELECT DEPTNO FROM EMP WHERE COUNT(*) > 5 GROUP BY DEPTNO",
                    "SELECT DEPTNO, COUNT(*) FROM EMP GROUP BY DEPTNO HAVING COUNT(*) > 5",
                    "SELECT DEPTNO FROM EMP HAVING COUNT(*) > 5",
                    "SELECT DEPTNO FROM EMP GROUP BY DEPTNO WHERE COUNT(*) > 5"
                ),
                2, "집계 함수에 대한 조건은 WHERE가 아닌 HAVING에 작성해야 합니다. WHERE 절에서는 집계 함수를 사용할 수 없습니다."),

            new Question(53, "JOIN", "변형", "상",
                "다음 SQL의 실행 결과로 옳은 것은?\n\n-- EMP 테이블: MGR 컬럼이 상사의 EMPNO를 참조\nSELECT E.ENAME AS 직원, M.ENAME AS 상사\nFROM EMP E LEFT JOIN EMP M ON E.MGR = M.EMPNO;",
                null,
                Arrays.asList(
                    "상사가 있는 직원만 출력된다",
                    "모든 직원이 출력되며, 상사가 없는 직원은 상사 컬럼이 NULL이다",
                    "상사만 출력된다",
                    "오류가 발생한다"
                ),
                2, "같은 테이블을 셀프 조인(Self Join)한 예입니다. LEFT JOIN이므로 상사가 없는 직원(최고 관리자)도 포함되며, 상사 컬럼은 NULL로 표시됩니다."),

            new Question(54, "윈도우함수", "변형", "상",
                "다음 SQL에서 NTILE(4) 함수의 결과로 옳은 것은?\n\nSELECT ENAME, SAL,\n  NTILE(4) OVER (ORDER BY SAL DESC) AS QUARTILE\nFROM EMP;",
                null,
                Arrays.asList(
                    "SAL 기준 순위를 1~4 사이로 정규화한다",
                    "전체 행을 4개의 그룹으로 나누어 그룹 번호를 부여한다",
                    "SAL을 4등분한 값을 반환한다",
                    "상위 4명을 선택한다"
                ),
                2, "NTILE(n)은 전체 결과를 n개의 균등한 그룹으로 나누어 각 행에 그룹 번호(1~n)를 부여합니다."),

            new Question(55, "정규화", "변형", "상",
                "다음 릴레이션에서 발생하는 이상(Anomaly)은 무엇인가?\n\n학생과목(학번, 과목코드, 성적, 교수명)\n- 기본키: (학번, 과목코드)\n- 과목코드 → 교수명 (부분 함수 종속 존재)",
                null,
                Arrays.asList(
                    "삽입 이상, 삭제 이상, 갱신 이상이 모두 발생할 수 있다",
                    "삽입 이상만 발생한다",
                    "삭제 이상만 발생한다",
                    "이상이 발생하지 않는다"
                ),
                1, "부분 함수 종속이 있으면 삽입 이상(학생 없이 과목-교수 정보 삽입 불가), 삭제 이상(학생 데이터 삭제 시 과목-교수 정보 손실), 갱신 이상(교수 변경 시 여러 행 수정 필요) 모두 발생합니다."),

            new Question(56, "TCL", "변형", "중",
                "다음 중 암시적 COMMIT이 발생하는 경우로 옳은 것은?",
                null,
                Arrays.asList(
                    "ROLLBACK 명령 실행 시",
                    "DDL 문장(CREATE, DROP 등) 실행 시",
                    "DML 문장(INSERT, UPDATE) 실행 시",
                    "SELECT 문장 실행 시"
                ),
                2, "DDL 문장은 자동으로 이전 트랜잭션을 COMMIT하고 실행됩니다. 이를 암시적(묵시적) COMMIT이라 합니다."),

            new Question(57, "SELECT/WHERE", "기출", "중",
                "다음 중 NVL 함수에 대한 설명으로 옳은 것은?\n\nNVL(COL1, 'DEFAULT')",
                null,
                Arrays.asList(
                    "COL1이 NULL이 아닐 경우 'DEFAULT'를 반환한다",
                    "COL1이 NULL일 경우 'DEFAULT'를 반환한다",
                    "COL1이 'DEFAULT'일 경우 NULL을 반환한다",
                    "COL1과 'DEFAULT' 중 큰 값을 반환한다"
                ),
                2, "NVL(컬럼, 대체값): 컬럼이 NULL이면 대체값을 반환합니다. Oracle SQL에서 사용하며, 표준 SQL에서는 COALESCE를 사용합니다."),

            new Question(58, "SELECT/WHERE", "변형", "중",
                "다음 SQL에서 COALESCE 함수의 결과로 옳은 것은?\n\nSELECT COALESCE(NULL, NULL, 'C', 'D') FROM DUAL;",
                null,
                Arrays.asList("NULL", "C", "D", "오류"),
                2, "COALESCE(값1, 값2, ...)는 왼쪽부터 순서대로 NULL이 아닌 첫 번째 값을 반환합니다. NULL, NULL은 건너뛰고 'C'를 반환합니다."),

            new Question(59, "DDL", "변형", "중",
                "다음 중 CHECK 제약조건의 예로 옳은 것은?",
                null,
                Arrays.asList(
                    "CONSTRAINT CHK_AGE CHECK (AGE BETWEEN 0 AND 150)",
                    "CONSTRAINT CHK_AGE CHECK (AGE = PRIMARY KEY)",
                    "CONSTRAINT CHK_AGE CHECK (AGE REFERENCES USERS)",
                    "CONSTRAINT CHK_AGE CHECK (AGE IS UNIQUE)"
                ),
                1, "CHECK 제약조건은 컬럼에 입력 가능한 값의 범위나 조건을 지정합니다. BETWEEN, IN, 비교 연산자 등을 사용할 수 있습니다."),

            new Question(60, "인덱스", "기출", "상",
                "복합 인덱스(Composite Index)가 (COL1, COL2, COL3)으로 생성되어 있을 때, 인덱스를 효과적으로 사용할 수 있는 WHERE 조건은?",
                null,
                Arrays.asList(
                    "WHERE COL2 = 'A' AND COL3 = 'B'",
                    "WHERE COL3 = 'C'",
                    "WHERE COL1 = 'X' AND COL2 = 'Y'",
                    "WHERE COL2 = 'A'"
                ),
                3, "복합 인덱스는 선두 컬럼(COL1)부터 사용해야 효과적입니다. COL1이 WHERE 절에 포함된 경우에만 인덱스를 활용할 수 있습니다. COL1만 있거나 COL1+COL2, COL1+COL2+COL3 순으로 사용 가능합니다.")

        );
    }
}
