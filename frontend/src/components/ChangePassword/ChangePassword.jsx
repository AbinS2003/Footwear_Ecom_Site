import React, { useState } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import CustomNavbar from "../Navbar/CustomNavbar";
import Footer from "../Footer/Footer";
import "./ChangePassword.css";
import axios from "axios";
import { jwtDecode } from "jwt-decode";



const ChangePassword = () => {

  const token = localStorage.getItem('token');
  const decoded = jwtDecode(token);
  const userId = decoded.userId || decoded.id;

  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [message, setMessage] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    if (newPassword.length < 6) {
      setMessage("New password must be at least 6 characters.");
      return;
    }

    if (newPassword !== confirmPassword) {
      setMessage("New passwords do not match.");
      return;
    }

    axios.post(`http://localhost:8080/api/user/change-password`,{
      userId: userId,
      currentPassword: currentPassword,
      newPassword: newPassword
    },{
      headers: { Authorization: "Bearer " + token }
    }).then(response => {
      setMessage(response.data);
    }).catch(error => {
      setMessage(error.response.data);
    });

    setCurrentPassword("");
    setNewPassword("");
    setConfirmPassword("");
  };

  return (
    <>
      <div className="change-password-page min-vh-100 d-flex flex-column">
        <CustomNavbar />

        <div className="container flex-grow-1 d-flex flex-column align-items-center justify-content-center py-5">
          <h3 className="fw-bold mb-4">Change Password</h3>

          <div className="card shadow-lg p-4" style={{ maxWidth: "500px", width: "100%" }}>
            {message && (
              <div className="alert alert-info text-center">{message}</div>
            )}

            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="currentPassword" className="form-label fw-semibold">Current Password</label>
                <input
                  type="password"
                  className="form-control"
                  id="currentPassword"
                  value={currentPassword}
                  onChange={(e) => setCurrentPassword(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="newPassword" className="form-label fw-semibold">New Password</label>
                <input
                  type="password"
                  className="form-control"
                  id="newPassword"
                  value={newPassword}
                  onChange={(e) => setNewPassword(e.target.value)}
                  required
                />
              </div>

              <div className="mb-4">
                <label htmlFor="confirmPassword" className="form-label fw-semibold">Confirm New Password</label>
                <input
                  type="password"
                  className="form-control"
                  id="confirmPassword"
                  value={confirmPassword}
                  onChange={(e) => setConfirmPassword(e.target.value)}
                  required
                />
              </div>

              <div className="text-center">
                <button type="submit" className="btn btn-primary px-4">
                  Update Password
                </button>
              </div>
            </form>
          </div>
        </div>

        <Footer />
      </div>
    </>
  );
};

export default ChangePassword;
