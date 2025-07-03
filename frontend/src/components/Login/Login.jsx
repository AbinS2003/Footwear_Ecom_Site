import React, { useState } from 'react';
import './Login.css';
import axios from 'axios';
import AuthNavbar from '../Navbar/AuthNavbar';
import { useNavigate } from 'react-router-dom';

const Login = () => {

  var[email, setEmail] = useState('');
  var[password, setPassword] = useState('');
  var[errorMessage, setErrorMessage] = useState('');
  var navigate = useNavigate();


  function attemptLogin(event) {

    event.preventDefault();

    axios.post('http://localhost:8080/api/auth/login',{
      email: email,
      password: password
      }).then(response => {

        setErrorMessage('')
        const token = response.data;
        console.log( response.data)
        localStorage.setItem('token', token);
  
        navigate('/home');
      }).catch(error => {
        if(error.response.data){
                setErrorMessage(error.response.data)
            }else if(error.response.data){
                setErrorMessage(error.response.data.message)
            }else{
                setErrorMessage('Failed to login user. Please contact admin')
            }
      })
  }



  return (
    <>
    <AuthNavbar />
    <div className="login-wrapper">
      <div className="login-card">
        <h2 className="login-title">Login</h2>
        {errorMessage?<div className="alert alert-danger">{errorMessage}</div>:''}
        <form className="login-form">
          <input type="email" placeholder="Email Address" value={email} onInput={(event) =>setEmail(event.target.value)} required />
          <input type="password" placeholder="Password" value={password} onInput={(event) =>setPassword(event.target.value)} required />
          <button type="submit" onClick={attemptLogin}>Login</button>
          <div className="signup-link">
            Don't have an account? <a href="/signup">Sign up</a>
          </div>
        </form>
      </div>
    </div>
    </>
  );
};

export default Login;
