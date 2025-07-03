import React, { useEffect, useState } from "react";
import CustomNavbar from "../Navbar/CustomNavbar";
import 'bootstrap/dist/css/bootstrap.min.css';
import Footer from "../Footer/Footer";
import { useParams } from "react-router-dom";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


const ProductDetails = () => {

  const {productId} = useParams();
  const [product, setProduct] = useState(null);
  const [selectedSize, setSelectedSize] = useState("");
  const token = localStorage.getItem("token");
  const decoded = jwtDecode(token);
  const userId = decoded.userId || decoded.id;
  const[message, setMessage] = useState('');
  const [quantity, setQuantity] = useState(1);

  

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/products/${productId}`, {
        headers: { Authorization: "Bearer " + token }, 
      })
      .then((response) => setProduct(response.data)) 
      .catch((error) => {
        console.error("Failed to fetch product", error);
      });
  }, [productId]);

const handleAddToCart = () => {
  if (!product) return;

  if (!selectedSize) {
    toast.info("Please select a size", { autoClose: 2000 });
    return;
  }

  const sizeId = selectedSize;

  axios.post(`http://localhost:8080/api/cart/add`, {
    userId: userId,
    productId: productId,
    sizeId: sizeId,
    quantity: quantity  
  }, {
    headers: { Authorization: "Bearer " + token }
  }).then(response => {
    setMessage(response.data);
    toast.info(response.data, { autoClose: 2000 });
  }).catch(error => {
    const errorMsg = error?.response?.data || "Something went wrong.";
    setMessage(errorMsg);
     toast.info(error.response.data, { autoClose: 2000 });


  
  });
};

const showConfirmationToast = () => {
  if (!selectedSize) {
    toast.info("Please select a size", { autoClose: 2000 });
    return;
  }



  toast.info(
    <div>
      <p className="mb-2">Confirm your order?</p>
      <div className="d-flex justify-content-end gap-2">
        <button className="btn btn-sm btn-outline-secondary" onClick={() => toast.dismiss()}>
          Cancel
        </button>
        <button className="btn btn-sm btn-success" onClick={() => {
          toast.dismiss();
          handleBuyNow();
        }}>
          Confirm
        </button>
      </div>
    </div>,
    { autoClose: false }
  );
};




  const handleBuyNow = () => {
  
    const sizeId = selectedSize;
    axios.post(`http://localhost:8080/api/order/add`, {
      userId: userId,
      productId: productId,
      sizeId: sizeId,
      quantity: quantity
    },{
    headers: { Authorization: "Bearer " + token }
      }).then(response => {
        setMessage(response.data);
      }).catch(error => {
        const errorMsg = error?.response?.data || "Failed to place order.";
        setMessage(errorMsg);
      });

       toast.info("Waiting for confirmation", { autoClose: 2000 });
      };

    if (!product) {
    return (
      <>
        <CustomNavbar />
        <div className="container mt-5 text-center">
          <p>Loading product details...</p>
        </div>
        
        <Footer />
      </>
    );
  }



  return (
    <>
      <CustomNavbar />
      <div className="container mt-5 mb-5 pt-5 pb-5" style={{ maxWidth: "900px" }}>
        <div className="row g-4 ">
          {/* Product Image */}
          <div className="col-md-6 text-center">
            <img
              src={`http://localhost:8080/images/${product.image}`}
              alt={product.title}
              className="img-fluid rounded shadow"
              style={{ maxHeight: "450px", objectFit: "cover" }}
            />
          </div>

          {/* Product Details */}
          <div className="col-md-6 text-center d-flex flex-column align-items-center justify-content-center">
            <h3 className="mb-2">{product.title}</h3>
            <h6 className="text-muted mb-3">{product.brand?.name}</h6>
            <p className="small px-3">{product.description}</p>

            {/* Size Selection */}
           <div className="d-flex gap-3 mb-3">
  {/* Size Dropdown */}
  <div>
    <label htmlFor="sizeSelect" className="form-label fw-semibold">
      Size
    </label>
    <select
      className="form-select form-select-sm text-center"
      id="sizeSelect"
      value={selectedSize}
      onChange={(e) => setSelectedSize(e.target.value)}
    >
      <option value="">--</option>
      {product.sizes?.map((size) => (
        <option key={size.id} value={size.id}>
          {size.value}
        </option>
      ))}
    </select>
  </div>

  {/* Quantity Dropdown */}
  <div>
    <label htmlFor="quantitySelect" className="form-label fw-semibold">
      Qty
    </label>
    <select
      className="form-select form-select-sm text-center"
      id="quantitySelect"
      value={quantity}
      onChange={(e) => setQuantity(parseInt(e.target.value))}
    >
      {[...Array(5).keys()].map((q) => (
        <option key={q + 1} value={q + 1}>
          {q + 1}
        </option>
      ))}
    </select>
  </div>
</div>



            {/* Price */}
            <h4 className="text-success mb-3">₹{product.price}</h4>

            {/* Action Buttons */}
            <div className="d-flex gap-3">
              <button className="btn btn-primary btn-sm px-4" onClick={handleAddToCart}>
                Add to Cart
              </button>
              <button className="btn btn-success btn-sm px-4" onClick={showConfirmationToast}>
                Buy Now
              </button>
              </div>
          </div>
        </div>
      </div>
      <Footer />
      <ToastContainer position="top-center" />

    </>
  );
};

export default ProductDetails;
