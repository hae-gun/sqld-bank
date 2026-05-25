import React, { createContext, useContext, useState, useEffect } from 'react';
import axios from 'axios';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    try {
      const saved = localStorage.getItem('sqld_user');
      return saved ? JSON.parse(saved) : null;
    } catch { return null; }
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const authHeader = user ? { Authorization: `Bearer ${user.token}` } : {};

  const register = async (username, password) => {
    setLoading(true);
    setError(null);
    try {
      const res = await axios.post('/api/auth/register', { username, password });
      const u = res.data;
      setUser(u);
      localStorage.setItem('sqld_user', JSON.stringify(u));
      return u;
    } catch (e) {
      const msg = e.response?.data?.error || '회원가입 실패';
      setError(msg);
      throw new Error(msg);
    } finally {
      setLoading(false);
    }
  };

  const login = async (username, password) => {
    setLoading(true);
    setError(null);
    try {
      const res = await axios.post('/api/auth/login', { username, password });
      const u = res.data;
      setUser(u);
      localStorage.setItem('sqld_user', JSON.stringify(u));
      return u;
    } catch (e) {
      const msg = e.response?.data?.error || '로그인 실패';
      setError(msg);
      throw new Error(msg);
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem('sqld_user');
  };

  const clearError = () => setError(null);

  return (
    <AuthContext.Provider value={{ user, loading, error, authHeader, register, login, logout, clearError }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
