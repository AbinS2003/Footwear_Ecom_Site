import React, { useState } from 'react';
import {
  Navbar,
  Nav,
  Form,
  FormControl,
  Button,
  Dropdown,
  Badge,
  Container
} from 'react-bootstrap';
import { FaUserCircle } from 'react-icons/fa';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faShoppingCart,
  faUser,
  faCube,
  faLock,
  faRightFromBracket,
  faUserEdit
} from '@fortawesome/free-solid-svg-icons';
import { Link, useNavigate } from 'react-router-dom';

import './CustomNavbar.css';
import { jwtDecode } from 'jwt-decode';
import { useEffect } from 'react';
import axios from 'axios';

const CustomNavbar = () => {

  const navigate = useNavigate();

  const[searchKeyword, setSearchKeyword] = useState('');
  const[cartCount, setCartCount] = useState(0)
  const token = localStorage.getItem("token");
  const decoded = jwtDecode(token);
  const userId = decoded.userId || decoded.id;

  const handleSearch = () => {
    if (searchKeyword.trim() !== "") {
      navigate(`/product-list?query=${encodeURIComponent(searchKeyword.trim())}`);
    }
  };

  const handleCategoryClick = (categoryName) => {
    navigate(`/product-list?category=${encodeURIComponent(categoryName)}`);
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  useEffect(() => {

    axios.get(`http://localhost:8080/api/cart/count/${userId}`, {
      headers: { Authorization: "Bearer " + token }
    })
    .then(res => {
      setCartCount(res.data);
    })
    .catch(err => {
      console.error("Failed to fetch cart count", err);
    });
  }
, [cartCount]);

    
  return (
    <Navbar expand="lg" className="custom-navbar shadow-sm py-3 sticky-top">
      <Container fluid>
        {/* Brand */}
        <Navbar.Brand as={Link} to="/home" className="navbar-brand-text text-light fs-2 fw-bold">
          Sneakz
        </Navbar.Brand>

        <Navbar.Toggle aria-controls="navbarScroll" />
        <Navbar.Collapse id="navbarScroll" className="align-items-center">

          {/* Left Links */}
         <Nav className="me-4 d-flex align-items-center gap-3">
              {["Men", "Women", "Kids", "Unisex"].map((label, i) => (
                <Nav.Link
                  key={i}
                  className="nav-category-link text-light"
                  onClick={() => handleCategoryClick(label.toLowerCase())}
                  style={{ cursor: 'pointer' }}
                >
                  {label}
                </Nav.Link>
              ))}
          </Nav>

        

          {/* Search */}
          <Form className="d-flex mx-auto w-50 search-form">
            <FormControl
              type="search"
              placeholder="Search for shoes..."
              className="form-control"
              value={searchKeyword}
                  onChange={(e) => setSearchKeyword(e.target.value)}
            />
            <Button variant="dark" className="ms-2 px-4" onClick={handleSearch}>
              Search
            </Button>
          </Form>

          {/* Right Icons */}
          <Nav className="ms-auto d-flex align-items-center gap-3">
            <Nav.Link as={Link} to="/cart" className="position-relative cart-icon text-light">
              <FontAwesomeIcon icon={faShoppingCart} className="fs-4" />
              {cartCount > 0 && (
                <Badge
                  bg="danger"
                  pill
                  className="position-absolute top-0 start-100 translate-middle"
                >
                  {cartCount}
                </Badge>
              )}
            </Nav.Link>

            {/* Dropdown */}
            <Dropdown align="end">
  <Dropdown.Toggle variant="link" className="user-icon-btn text-light border-0">
    <FaUserCircle size={26} />
  </Dropdown.Toggle>
  <Dropdown.Menu className="dropdown-dark">
    <Dropdown.Item as={Link} to="/profile">
      <FontAwesomeIcon icon={faUser} className="me-2" />
      Profile
    </Dropdown.Item>
    <Dropdown.Item as={Link} to="/password">
      <FontAwesomeIcon icon={faLock} className="me-2" />
      Change Password
    </Dropdown.Item>
    <Dropdown.Item as={Link} to="/orders">
      <FontAwesomeIcon icon={faCube} className="me-2" />
      My Orders
    </Dropdown.Item>
    <Dropdown.Item as={Link} to="/update-profile">
      <FontAwesomeIcon icon={faUserEdit} className="me-2" />
      Edit Profile
    </Dropdown.Item>
    <Dropdown.Divider />
    <Dropdown.Item onClick={handleLogout}>
      <FontAwesomeIcon icon={faRightFromBracket} className="me-2" />
      Logout
    </Dropdown.Item>
  </Dropdown.Menu>
</Dropdown>

          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default CustomNavbar;
