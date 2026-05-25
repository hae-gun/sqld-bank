import { SUBJECT1 } from './questions_subject1';
import { SUBJECT2A } from './questions_subject2a';
import { SUBJECT2B } from './questions_subject2b';

// 기존 기출 60문제
const ORIGINAL = [
  {
    id: 1, category: 'SELECT/WHERE', subject: '2과목', type: '기출', difficulty: '하',
    question: '다음 SQL의 실행 결과로 옳은 것은?\n\nSELECT ENAME, SAL\nFROM EMP\nWHERE SAL BETWEEN 2000 AND 3000;',
    options: [
      'SAL이 2000 이상 3000 미만인 직원을 조회한다',
      'SAL이 2000 초과 3000 이하인 직원을 조회한다',
      'SAL이 2000 이상 3000 이하인 직원을 조회한다',
      'SAL이 2000 미만이거나 3000 초과인 직원을 조회한다',
    ],
    answer: 3,
    explanation: 'BETWEEN A AND B는 A 이상 B 이하 (양쪽 포함)를 의미합니다.',
  },
  {
    id: 2, category: 'SELECT/WHERE', subject: '2과목', type: '기출', difficulty: '하',
    question: 'NULL 값 처리에 대한 설명으로 옳지 않은 것은?',
    options: [
      'NULL과의 산술 연산 결과는 NULL이다',
      'NULL = NULL의 결과는 TRUE이다',
      'NULL IS NULL의 결과는 TRUE이다',
      '집계 함수(SUM, AVG 등)는 NULL을 무시한다',
    ],
    answer: 2,
    explanation: 'NULL = NULL은 UNKNOWN을 반환합니다. NULL 비교는 IS NULL / IS NOT NULL을 사용해야 합니다.',
  },
  {
    id: 3, category: 'SELECT/WHERE', subject: '2과목', type: '변형', difficulty: '중',
    question: '다음 SQL에서 WHERE 조건의 결과로 선택되는 행은?\n\nSELECT * FROM T\nWHERE COL1 = NULL;',
    options: [
      'COL1이 NULL인 모든 행',
      'COL1이 NULL이 아닌 모든 행',
      '아무 행도 선택되지 않는다',
      '모든 행이 선택된다',
    ],
    answer: 3,
    explanation: 'NULL과의 비교연산(=, <>, !=)은 항상 UNKNOWN을 반환하므로 WHERE 조건을 만족하는 행이 없습니다.',
  },
  {
    id: 5, category: 'SELECT/WHERE', subject: '2과목', type: '변형', difficulty: '중',
    question: '다음 중 LIKE 연산자에 대한 설명으로 옳은 것은?',
    options: [
      '% 는 정확히 1개의 문자를 의미한다',
      '_ 는 0개 이상의 문자를 의미한다',
      '% 는 0개 이상의 임의 문자열을 의미한다',
      '_ 와 % 는 동일한 의미이다',
    ],
    answer: 3,
    explanation: 'LIKE에서 %는 0개 이상의 임의 문자열, _는 정확히 1개의 임의 문자를 의미합니다.',
  },
  {
    id: 6, category: 'GROUP BY/HAVING', subject: '2과목', type: '기출', difficulty: '중',
    question: '다음 SQL의 실행 결과로 옳은 것은?\n\nSELECT DEPTNO, COUNT(*), AVG(SAL)\nFROM EMP\nGROUP BY DEPTNO\nHAVING AVG(SAL) > 2000;',
    options: [
      '전체 직원 중 SAL > 2000인 직원을 부서별로 집계한다',
      '부서별로 집계한 후, 부서 평균급여가 2000 초과인 부서만 출력한다',
      'WHERE AVG(SAL) > 2000 과 동일한 결과이다',
      'GROUP BY 없이도 동일한 결과를 얻을 수 있다',
    ],
    answer: 2,
    explanation: 'HAVING은 GROUP BY로 집계된 결과에 조건을 적용합니다.',
  },
  {
    id: 7, category: 'GROUP BY/HAVING', subject: '2과목', type: '기출', difficulty: '중',
    question: 'SQL 실행 순서로 올바른 것은?',
    options: [
      'SELECT → FROM → WHERE → GROUP BY → HAVING → ORDER BY',
      'FROM → WHERE → GROUP BY → HAVING → SELECT → ORDER BY',
      'FROM → SELECT → WHERE → GROUP BY → HAVING → ORDER BY',
      'WHERE → FROM → GROUP BY → HAVING → SELECT → ORDER BY',
    ],
    answer: 2,
    explanation: 'SQL 논리적 실행 순서: FROM → WHERE → GROUP BY → HAVING → SELECT → ORDER BY',
  },
  {
    id: 8, category: 'GROUP BY/HAVING', subject: '2과목', type: '변형', difficulty: '중',
    question: '다음 SQL에서 오류가 발생하는 이유는?\n\nSELECT DEPTNO, ENAME, COUNT(*)\nFROM EMP\nGROUP BY DEPTNO;',
    options: [
      'COUNT(*) 는 GROUP BY에 사용할 수 없다',
      'ENAME이 GROUP BY 절에 없어서 오류 발생',
      'DEPTNO는 SELECT에서 사용할 수 없다',
      'FROM 절이 잘못되었다',
    ],
    answer: 2,
    explanation: 'GROUP BY 사용 시 SELECT 절에는 GROUP BY에 명시된 컬럼이나 집계 함수만 올 수 있습니다.',
  },
  {
    id: 9, category: 'GROUP BY/HAVING', subject: '2과목', type: '변형', difficulty: '상',
    question: '다음 SQL의 결과로 옳은 것은?\n\nSELECT COUNT(*), COUNT(COL1)\nFROM T;\n\n(T 테이블: 총 5건, COL1이 NULL인 행 2건)',
    options: ['5, 5', '3, 3', '5, 3', '3, 5'],
    answer: 3,
    explanation: 'COUNT(*)는 NULL 포함 모든 행을 셉니다(5). COUNT(컬럼명)은 NULL을 제외합니다(3).',
  },
  {
    id: 10, category: 'JOIN', subject: '2과목', type: '기출', difficulty: '중',
    question: '다음 중 INNER JOIN에 대한 설명으로 옳은 것은?',
    options: [
      '두 테이블의 모든 행을 반환한다',
      '왼쪽 테이블의 모든 행과 일치하는 오른쪽 행을 반환한다',
      '두 테이블에서 조인 조건을 만족하는 행만 반환한다',
      '조인 조건이 없어도 실행 가능하다',
    ],
    answer: 3,
    explanation: 'INNER JOIN은 두 테이블 모두에 조인 조건을 만족하는 행만 결과로 반환합니다.',
  },
  {
    id: 11, category: 'JOIN', subject: '2과목', type: '기출', difficulty: '중',
    question: 'LEFT OUTER JOIN에 대한 설명으로 옳은 것은?',
    options: [
      '오른쪽 테이블의 모든 행을 반환한다',
      '왼쪽 테이블의 모든 행을 반환하고, 매칭되지 않는 오른쪽은 NULL로 채운다',
      '두 테이블의 교집합만 반환한다',
      '두 테이블의 합집합을 반환한다',
    ],
    answer: 2,
    explanation: 'LEFT OUTER JOIN은 왼쪽 테이블의 모든 행을 반환하며, 매칭 행이 없으면 NULL로 채웁니다.',
  },
  {
    id: 12, category: 'JOIN', subject: '2과목', type: '변형', difficulty: '상',
    question: '다음 SQL의 실행 결과 행 수는?\n\n-- EMP: 14건, DEPT: 4건\nSELECT *\nFROM EMP CROSS JOIN DEPT;',
    options: ['14', '4', '18', '56'],
    answer: 4,
    explanation: 'CROSS JOIN(카테시안 곱)은 두 테이블의 모든 조합을 반환합니다. 14 × 4 = 56건',
  },
  {
    id: 13, category: 'JOIN', subject: '2과목', type: '기출', difficulty: '중',
    question: 'NATURAL JOIN에 대한 설명으로 옳지 않은 것은?',
    options: [
      '두 테이블에서 같은 이름을 가진 컬럼을 자동으로 조인 조건으로 사용한다',
      '같은 이름의 컬럼이 여러 개이면 모두 조인 조건에 포함된다',
      '조인에 사용된 컬럼은 결과에 중복 출력된다',
      'USING 절로 대체할 수 있다',
    ],
    answer: 3,
    explanation: 'NATURAL JOIN에서는 동일한 이름의 컬럼이 결과에 한 번만 출력됩니다(중복 없음).',
  },
  {
    id: 15, category: '서브쿼리', subject: '2과목', type: '기출', difficulty: '중',
    question: '다음 중 스칼라 서브쿼리(Scalar Subquery)에 대한 설명으로 옳은 것은?',
    options: [
      'WHERE 절에서만 사용 가능하다',
      '반드시 여러 행을 반환해야 한다',
      '단일 행, 단일 열을 반환하며 SELECT 절에 사용 가능하다',
      'FROM 절에서만 사용 가능하다',
    ],
    answer: 3,
    explanation: '스칼라 서브쿼리는 단일 값(1행 1열)을 반환하며 SELECT, WHERE, HAVING 절 등에서 사용할 수 있습니다.',
  },
  {
    id: 17, category: '서브쿼리', subject: '2과목', type: '변형', difficulty: '상',
    question: '다음 SQL의 결과로 옳은 것은?\n\nSELECT ENAME FROM EMP\nWHERE SAL > ALL\n  (SELECT SAL FROM EMP WHERE DEPTNO = 30);',
    options: [
      '30번 부서 직원보다 급여가 높은 직원',
      '30번 부서 직원 중 가장 낮은 급여보다 높은 직원',
      '30번 부서 직원 중 가장 높은 급여보다 높은 직원',
      '30번 부서가 아닌 모든 직원',
    ],
    answer: 3,
    explanation: '> ALL은 서브쿼리 결과의 최대값보다 크다는 의미입니다.',
  },
  {
    id: 19, category: '윈도우함수', subject: '2과목', type: '기출', difficulty: '중',
    question: '다음 중 윈도우 함수(Window Function)에 대한 설명으로 옳지 않은 것은?',
    options: [
      'OVER() 절을 반드시 사용해야 한다',
      '결과 행의 수를 줄인다',
      'PARTITION BY로 그룹을 나눌 수 있다',
      'ORDER BY를 함께 사용할 수 있다',
    ],
    answer: 2,
    explanation: '윈도우 함수는 GROUP BY와 달리 행을 집약하지 않습니다. 결과 행의 수는 원본과 동일합니다.',
  },
  {
    id: 20, category: '윈도우함수', subject: '2과목', type: '기출', difficulty: '중',
    question: 'RANK()와 DENSE_RANK()의 차이로 옳은 것은?\n\nSELECT ENAME, SAL,\n  RANK() OVER (ORDER BY SAL DESC) RNK,\n  DENSE_RANK() OVER (ORDER BY SAL DESC) DRNK\nFROM EMP;',
    options: [
      'RANK()와 DENSE_RANK()는 항상 동일한 결과이다',
      'RANK()는 동점 시 순위를 건너뛰고, DENSE_RANK()는 건너뛰지 않는다',
      'DENSE_RANK()는 동점 시 순위를 건너뛰고, RANK()는 건너뛰지 않는다',
      'RANK()는 중복을 허용하지 않는다',
    ],
    answer: 2,
    explanation: 'RANK(): 동점이면 같은 순위, 다음 순위 건너뜀(1,2,2,4). DENSE_RANK(): 동점이면 같은 순위, 건너뛰지 않음(1,2,2,3).',
  },
  {
    id: 21, category: '윈도우함수', subject: '2과목', type: '변형', difficulty: '상',
    question: 'LAG 함수의 용도로 옳은 것은?\n\nSELECT ENAME, SAL,\n  LAG(SAL, 1, 0) OVER (ORDER BY SAL) PREV_SAL\nFROM EMP;',
    options: [
      '다음 행의 SAL 값을 가져온다',
      '이전 행의 SAL 값을 가져오고, 이전 행이 없으면 0을 반환한다',
      'SAL의 누적 합계를 계산한다',
      'SAL의 이동 평균을 계산한다',
    ],
    answer: 2,
    explanation: 'LAG(컬럼, 오프셋, 기본값)은 현재 행 기준으로 이전 행의 값을 가져옵니다.',
  },
  {
    id: 23, category: '윈도우함수', subject: '2과목', type: '기출', difficulty: '중',
    question: 'ROW_NUMBER() 함수에 대한 설명으로 옳은 것은?',
    options: [
      '동점 행에 같은 번호를 부여한다',
      '순위를 건너뛰지 않는다',
      '각 행에 고유한 순번을 부여하며 동점을 허용하지 않는다',
      'PARTITION BY 없이는 사용할 수 없다',
    ],
    answer: 3,
    explanation: 'ROW_NUMBER()는 동점 여부와 관계없이 각 행에 고유한 순번(1,2,3,4...)을 부여합니다.',
  },
  {
    id: 24, category: 'DDL', subject: '2과목', type: '기출', difficulty: '하',
    question: '다음 중 DDL(Data Definition Language)에 해당하지 않는 것은?',
    options: ['CREATE', 'ALTER', 'DROP', 'UPDATE'],
    answer: 4,
    explanation: 'DDL: CREATE, ALTER, DROP, TRUNCATE, RENAME. UPDATE는 DML입니다.',
  },
  {
    id: 25, category: 'DDL', subject: '2과목', type: '기출', difficulty: '중',
    question: 'TRUNCATE와 DELETE의 차이로 옳은 것은?',
    options: [
      'TRUNCATE는 WHERE 절을 사용할 수 있다',
      'DELETE는 자동으로 COMMIT된다',
      'TRUNCATE는 DDL로 자동 COMMIT되며 롤백이 불가능하다',
      'DELETE와 TRUNCATE는 성능이 동일하다',
    ],
    answer: 3,
    explanation: 'TRUNCATE는 DDL이므로 즉시 자동 COMMIT. DELETE는 DML로 ROLLBACK 가능합니다.',
  },
  {
    id: 26, category: 'DDL', subject: '2과목', type: '변형', difficulty: '중',
    question: '다음 제약조건 중 한 테이블에 하나만 존재할 수 있는 것은?',
    options: ['UNIQUE', 'NOT NULL', 'CHECK', 'PRIMARY KEY'],
    answer: 4,
    explanation: 'PRIMARY KEY는 테이블당 1개만 존재할 수 있습니다.',
  },
  {
    id: 27, category: 'DDL', subject: '2과목', type: '변형', difficulty: '중',
    question: 'FOREIGN KEY의 ON DELETE CASCADE 옵션 설명으로 옳은 것은?',
    options: [
      '부모 테이블의 행 삭제 시 자식 테이블 행도 자동 삭제',
      '자식 테이블의 행 삭제 시 부모 테이블 행도 삭제',
      '부모 행 삭제 시 자식 행의 FK를 NULL로 변경',
      '부모 행 삭제를 막는다',
    ],
    answer: 1,
    explanation: 'ON DELETE CASCADE: 부모 행 삭제 시 자식 테이블의 관련 행도 자동으로 삭제됩니다.',
  },
  {
    id: 29, category: 'DML', subject: '2과목', type: '기출', difficulty: '하',
    question: '다음 중 DML(Data Manipulation Language)에 해당하는 것을 모두 고른 것은?\n\nA. INSERT  B. SELECT  C. DROP  D. UPDATE  E. DELETE',
    options: ['A, C, D', 'A, B, D, E', 'B, C, E', 'A, D, E'],
    answer: 2,
    explanation: 'DML: SELECT, INSERT, UPDATE, DELETE. DROP은 DDL입니다.',
  },
  {
    id: 31, category: 'TCL', subject: '2과목', type: '기출', difficulty: '하',
    question: '다음 중 TCL(Transaction Control Language)에 해당하지 않는 것은?',
    options: ['COMMIT', 'ROLLBACK', 'SAVEPOINT', 'GRANT'],
    answer: 4,
    explanation: 'TCL: COMMIT, ROLLBACK, SAVEPOINT. GRANT/REVOKE는 DCL입니다.',
  },
  {
    id: 32, category: 'TCL', subject: '2과목', type: '기출', difficulty: '중',
    question: '트랜잭션의 특성(ACID) 중 "트랜잭션 실행 결과가 영구적으로 반영된다"를 의미하는 것은?',
    options: ['원자성(Atomicity)', '일관성(Consistency)', '격리성(Isolation)', '지속성(Durability)'],
    answer: 4,
    explanation: '지속성(Durability): COMMIT된 트랜잭션의 결과는 장애 발생해도 영구적으로 유지됩니다.',
  },
  {
    id: 35, category: '정규화', subject: '1과목', type: '기출', difficulty: '중',
    question: '다음 중 제2정규형(2NF)을 만족하기 위한 조건으로 옳은 것은?',
    options: [
      '모든 속성이 기본키에 완전 함수 종속되어야 한다',
      '이행 함수 종속이 없어야 한다',
      '다치 종속이 없어야 한다',
      '조인 종속이 없어야 한다',
    ],
    answer: 1,
    explanation: '2NF: 1NF를 만족하면서, 기본키의 부분 함수 종속을 제거해야 합니다.',
  },
  {
    id: 36, category: '정규화', subject: '1과목', type: '기출', difficulty: '중',
    question: '정규화 단계에 대한 설명으로 옳지 않은 것은?',
    options: [
      '1NF: 모든 도메인이 원자값이어야 한다',
      '2NF: 부분 함수 종속을 제거한다',
      '3NF: 이행 함수 종속을 제거한다',
      'BCNF: 다치 종속을 제거한다',
    ],
    answer: 4,
    explanation: 'BCNF는 모든 결정자가 후보키이어야 하는 조건. 다치 종속 제거는 4NF의 조건입니다.',
  },
  {
    id: 39, category: '인덱스', subject: '2과목', type: '기출', difficulty: '중',
    question: '다음 중 인덱스(Index)에 대한 설명으로 옳지 않은 것은?',
    options: [
      'SELECT 성능을 향상시킬 수 있다',
      'INSERT, UPDATE, DELETE 시 추가적인 비용이 발생한다',
      '인덱스가 많을수록 항상 성능이 좋아진다',
      'B-Tree 인덱스가 가장 일반적으로 사용된다',
    ],
    answer: 3,
    explanation: '인덱스가 너무 많으면 DML 성능이 저하되고 저장 공간도 증가합니다.',
  },
  {
    id: 43, category: '집합연산자', subject: '2과목', type: '기출', difficulty: '중',
    question: '다음 중 UNION과 UNION ALL의 차이로 옳은 것은?',
    options: [
      'UNION은 중복 행을 포함하고, UNION ALL은 제거한다',
      'UNION ALL은 중복 행을 포함하고, UNION은 제거한다',
      'UNION과 UNION ALL은 동일한 결과를 반환한다',
      'UNION ALL은 정렬을 수행한다',
    ],
    answer: 2,
    explanation: 'UNION: 중복 행 제거. UNION ALL: 중복 포함하여 모두 반환(성능 유리).',
  },
  {
    id: 46, category: 'SQL최적화', subject: '2과목', type: '기출', difficulty: '중',
    question: '다음 중 옵티마이저(Optimizer)에 대한 설명으로 옳은 것은?',
    options: [
      'SQL 문법 오류를 검사하는 도구이다',
      'SQL을 가장 효율적인 방법으로 실행하기 위한 실행계획을 수립한다',
      '인덱스를 자동으로 생성해주는 도구이다',
      'SQL 결과를 캐시하여 재사용하는 도구이다',
    ],
    answer: 2,
    explanation: '옵티마이저는 SQL 실행 시 최적의 실행 계획(Execution Plan)을 수립하는 DBMS 내부 컴포넌트입니다.',
  },
  {
    id: 48, category: '데이터모델링이해', subject: '1과목', type: '기출', difficulty: '중',
    question: '다음 중 엔터티(Entity)에 대한 설명으로 옳지 않은 것은?',
    options: [
      '업무에서 관리해야 할 데이터 집합이다',
      '반드시 2개 이상의 인스턴스를 가져야 한다',
      '속성(Attribute)을 하나 이상 가져야 한다',
      '반드시 하나 이상의 식별자(Identifier)를 가져야 한다',
    ],
    answer: 2,
    explanation: '엔터티는 인스턴스가 반드시 2개 이상일 필요는 없습니다.',
  },
  {
    id: 55, category: 'NULL처리', subject: '2과목', type: '기출', difficulty: '중',
    question: '다음 중 NVL 함수에 대한 설명으로 옳은 것은?\n\nNVL(COL1, \'DEFAULT\')',
    options: [
      'COL1이 NULL이 아닐 경우 \'DEFAULT\'를 반환한다',
      'COL1이 NULL일 경우 \'DEFAULT\'를 반환한다',
      'COL1이 \'DEFAULT\'일 경우 NULL을 반환한다',
      'COL1과 \'DEFAULT\' 중 큰 값을 반환한다',
    ],
    answer: 2,
    explanation: 'NVL(컬럼, 대체값): 컬럼이 NULL이면 대체값을 반환합니다.',
  },
  {
    id: 60, category: 'ORDER BY', subject: '2과목', type: '기출', difficulty: '중',
    question: '다음 중 ORDER BY 절에 대한 설명으로 옳지 않은 것은?',
    options: [
      'ASC는 오름차순, DESC는 내림차순 정렬이다',
      'ORDER BY 절은 SELECT 문에서 가장 마지막에 실행된다',
      'NULL 값은 오름차순 정렬 시 항상 맨 앞에 온다',
      '컬럼명 대신 SELECT 절의 순서 번호로 정렬할 수 있다',
    ],
    answer: 3,
    explanation: 'NULL 정렬 순서는 DBMS마다 다릅니다. Oracle ASC 시 NULL 마지막, MySQL은 맨 앞. "항상 맨 앞"은 옳지 않습니다.',
  },
];

// 전체 문제 합치기
export const QUESTIONS = [
  ...ORIGINAL,
  ...SUBJECT1,
  ...SUBJECT2A,
  ...SUBJECT2B,
];

export function getRandomQuestion(category = null, subject = null) {
  let pool = QUESTIONS;
  if (category) pool = pool.filter(q => q.category === category);
  if (subject) pool = pool.filter(q => q.subject === subject);
  if (pool.length === 0) pool = QUESTIONS;
  return pool[Math.floor(Math.random() * pool.length)];
}

export function getCategories() {
  return [...new Set(QUESTIONS.map(q => q.category))].sort();
}

export function getStats() {
  return {
    total: QUESTIONS.length,
    subject1: QUESTIONS.filter(q => q.subject === '1과목').length,
    subject2: QUESTIONS.filter(q => q.subject === '2과목').length,
    기출: QUESTIONS.filter(q => q.type === '기출').length,
    변형: QUESTIONS.filter(q => q.type === '변형').length,
    하: QUESTIONS.filter(q => q.difficulty === '하').length,
    중: QUESTIONS.filter(q => q.difficulty === '중').length,
    상: QUESTIONS.filter(q => q.difficulty === '상').length,
  };
}
