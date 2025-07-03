// src/components/CartItem.jsx
import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';

const CartItem = ({ item, onBuyNow, onRemove }) => {
  console.log(item);
  return (
    <div className="d-flex align-items-center border rounded p-3 mb-3 shadow-sm bg-white">
      {/* Product Image */}
      <img
        src={`http://localhost:8080/images/${item.image}`}
        alt={item.title}
        style={{ width: "80px", height: "80px", objectFit: "contain" }}
        className="me-3"
      />

      {/* Product Info (Left aligned) */}
      <div className="flex-grow-1 text-start d-flex flex-column justify-content-center">
        <h6 className="mb-1 text-dark">{item.title}</h6>
        {item.size && (
          <small className="text-muted">Size: {item.size}</small>
        )}
        <div className="d-flex flex-column">
          <span className="text-success fw-semibold">₹{item.price * item.quantity}</span>
          <span className="text-muted" style={{ fontSize: "0.85rem" }}>Qty: {item.quantity}</span>
        </div>
      </div>


      {/* Buy Now Button */}
      <button
        className="btn btn-sm btn-success px-3 me-2"
        onClick={() => onBuyNow(item)}
      >
        Buy Now
      </button>

      <button
        className="btn btn-sm btn-danger px-3"
        onClick={() => onRemove(item)}
      >
        Remove
      </button>
    </div>
  );
};

export default CartItem;
