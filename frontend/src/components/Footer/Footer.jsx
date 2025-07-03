import React from "react";
import { Link } from "react-router-dom";
import {
  FaFacebookF,
  FaTwitter,
  FaGoogle,
  FaInstagram,
  FaHome,
  FaEnvelope,
  FaPhone,
  FaPrint,
} from "react-icons/fa";

const Footer = () => {
  return (
    <div className="mt-5">
      <footer
        className="text-center text-lg-start text-white"
        style={{ backgroundColor: "#151515" }}
      >
        <div className="container p-4 pb-0">
          <section>
            <div className="row">
              {/* Company Info */}
              <div className="col-md-3 col-lg-3 col-xl-3 mx-auto mt-3">
                <h6 className="text-uppercase mb-4 font-weight-bold">
                  Sneakz
                </h6>
                <p>
                  Your ultimate destination for trend-setting,
                   comfortable, and affordable footwear. From streetwear
                    kicks to classic formals, Sneakz delivers style at your doorstep.
                </p>
              </div>

              <hr className="w-100 clearfix d-md-none" />

              {/* Products */}
              <div className="col-md-2 col-lg-2 col-xl-2 mx-auto mt-3">
                <h6 className="text-uppercase mb-4 font-weight-bold">Products</h6>
                <p><a href="#!" className="text-white">Mens' Footwear</a></p>
                <p><a href="#!" className="text-white">Womens' Footwear</a></p>
                <p><a href="#!" className="text-white">Kids' Footwear</a></p>
                <p><a href="#!" className="text-white">Others</a></p>
              </div>

              <hr className="w-100 clearfix d-md-none" />

              {/* Useful Links */}
              <div className="col-md-3 col-lg-2 col-xl-2 mx-auto mt-3">
                <h6 className="text-uppercase mb-4 font-weight-bold">
                  Useful links
                </h6>
                <p><Link to="/profile" className="text-white">Your Account</Link></p>
                <p><Link to="/orders" className="text-white">Your Orders</Link></p>
                <p><a href="#!" className="text-white">Help</a></p>
              </div>

              <hr className="w-100 clearfix d-md-none" />

              {/* Contact */}
              <div className="col-md-4 col-lg-3 col-xl-3 mx-auto mt-3">
                <h6 className="text-uppercase mb-4 font-weight-bold">Contact</h6>
                <p><FaHome className="me-2" /> New York, NY 10012, US</p>
                <p><FaEnvelope className="me-2" /> info@gmail.com</p>
                <p><FaPhone className="me-2" /> + 01 234 567 88</p>
                <p><FaPrint className="me-2" /> + 01 234 567 89</p>
              </div>
            </div>
          </section>

          <hr className="my-3" />

          {/* Footer Bottom Section */}
          <section className="p-3 pt-0">
            <div className="row d-flex align-items-center">
              <div className="col-md-7 col-lg-8 text-center text-md-start">
                <div className="p-3">
                  © 2025 Copyright:
                  <a className="text-white" href="https://github.com/AbinS2003"> github.com/AbinS2003</a>
                </div>
              </div>

              <div className="col-md-5 col-lg-4 text-center text-md-end">
                <a className="btn btn-outline-light btn-floating m-1" role="button"><FaFacebookF /></a>
                <a className="btn btn-outline-light btn-floating m-1" role="button"><FaTwitter /></a>
                <a className="btn btn-outline-light btn-floating m-1" role="button"><FaGoogle /></a>
                <a className="btn btn-outline-light btn-floating m-1" role="button"><FaInstagram /></a>
              </div>
            </div>
          </section>
        </div>
      </footer>
      </div>
    
  );
};

export default Footer;
