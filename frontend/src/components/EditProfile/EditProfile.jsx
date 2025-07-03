import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import CustomNavbar from '../Navbar/CustomNavbar';
import Footer from '../Footer/Footer';

  const EditProfile = () => {

    const token = localStorage.getItem("token");
    console.log(token);
    const decoded = jwtDecode(token);
    const userId = decoded.userId || decoded.id;
    console.log("User id is: ", userId);

    const [profile, setProfile] = useState({
        name: '',
        address: '',
        phone: '',
    });

  const [message, setMessage] = useState('');


useEffect(() => {
  axios.get(`http://localhost:8080/api/user/${userId}`, {
    headers: { Authorization: "Bearer " + token },
  })
  .then((res) => {
    setProfile({ ...res.data, userId });
  })
  .catch((err) => {
    console.error('Failed to load profile', err);
  });
}, []);



  const handleChange = (e) => {
    setProfile({ ...profile, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    axios.put(`http://localhost:8080/api/user/update-profile/${userId}`, profile, {
      headers: { Authorization: "Bearer " + token },
    }) 
      .then((res) => {
        setMessage(res.data);
      })
      .catch((err) => {
        console.error(err);
        setMessage(err.response?.data);
      });
  };

  return (
    <>

    <CustomNavbar />
    <div className="container mt-5 mb-5" style={{ maxWidth: '500px' }}>
      <h3 className="mb-4 text-center mt-4">Edit Profile</h3>
      {message && <div className="alert alert-info">{message}</div>}
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label>Name</label>
          <input
            type="text"
            name="name"
            value={profile.name}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>
        <div className="mb-3">
          <label>Address</label>
          <input
            type="text"
            name="address"
            value={profile.address}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>
        <div className="mb-3">
          <label>Phone</label>
          <input
            maxLength={10}
            type="text"
            name="phone"
            value={profile.phone}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>
        <button type="submit" className="btn btn-primary mb-5">Update Profile</button>
      </form>
    </div>
    <Footer />
    </>
  );
};

export default EditProfile;
