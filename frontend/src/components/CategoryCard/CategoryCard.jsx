import React from 'react';
import './CategoryCard.css'; 



const CategoryCard = ({ title, image }) => {
  return (
    <div className="category-card card shadow-lg border-0 h-100 text-center">
      <img
        src={image}
        className="card-img-top category-img"
        alt={title}
      />
      <div className="card-body d-flex flex-column justify-content-center">
        <h5 className="card-title text-uppercase font-weight-bold text-dark">
          {title}
        </h5>
      </div>
    </div>
  );
};


export default CategoryCard;
