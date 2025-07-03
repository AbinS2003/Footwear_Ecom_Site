// src/components/Cart.jsx
import React, { useEffect, useState } from "react";
import CartItem from "./CartItem";
import CustomNavbar from "../Navbar/CustomNavbar";
import Footer from "../Footer/Footer";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";


const Cart = () => {

    
    const token = localStorage.getItem('token');
    const decoded = jwtDecode(token);
    const userId = decoded.userId || decoded.id;
    const navigate = useNavigate();

    console.log(token);

    const [cartItems, setCartItems] = useState([]);
    const [message, setMessage] = useState('');
    const [showMessage, setShowMessage] = useState(false);
    const [selectedItem, setSelectedItem] = useState(null);
    const [showModal, setShowModal] = useState(false);


  useEffect(() => {

    axios.get(`http://localhost:8080/api/cart/user/${userId}`, {
      headers: { Authorization: "Bearer " + token },
    }).then(response => {
      setCartItems(response.data);
      console.log(cartItems);
    }).catch(error => {
      console.log("Failed to fetch cart items", error);
    })
  },[]);

  const handleBuyNow = (item) => {
    setSelectedItem(item);
    setShowModal(true);
  };


  const handleRemove = (item) => {

    console.log("Item to remove:", item);

    axios.delete(`http://localhost:8080/api/cart/remove/${userId}/${item.productId}`, {
      headers: { Authorization: "Bearer " + token }
    }).then(response => {
      setMessage(response.data);
      fetchCartItems();
    }).catch(error => {
      setMessage(error.response.data);
    })
  }

  const fetchCartItems = () => {
  axios.get(`http://localhost:8080/api/cart/user/${userId}`, {
    headers: { Authorization: "Bearer " + token }
  })
  .then(response => {
    setCartItems(response.data);
  })
  .catch(error => {
    console.error("Failed to fetch cart items:", error);
  });
};

const handleConfirmOrder = () => {
  axios.post(`http://localhost:8080/api/order/add`, {
    userId: userId,
    productId: selectedItem.productId || selectedItem.id,
    sizeId: selectedItem.sizeId || selectedItem.size,
    quantity: selectedItem.quantity || 1 
  }, {
    headers: { Authorization: "Bearer " + token }
  })
  .then(response => {
    alert("Order placed successfully!");
    setShowModal(false);
    fetchCartItems();
  })
  .catch(error => {
    alert("Failed to place order.");
    setShowModal(false);
  });
};



  return (
    <>
    <CustomNavbar/>
    
    <div className="container mt-5" style={{ maxWidth: "900px" }}>
      <h4 className="mb-4 fs-1 fw-bold text-center">My Cart</h4>
      {cartItems.length === 0 ? (
        <p className="text-center mt-5 mb-5 fs-2">Your cart is empty.</p>
      ) : (
        cartItems.map((item) => (
          <CartItem key={item.id} item={item} onBuyNow={handleBuyNow} onRemove={handleRemove}/>
        ))
      )}

      {showMessage && message && (
        <div
          className="mt-3 alert alert-info text-center px-4 py-2 fade show"
        >
          {message}
        </div>    
      )}
    </div>
    {selectedItem && (
  <div className={`modal fade ${showModal ? "show d-block" : ""}`} tabIndex="-1" role="dialog" style={{ backgroundColor: "rgba(0,0,0,0.5)" }}>
    <div className="modal-dialog modal-dialog-centered" role="document">
      <div className="modal-content">
        <div className="modal-header">
          <h5 className="modal-title">Confirm Order</h5>
          <button type="button" className="btn-close" onClick={() => setShowModal(false)}></button>
        </div>
        <div className="modal-body text-center">
          <img
            src={`http://localhost:8080/images/${selectedItem.image}`}
            alt={selectedItem.title}
            className="img-fluid mb-3"
            style={{ maxHeight: "150px" }}
          />
          <h5>{selectedItem.title}</h5>
          <p>Size: <strong>{selectedItem.size}</strong></p>
          <p>Price: <strong>₹{selectedItem.price * selectedItem.quantity}</strong></p>
            <p>Quantity: <strong>{selectedItem.quantity}</strong></p>  
        </div>
        <div className="modal-footer">
          <button className="btn btn-secondary" onClick={() => setShowModal(false)}>Cancel</button>
          <button className="btn btn-success" onClick={handleConfirmOrder}>Confirm Order</button>
        </div>
      </div>
    </div>
  </div>
)}

    <Footer />
    </>
  );
};

export default Cart;
