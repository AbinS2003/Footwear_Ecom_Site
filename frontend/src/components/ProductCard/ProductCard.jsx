// src/components/ProductCard.jsx
import React from 'react';
import './ProductCard.css';



const ProductCard = ({ title, image, price, onClick }) => {
  return (
    <div className="col-md-3 mb-4 d-flex">
      <div className="product-card w-100 mb-2"  onClick={onClick}
      style={{ cursor: "pointer" }}>
        <img src={`http://localhost:8080/images/${image}`} className="card-img-top product-img" alt={title} />
        <div className="card-body text-center">
          <h6 className="card-title mb-1 mt-1">{title}</h6>
          <p className="card-price mb-0 mt-1">₹{price}</p>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
