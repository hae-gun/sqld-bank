import React, { useState } from 'react';
import { useAuth } from './AuthContext';

export default function AuthScreen({ onGuest }) {
  const { login, register, loading, error, clearError } = useAuth();
  const [mode, setMode]         = useState('login'); // 'login' | 'register'
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [localErr, setLocalErr] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLocalErr('');
    clearError();
    if (!username.trim() || !password.trim()) {
      setLocalErr('아이디와 비밀번호를 입력하세요.');
      return;
    }
    try {
      if (mode === 'login') {
        await login(username.trim(), password);
      } else {
        if (username.trim().length < 3) {
          setLocalErr('아이디는 3자 이상이어야 합니다.');
          return;
        }
        if (password.length < 4) {
          setLocalErr('비밀번호는 4자 이상이어야 합니다.');
          return;
        }
        await register(username.trim(), password);
      }
    } catch (err) {
      // error is set via AuthContext
    }
  };

  const switchMode = () => {
    setMode(m => m === 'login' ? 'register' : 'login');
    setLocalErr('');
    clearError();
  };

  const displayErr = localErr || error;

  return (
    <div className="auth-screen">
      <div className="auth-card">
        {/* 로고 */}
        <div className="auth-logo">
          <span className="logo-icon">🗄️</span>
          <span className="logo-text">SQLD<span className="logo-sub"> 문제은행</span></span>
        </div>
        <p className="auth-subtitle">SQL 개발자 자격증 대비 문제 풀이</p>

        {/* 탭 */}
        <div className="auth-tabs">
          <button
            className={`auth-tab ${mode === 'login' ? 'active' : ''}`}
            onClick={() => { setMode('login'); setLocalErr(''); clearError(); }}
          >로그인</button>
          <button
            className={`auth-tab ${mode === 'register' ? 'active' : ''}`}
            onClick={() => { setMode('register'); setLocalErr(''); clearError(); }}
          >회원가입</button>
        </div>

        {/* 폼 */}
        <form onSubmit={handleSubmit} className="auth-form">
          <div className="form-group">
            <label className="form-label">아이디</label>
            <input
              className="form-input"
              type="text"
              placeholder="아이디 입력"
              value={username}
              onChange={e => setUsername(e.target.value)}
              autoComplete="username"
            />
          </div>
          <div className="form-group">
            <label className="form-label">비밀번호</label>
            <input
              className="form-input"
              type="password"
              placeholder="비밀번호 입력"
              value={password}
              onChange={e => setPassword(e.target.value)}
              autoComplete={mode === 'login' ? 'current-password' : 'new-password'}
            />
          </div>

          {displayErr && (
            <div className="auth-error">{displayErr}</div>
          )}

          <button className="btn-auth" type="submit" disabled={loading}>
            {loading ? '처리 중...' : mode === 'login' ? '로그인' : '회원가입'}
          </button>
        </form>

        {/* 구분선 */}
        <div className="auth-divider"><span>또는</span></div>

        {/* 게스트 */}
        <button className="btn-guest" onClick={onGuest}>
          로그인 없이 시작하기
        </button>
        <p className="guest-notice">게스트 모드에서는 새로고침 시 기록이 초기화됩니다.</p>
      </div>
    </div>
  );
}
