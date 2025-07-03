import React from 'react';
import { Navbar, Nav, Container } from 'react-bootstrap';
import './AuthNavbar.css';
const AuthNavbar = () => {
  return (
    <Navbar expand="lg" className=" shadow-sm py-2 sticky-top auth-nav">
      <Container fluid>
        <Navbar.Brand href="/home" className="navbar-brand-text fs-2 ml-3 text-white">
          Sneakz
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="authNavbar" />
        <Navbar.Collapse id="authNavbar" className="justify-content-end">
          <Nav className="d-flex align-items-center gap-3">
            <Nav.Link href="/login"><span  className="nav-text">Login</span></Nav.Link>
            <Nav.Link href="/signup"><span  className="nav-text">SignUp</span></Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default AuthNavbar;
