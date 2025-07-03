import React from 'react';

const BrandCard = ({ name, image }) => {
  return (
    <div className="brand-card text-center p-2">
      <img
        src={`http://localhost:8080/images/${image}`}
        alt={name}
        className="img-fluid mx-auto d-block"
        style={{ maxHeight: '150px', objectFit: 'contain' }}
      />
      <h5 className="mt-2 text-wrap" style={{ whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>
        {name}
      </h5>
    </div>
  );
};

export default BrandCard;