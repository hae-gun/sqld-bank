import React, { useState } from 'react';
import axios from 'axios';
import { useAuth } from './AuthContext';

const DIFFICULTY_MAP = {
  '하': { color: '#4caf82' },
  '중': { color: '#f7a84f' },
  '상': { color: '#e55a6f' },
};

export default function HistoryCard({ item, onRetryDone }) {
  const { authHeader } = useAuth();
  const [expanded, setExpanded]     = useState(false);
  const [retryAns, setRetryAns]     = useState(null);
  const [retryDone, setRetryDone]   = useState(false);
  const [retryResult, setRetryResult] = useState(
    // 이미 재시도 결과가 있으면 초기값으로 세팅
    item.retriedCorrectly !== null && item.retriedCorrectly !== undefined
      ? item.retriedCorrectly
      : null
  );

  const q = item.question; // full question object

  const handleExpand = () => {
    setExpanded(e => !e);
    // 다시 열면 재시도 초기화 (단, 이미 서버에 저장된 결과는 유지)
    if (!expanded) {
      setRetryAns(null);
      setRetryDone(item.retriedCorrectly !== null && item.retriedCorrectly !== undefined);
    }
  };

  const handleRetry = async (idx) => {
    if (retryDone || retryAns !== null) return;
    setRetryAns(idx);
    const correct = idx + 1 === q.answer;
    setRetryDone(true);
    setRetryResult(correct);

    // 서버에 저장 (로그인 상태이고 백엔드 ID가 있을 때)
    if (item.backendId && authHeader?.Authorization) {
      try {
        const res = await axios.put(`/api/history/${item.backendId}/retry`, {
          retriedCorrectly: correct,
          retriedOption: idx + 1,
        }, { headers: authHeader });
        if (onRetryDone) onRetryDone(item.id, res.data);
      } catch (e) {
        // 서버 저장 실패해도 UI는 유지
      }
    }
  };

  const optCls = (idx) => {
    if (!retryDone && retryAns === null) return 'option';
    if (idx + 1 === q.answer)              return 'option correct';
    if (retryAns !== null && idx === retryAns && idx + 1 !== q.answer) return 'option wrong';
    return 'option dim';
  };

  const lines    = q.question.split('\n');
  const qText    = lines[0];
  const sqlBlock = lines.slice(1).join('\n').trim();
  const diff     = DIFFICULTY_MAP[q.difficulty] || DIFFICULTY_MAP['중'];

  // 배지 결정: 원래 정답 / 오답, 재시도 여부
  const statusBadge = () => {
    if (item.correct) {
      return <span className="h-badge h-badge-correct">✅ 정답</span>;
    }
    if (retryResult === true || item.retriedCorrectly === true) {
      return <span className="h-badge h-badge-retry-correct">🔄 재시도 정답</span>;
    }
    if (retryResult === false || item.retriedCorrectly === false) {
      return <span className="h-badge h-badge-wrong">❌ 재시도 오답</span>;
    }
    return <span className="h-badge h-badge-wrong">❌ 오답</span>;
  };

  return (
    <div className={`history-card ${item.correct ? 'correct' : 'wrong'} ${expanded ? 'expanded' : ''}`}>
      {/* 요약 행 (항상 표시) */}
      <div className="history-card-header" onClick={handleExpand}>
        <div className="history-card-left">
          {statusBadge()}
          <div className="history-card-info">
            <span className="history-q">{item.text}</span>
            <span className="history-cat">{item.subject} · {item.category}</span>
          </div>
        </div>
        <span className="history-expand-icon">{expanded ? '▲' : '▼'}</span>
      </div>

      {/* 펼쳐진 재시도 영역 */}
      {expanded && q && (
        <div className="history-retry-area">
          <div className="retry-label">
            {retryDone
              ? (retryResult ? '🎉 재시도 정답!' : '❌ 재시도 오답')
              : '다시 풀어보기'}
          </div>

          {/* 난이도/카테고리 배지 */}
          <div className="retry-badges">
            <span className="badge" style={{ background: diff.color+'22', color: diff.color, borderColor: diff.color+'44' }}>
              {q.difficulty}
            </span>
            <span className="badge category-badge">{q.category}</span>
          </div>

          {/* 문제 텍스트 */}
          <div className="retry-question">{qText}</div>

          {/* SQL 블록 */}
          {sqlBlock && (
            <div className="sql-block" style={{ marginBottom: '12px' }}>
              <div className="sql-header">
                <span className="sql-dot r"/><span className="sql-dot y"/><span className="sql-dot g"/>
                <span className="sql-lang">SQL</span>
              </div>
              <pre className="sql-code">{sqlBlock}</pre>
            </div>
          )}

          {/* 보기 */}
          <div className="options-list retry-options">
            {q.options.map((opt, idx) => (
              <button
                key={idx}
                className={optCls(idx)}
                onClick={() => handleRetry(idx)}
                disabled={retryDone}
              >
                <span className="opt-num">{idx + 1}</span>
                <span className="opt-text">{opt}</span>
                {retryDone && idx + 1 === q.answer && <span className="opt-mark correct-mark">✓</span>}
                {retryDone && retryAns !== null && idx === retryAns && idx + 1 !== q.answer && (
                  <span className="opt-mark wrong-mark">✗</span>
                )}
              </button>
            ))}
          </div>

          {/* 해설 (재시도 후 표시) */}
          {retryDone && (
            <div className="explanation-box" style={{ marginTop: '12px' }}>
              <div className="explanation-label">💡 해설</div>
              <div className="explanation-text">{q.explanation}</div>
            </div>
          )}

          {/* 원래 정답도 표시 */}
          {!retryDone && item.answeredAt && (
            <p className="original-answer-note">
              최초 답: {item.selectedOption}번 ({item.correct ? '정답' : '오답'})
            </p>
          )}
        </div>
      )}
    </div>
  );
}
