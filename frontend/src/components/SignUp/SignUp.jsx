import React, { useState } from 'react';
import axios from 'axios';
import './SignUp.css';
import AuthNavbar from '../Navbar/AuthNavbar';
import { useNavigate } from 'react-router-dom';

const SignUp = () => {

  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [address, setAddress] = useState('');
  const [phone, setPhone] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const registerUser = (event) => {
    event.preventDefault();

    if (password !== confirmPassword) {
      setErrorMessage("Passwords do not match.");
      return;
    }

    const user = { name, email, password, address, phone };

    axios.post('http://localhost:8080/api/auth/signup', user)
      .then(response => {
        setErrorMessage('');
        navigate('/login');
      })
      .catch(error => {
        setErrorMessage(error.response?.data || "Registration failed.");
      });
  };

  return (
    <>
      <AuthNavbar />
      <div className="signup-wrapper">
        <div className="signup-card">
          <h2 className="signup-title">Create Your Account</h2>
          {errorMessage && <div className="alert alert-danger">{errorMessage}</div>}
          
          <form className="signup-form" onSubmit={registerUser}>
            <div className="form-group">
              <input type="text" placeholder="Full Name" value={name} onChange={(e) => setName(e.target.value)} required />
              <input type="email" placeholder="Email Address" value={email} onChange={(e) => setEmail(e.target.value)} required />
            </div>
            <div className="form-group">
              <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required />
              <input type="password" placeholder="Confirm Password" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} required />
            </div>
            <textarea placeholder="Address" rows="4" value={address} onChange={(e) => setAddress(e.target.value)} required />
            <input type="tel" placeholder="Phone Number" value={phone} onChange={(e) => setPhone(e.target.value)} required />
            
            <button type="submit">Sign Up</button>
            
            <div className="login-link">
              Already registered? <a href="/login">Login</a>
            </div>
          </form>
        </div>
      </div>
    </>
  );
};

export default SignUp;
