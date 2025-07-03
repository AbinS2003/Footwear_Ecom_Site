import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import CustomNavbar from "../Navbar/CustomNavbar";
import Footer from "../Footer/Footer";
import { jwtDecode } from "jwt-decode";
import axios from "axios";

const UserProfile = () => {

    const token = localStorage.getItem("token");
    const decoded = jwtDecode(token);
    const userId = decoded.userId || decoded.id;

  const[user, setUser] = useState('');

  useEffect(() => {

    axios.get(`http://localhost:8080/api/user/${userId}`, {
      headers: { Authorization: "Bearer " + token },
    }).then(response =>{
      setUser(response.data);
      
    }).catch(error =>{
      console.log("Failed to fetch user details", error);
    });
  })



  return (
    <>
      <div className="user-profile-page min-vh-100 d-flex flex-column">
        <CustomNavbar />

        <div className="container my-5 flex-grow-1 d-flex flex-column align-items-center justify-content-start">
          <div className="card shadow-sm p-5 w-100" style={{ maxWidth: "700px" }}>
            <h2 className="fw-bold mb-4 text-center">Personal Information</h2>

            <div className="mb-3">
              <label className="form-label fw-semibold">Full Name</label>
              <div className="border-bottom py-2 ps-1 text-secondary">{user.name}</div>
            </div>

            <div className="mb-3">
              <label className="form-label fw-semibold">Email Address</label>
              <div className="border-bottom py-2 ps-1 text-secondary">{user.email}</div>
            </div>

            <div className="mb-3">
              <label className="form-label fw-semibold">Address</label>
              <div className="border-bottom py-2 ps-1 text-secondary">{user.address}</div>
            </div>

            <div className="mb-3">
              <label className="form-label fw-semibold">Telephone (+91)</label>
              <div className="border-bottom py-2 ps-1 text-secondary">{user.phone}</div>
            </div>
          </div>
        </div>

        <Footer />
      </div>
    </>
  );
};

export default UserProfile;
