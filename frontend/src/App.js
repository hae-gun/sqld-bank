import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import './App.css';
import { AuthProvider, useAuth } from './AuthContext';
import AuthScreen from './AuthScreen';
import HistoryCard from './HistoryCard';

// ─── 카테고리 정의 ─────────────────────────────
const ALL_CATEGORIES = [
  { key: null,               subject: null,   label: '전체',    icon: '📚' },
  { key: '데이터모델링이해', subject: '1과목', label: '모델링이해', icon: '📐' },
  { key: '엔터티',           subject: '1과목', label: '엔터티',  icon: '🗂️' },
  { key: '속성',             subject: '1과목', label: '속성',    icon: '🔖' },
  { key: '관계',             subject: '1과목', label: '관계',    icon: '🔁' },
  { key: '식별자',           subject: '1과목', label: '식별자',  icon: '🔑' },
  { key: '정규화',           subject: '1과목', label: '정규화',  icon: '📏' },
  { key: '반정규화',         subject: '1과목', label: '반정규화', icon: '↩️' },
  { key: '관계와조인',       subject: '1과목', label: '관계&조인', icon: '🔗' },
  { key: '트랜잭션',         subject: '1과목', label: '트랜잭션', icon: '🔄' },
  { key: 'SELECT/WHERE',    subject: '2과목', label: 'SELECT',  icon: '🔍' },
  { key: 'GROUP BY/HAVING', subject: '2과목', label: 'GROUP BY', icon: '📊' },
  { key: 'ORDER BY',        subject: '2과목', label: 'ORDER BY', icon: '↕️' },
  { key: '함수',            subject: '2과목', label: '함수',    icon: '⚙️' },
  { key: 'NULL처리',        subject: '2과목', label: 'NULL',    icon: '∅' },
  { key: 'JOIN',            subject: '2과목', label: 'JOIN',    icon: '🔗' },
  { key: '서브쿼리',        subject: '2과목', label: '서브쿼리', icon: '🧩' },
  { key: '집합연산자',      subject: '2과목', label: '집합',    icon: '∪' },
  { key: '계층형질의',      subject: '2과목', label: '계층형',  icon: '🌳' },
  { key: '뷰',              subject: '2과목', label: '뷰',      icon: '👁️' },
  { key: '윈도우함수',      subject: '2과목', label: '윈도우',  icon: '🪟' },
  { key: 'DDL',             subject: '2과목', label: 'DDL',     icon: '🏗️' },
  { key: 'DML',             subject: '2과목', label: 'DML',     icon: '✏️' },
  { key: 'TCL',             subject: '2과목', label: 'TCL',     icon: '🔒' },
  { key: 'DCL',             subject: '2과목', label: 'DCL',     icon: '🛡️' },
  { key: '절차형SQL',       subject: '2과목', label: '절차형SQL', icon: '📝' },
  { key: '옵티마이저',      subject: '2과목', label: '옵티마이저', icon: '🚀' },
  { key: '인덱스',          subject: '2과목', label: '인덱스',  icon: '⚡' },
  { key: '실행계획',        subject: '2과목', label: '실행계획', icon: '📋' },
  { key: '조인수행원리',    subject: '2과목', label: '조인원리', icon: '⚙️' },
  { key: 'SQL최적화',       subject: '2과목', label: 'SQL최적화', icon: '💡' },
];

const SUBJECT_TABS = [
  { key: null,   label: '전체' },
  { key: '1과목', label: '1과목' },
  { key: '2과목', label: '2과목' },
];

const DIFFICULTY_MAP = {
  '하': { color: '#4caf82' },
  '중': { color: '#f7a84f' },
  '상': { color: '#e55a6f' },
};
const TYPE_MAP = {
  '기출': { color: '#4f8ef7' },
  '변형': { color: '#a78bfa' },
};

// 히스토리 아이템 생성 헬퍼
function makeHistoryItem(backendId, question, correct, selectedOption, retriedCorrectly, retriedOption) {
  return {
    id: backendId ?? Date.now(),
    backendId: backendId ?? null,
    questionId: question.id,
    question,
    text: question.question.split('\n')[0].substring(0, 38) + '…',
    correct,
    selectedOption,
    category: question.category,
    subject: question.subject || '2과목',
    retriedCorrectly: retriedCorrectly ?? null,
    retriedOption: retriedOption ?? null,
    answeredAt: new Date().toISOString(),
  };
}

// ─── 루트: AuthProvider 감싸기 ───────────────────
export default function App() {
  return (
    <AuthProvider>
      <AppInner />
    </AuthProvider>
  );
}

// ─── 인증 게이트 ────────────────────────────────
function AppInner() {
  const { user, authHeader, logout } = useAuth();
  const [authMode, setAuthMode] = useState(() => user ? 'app' : 'screen');

  useEffect(() => {
    if (user) setAuthMode('app');
  }, [user]);

  if (authMode === 'screen') {
    return <AuthScreen onGuest={() => setAuthMode('app')} />;
  }
  return <QuizApp user={user} authHeader={authHeader} onLogout={logout} />;
}

// ─── 퀴즈 앱 (로그인/게스트 공통) ───────────────
function QuizApp({ user, authHeader, onLogout }) {
  const [questions, setQuestions]         = useState([]);   // API에서 로드한 전체 문제
  const [qLoading, setQLoading]           = useState(true); // 문제 로딩 중
  const [qError, setQError]               = useState(false);
  const [stats, setStats]                 = useState(null);
  const [question, setQuestion]           = useState(null);
  const [selectedCat, setSelectedCat]     = useState(null);
  const [selectedSub, setSelectedSub]     = useState(null);
  const [selectedAns, setSelectedAns]     = useState(null);
  const [showAnswer, setShowAnswer]       = useState(false);
  const [score, setScore]                 = useState({ correct: 0, total: 0 });
  const [history, setHistory]             = useState([]);
  const [mainTab, setMainTab]             = useState('quiz');
  const [subjectFilter, setSubjectFilter] = useState(null);
  const [streak, setStreak]               = useState(0);
  const [animation, setAnimation]         = useState('');
  const [usedIds, setUsedIds]             = useState([]);
  const [historyLoading, setHistoryLoading] = useState(false);
  const [historyFilter, setHistoryFilter]   = useState('all'); // 'all' | 'correct' | 'wrong' | 'retry'

  // 앱 시작 시 백엔드에서 전체 문제 로드
  useEffect(() => {
    setQLoading(true);
    Promise.all([
      axios.get('/api/questions'),
      axios.get('/api/questions/stats'),
    ])
      .then(([qRes, sRes]) => {
        setQuestions(qRes.data);
        setStats(sRes.data);
        setQError(false);
      })
      .catch(() => setQError(true))
      .finally(() => setQLoading(false));
  }, []);

  // 문제가 로드된 후 최초 문제 선택
  useEffect(() => {
    if (questions.length > 0 && !question) {
      const first = questions[Math.floor(Math.random() * questions.length)];
      setUsedIds([first.id]);
      setQuestion(first);
      setAnimation('slide-in');
    }
  }, [questions]); // eslint-disable-line react-hooks/exhaustive-deps

  // 로그인 시 서버에서 이력 로드 (문제도 이미 로드된 후)
  useEffect(() => {
    if (!user || !authHeader?.Authorization || questions.length === 0) return;
    setHistoryLoading(true);
    axios.get('/api/history', { headers: authHeader })
      .then(res => {
        const items = res.data.map(h => {
          const q = questions.find(qq => qq.id === h.questionId);
          if (!q) return null;
          return makeHistoryItem(h.id, q, h.answeredCorrectly, h.selectedOption,
            h.retriedCorrectly, h.retriedOption);
        }).filter(Boolean);
        setHistory(items);
        const correct = items.filter(i => i.correct).length;
        setScore({ correct, total: items.length });
      })
      .catch(() => {})
      .finally(() => setHistoryLoading(false));
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [user, questions]);

  const pickQuestion = useCallback((cat, sub, used, qs) => {
    let pool = qs
      .filter(q => !cat || q.category === cat)
      .filter(q => !sub || q.subject === sub)
      .filter(q => !used.includes(q.id));
    if (pool.length === 0) {
      pool = qs
        .filter(q => !cat || q.category === cat)
        .filter(q => !sub || q.subject === sub);
      setUsedIds([]);
    }
    return pool[Math.floor(Math.random() * pool.length)];
  }, []);

  const loadQuestion = useCallback((cat, sub, used) => {
    if (questions.length === 0) return;
    setSelectedAns(null);
    setShowAnswer(false);
    setAnimation('slide-out');
    setTimeout(() => {
      const next = pickQuestion(cat, sub, used, questions);
      setUsedIds(prev => [...prev, next.id]);
      setQuestion(next);
      setAnimation('slide-in');
    }, 180);
  }, [pickQuestion, questions]);

  const handleCatChange = (cat) => {
    setSelectedCat(cat);
    setUsedIds([]);
    loadQuestion(cat, selectedSub, []);
  };

  const handleSubjectFilter = (sub) => {
    setSubjectFilter(sub);
    setSelectedCat(null);
    setSelectedSub(sub);
    setUsedIds([]);
    loadQuestion(null, sub, []);
  };

  const handleAnswer = async (idx) => {
    if (selectedAns !== null || showAnswer) return;
    setSelectedAns(idx);
    setShowAnswer(true);
    const correct = idx + 1 === question.answer;
    setScore(p => ({ correct: p.correct + (correct ? 1 : 0), total: p.total + 1 }));
    setStreak(p => correct ? p + 1 : 0);

    let backendId = null;
    if (user && authHeader?.Authorization) {
      try {
        const res = await axios.post('/api/history', {
          questionId: question.id,
          answeredCorrectly: correct,
          selectedOption: idx + 1,
        }, { headers: authHeader });
        backendId = res.data.id;
      } catch (e) { /* 저장 실패 무시 */ }
    }

    const item = makeHistoryItem(backendId, question, correct, idx + 1, null, null);
    setHistory(p => [item, ...p.slice(0, 49)]);
  };

  const handleNext = () => {
    setAnimation('fade-out');
    setTimeout(() => loadQuestion(selectedCat, selectedSub, usedIds), 160);
  };

  const handleRetryDone = (localId, serverData) => {
    setHistory(prev => prev.map(item =>
      item.id !== localId ? item : {
        ...item,
        retriedCorrectly: serverData.retriedCorrectly,
        retriedOption: serverData.retriedOption,
      }
    ));
  };

  const accuracy = score.total > 0 ? Math.round((score.correct / score.total) * 100) : 0;
  const visibleCats = ALL_CATEGORIES.filter(c =>
    c.key === null || !subjectFilter || c.subject === subjectFilter
  );

  return (
    <div className="app">

      {/* ── 헤더 ── */}
      <header className="header">
        <div className="header-top">
          <div className="logo">
            <span className="logo-icon">🗄️</span>
            <span className="logo-text">SQLD<span className="logo-sub"> 문제은행</span></span>
          </div>
          <div className="header-right">
            <div className="score-badge">
              <span className="score-correct">{score.correct}</span>
              <span className="score-sep">/</span>
              <span className="score-total">{score.total}</span>
              {streak >= 3 && <span className="streak-badge">🔥{streak}</span>}
            </div>
            {user ? (
              <div className="user-info">
                <span className="user-name">👤 {user.username}</span>
                <button className="btn-logout" onClick={onLogout}>로그아웃</button>
              </div>
            ) : (
              <span className="guest-label">게스트</span>
            )}
          </div>
        </div>

        <div className="tabs">
          {[['quiz','📝 풀기'],['history','📋 기록'],['stats','📊 통계']].map(([k, l]) => (
            <button key={k} className={`tab ${mainTab === k ? 'active' : ''}`} onClick={() => setMainTab(k)}>{l}</button>
          ))}
        </div>
      </header>

      {/* ── 과목 필터 + 카테고리 ── */}
      {mainTab === 'quiz' && (
        <>
          <div className="subject-bar">
            {SUBJECT_TABS.map(s => (
              <button
                key={s.key ?? 'all'}
                className={`subject-btn ${subjectFilter === s.key ? 'active' : ''}`}
                onClick={() => handleSubjectFilter(s.key)}
              >
                {s.label}
              </button>
            ))}
            <span className="total-badge">{questions.length}문제</span>
          </div>
          <div className="category-scroll">
            {visibleCats.map(cat => (
              <button
                key={cat.key ?? 'all'}
                className={`cat-chip ${selectedCat === cat.key ? 'active' : ''}`}
                onClick={() => handleCatChange(cat.key)}
              >
                {cat.icon} {cat.label}
              </button>
            ))}
          </div>
        </>
      )}

      {/* ── 메인 콘텐츠 ── */}
      <main className="main">

        {/* 퀴즈 탭 */}
        {mainTab === 'quiz' && (
          <div className={`quiz-container ${animation}`}>
            {qLoading && (
              <div className="loading">
                <div className="spinner" />
                <p>문제 불러오는 중...</p>
              </div>
            )}
            {qError && (
              <div className="empty-state">
                <div className="empty-icon">⚠️</div>
                <p>문제를 불러올 수 없습니다.</p>
                <p style={{ fontSize: '0.85rem', color: 'var(--text-muted)' }}>백엔드 서버가 실행 중인지 확인하세요.</p>
              </div>
            )}
            {!qLoading && !qError && question && (
              <QuizCard
                question={question}
                selectedAns={selectedAns}
                showAnswer={showAnswer}
                onAnswer={handleAnswer}
                onNext={handleNext}
              />
            )}
          </div>
        )}

        {/* 기록 탭 */}
        {mainTab === 'history' && (
          <div className="history-container">
            <div className="history-summary">
              {[
                { num: score.total,               label: '총 문제', cls: '' },
                { num: score.correct,             label: '정답',   cls: 'correct' },
                { num: score.total-score.correct, label: '오답',   cls: 'wrong' },
                { num: accuracy + '%',            label: '정확도', cls: 'accent' },
              ].map((s, i) => (
                <div key={i} className={`stat-card ${s.cls}`}>
                  <div className="stat-num">{s.num}</div>
                  <div className="stat-label">{s.label}</div>
                </div>
              ))}
            </div>

            {historyLoading && (
              <div className="empty-state"><p>기록 불러오는 중...</p></div>
            )}

            {!historyLoading && history.length === 0 && (
              <div className="empty-state">
                <div className="empty-icon">📝</div>
                <p>아직 푼 문제가 없습니다.</p>
                <button className="btn-primary" onClick={() => setMainTab('quiz')}>문제 풀러 가기</button>
              </div>
            )}

            {!historyLoading && history.length > 0 && (() => {
              const filteredHistory = history.filter(item => {
                if (historyFilter === 'correct')   return item.correct && !item.retriedCorrectly;
                if (historyFilter === 'wrong')     return !item.correct && !item.retriedCorrectly;
                if (historyFilter === 'retry')     return item.retriedCorrectly;
                return true; // 'all'
              });
              return (
                <>
                  <div className="history-tip-row">
                    <p className="history-tip">💡 문제를 클릭하면 다시 풀 수 있습니다.</p>
                    <select
                      className="history-filter-select"
                      value={historyFilter}
                      onChange={e => setHistoryFilter(e.target.value)}
                    >
                      <option value="all">모두 보기</option>
                      <option value="correct">✅ 정답만</option>
                      <option value="wrong">❌ 오답만</option>
                      <option value="retry">🔄 재시도 정답만</option>
                    </select>
                  </div>
                  {filteredHistory.length === 0 ? (
                    <div className="empty-state">
                      <div className="empty-icon">🔍</div>
                      <p>해당 조건의 문제가 없습니다.</p>
                    </div>
                  ) : (
                    <div className="history-list">
                      {filteredHistory.map(item => (
                        <HistoryCard
                          key={item.id}
                          item={item}
                          onRetryDone={handleRetryDone}
                        />
                      ))}
                    </div>
                  )}
                </>
              );
            })()}
          </div>
        )}

        {/* 통계 탭 */}
        {mainTab === 'stats' && (
          <div className="stats-container">
            <div className="stats-header"><h2>문제 은행 현황</h2></div>
            <div className="stats-big-card">
              <div className="big-num">{stats?.total ?? 0}</div>
              <div className="big-label">총 문제 수</div>
            </div>
            <div className="stats-row-3">
              <div className="stats-small-card blue">
                <div className="small-num">{stats?.bySubject?.['1과목'] ?? 0}</div>
                <div className="small-label">1과목</div>
              </div>
              <div className="stats-small-card green">
                <div className="small-num">{stats?.bySubject?.['2과목'] ?? 0}</div>
                <div className="small-label">2과목</div>
              </div>
              <div className="stats-small-card purple">
                <div className="small-num">{stats?.byType?.['기출'] ?? 0}</div>
                <div className="small-label">기출</div>
              </div>
            </div>
            <div className="diff-cards">
              <h3>난이도별</h3>
              <div className="diff-row">
                {[['하', stats?.byDifficulty?.['하'] ?? 0, '#4caf82'], ['중', stats?.byDifficulty?.['중'] ?? 0, '#f7a84f'], ['상', stats?.byDifficulty?.['상'] ?? 0, '#e55a6f']].map(([d, n, c]) => (
                  <div key={d} className="diff-card" style={{ borderColor: c + '44' }}>
                    <div className="diff-num" style={{ color: c }}>{n}</div>
                    <div className="diff-label">난이도 {d}</div>
                  </div>
                ))}
              </div>
            </div>
            <div className="category-stats">
              <h3>카테고리별 문제 수</h3>
              {ALL_CATEGORIES.filter(c => c.key).map(cat => {
                const cnt = questions.filter(q => q.category === cat.key).length;
                if (cnt === 0) return null;
                const pct = Math.round((cnt / (stats?.total ?? 1)) * 100);
                return (
                  <div key={cat.key} className="cat-stat-row">
                    <span className="cat-stat-label">{cat.icon} {cat.label}</span>
                    <div className="cat-stat-bar-wrap">
                      <div className="cat-stat-bar" style={{ width: `${Math.max(pct * 4, 4)}%` }} />
                    </div>
                    <span className="cat-stat-count">{cnt}</span>
                  </div>
                );
              })}
            </div>
            {score.total > 0 && (
              <div className="my-stats">
                <h3>내 성적</h3>
                <div className="accuracy-circle">
                  <svg viewBox="0 0 100 100" className="circle-svg">
                    <circle cx="50" cy="50" r="40" fill="none" stroke="var(--bg-elevated)" strokeWidth="10"/>
                    <circle cx="50" cy="50" r="40" fill="none" stroke="var(--accent-blue)" strokeWidth="10"
                      strokeDasharray={`${2.51 * accuracy} ${251 - 2.51 * accuracy}`}
                      strokeLinecap="round" transform="rotate(-90 50 50)"/>
                    <text x="50" y="50" textAnchor="middle" dy="0.3em" className="circle-text">{accuracy}%</text>
                  </svg>
                  <p>정확도</p>
                </div>
                {streak >= 3 && <div className="streak-display">🔥 연속 정답 {streak}개!</div>}
              </div>
            )}
          </div>
        )}
      </main>
    </div>
  );
}

// ─── 퀴즈 카드 ─────────────────────────────────
function QuizCard({ question, selectedAns, showAnswer, onAnswer, onNext }) {
  const diff = DIFFICULTY_MAP[question.difficulty] || DIFFICULTY_MAP['중'];
  const type = TYPE_MAP[question.type] || TYPE_MAP['기출'];

  const lines    = question.question.split('\n');
  const qText    = lines[0];
  const sqlBlock = lines.slice(1).join('\n').trim();

  const optCls = (idx) => {
    if (!showAnswer) return selectedAns === idx ? 'option selected' : 'option';
    if (idx + 1 === question.answer) return 'option correct';
    if (idx === selectedAns)         return 'option wrong';
    return 'option dim';
  };

  return (
    <div className="quiz-card">
      <div className="card-header">
        <div className="badges">
          <span className="badge subj-badge">{question.subject || '2과목'}</span>
          <span className="badge" style={{ background: type.color+'22', color: type.color, borderColor: type.color+'44' }}>
            {question.type}
          </span>
          <span className="badge" style={{ background: diff.color+'22', color: diff.color, borderColor: diff.color+'44' }}>
            {question.difficulty}
          </span>
          <span className="badge category-badge">{question.category}</span>
        </div>
        <span className="q-id">#{question.id}</span>
      </div>

      <div className="question-text">{qText}</div>

      {sqlBlock && (
        <div className="sql-block">
          <div className="sql-header">
            <span className="sql-dot r"/><span className="sql-dot y"/><span className="sql-dot g"/>
            <span className="sql-lang">SQL</span>
          </div>
          <pre className="sql-code">{sqlBlock}</pre>
        </div>
      )}

      <div className="options-list">
        {question.options.map((opt, idx) => (
          <button key={idx} className={optCls(idx)} onClick={() => onAnswer(idx)}>
            <span className="opt-num">{idx + 1}</span>
            <span className="opt-text">{opt}</span>
            {showAnswer && idx + 1 === question.answer && <span className="opt-mark correct-mark">✓</span>}
            {showAnswer && idx === selectedAns && idx + 1 !== question.answer && <span className="opt-mark wrong-mark">✗</span>}
          </button>
        ))}
      </div>

      {showAnswer && (
        <div className="explanation-box">
          <div className="result-row">
            <span className={selectedAns + 1 === question.answer ? 'result-correct' : 'result-wrong'}>
              {selectedAns + 1 === question.answer ? '🎉 정답입니다!' : `❌ 오답 (정답: ${question.answer}번)`}
            </span>
          </div>
          <div className="explanation-label">💡 해설</div>
          <div className="explanation-text">{question.explanation}</div>
        </div>
      )}

      {showAnswer && (
        <button className="btn-next" onClick={onNext}>다음 문제 →</button>
      )}
      {!showAnswer && (
        <button className="btn-show-answer" onClick={() => onAnswer(-1)}>답 바로 보기</button>
      )}
    </div>
  );
}
