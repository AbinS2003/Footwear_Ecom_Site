
import React, { useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import CustomNavbar from '../Navbar/CustomNavbar';
import Footer from '../Footer/Footer';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';


const OrderHistory = () => {

  const token = localStorage.getItem("token");
  const decoded = jwtDecode(token);
  const userId = decoded.userId || decoded.id;

  const [orders, setOrders] = useState([]);

  useEffect(() => {

    axios.get(`http://localhost:8080/api/order/${userId}`, {
       headers: { Authorization: "Bearer " + token },
    }).then(response => {
      setOrders(response.data);
      console.log(response.data);
    }).catch(error => {
      console.log("Failed to fetch products", error);
    })
  },[]);

const handleCancel = (productId) => {
  axios
    .post(
      `http://localhost:8080/api/order/cancel/${userId}/${productId}`,
      null,
      {
        headers: { Authorization: "Bearer " + token },
      }
    )
    .then(() => {
      // ✅ Re-fetch the updated orders
      axios
        .get(`http://localhost:8080/api/order/${userId}`, {
          headers: { Authorization: "Bearer " + token },
        })
        .then((response) => {
          setOrders(response.data);
          console.log("Orders after cancel:", response.data);
        })
        .catch((error) => {
          console.log("Failed to fetch orders after cancel", error);
        });
    })
    .catch((error) => {
      console.log("Cancel failed:", error.response?.data || error.message);
    });
};

  return (
    <>
      <CustomNavbar />
      <div className="container my-5" style={{ maxWidth: "900px" }}>
        <h3 className="mb-4 fw-bold text-center mt-3 fs-2">My Orders</h3>
        {orders.map(order => (
          <div
            key={order.id}
            className="d-flex align-items-center border rounded p-3 mb-3 shadow-sm bg-white justify-content-between"
          >
            {/* Image */}
            <img
              src={`http://localhost:8080/images/${order.image}`}
              alt={order.productTitle}
              style={{ width: "80px", height: "80px", objectFit: "contain" }}
              className="me-3"
            />

            {/* Info */}
            <div className="flex-grow-1 text-start ms-3">
              <h6 className="mb-1 fw-bold">{order.productTitle}</h6>
              <p className="mb-1 text-success fw-semibold">₹{order.price * order.quantity}</p>
              <p className="mb-1 text-muted small">Size: {order.sizeValue}</p>
              <p className="mb-0 text-muted small">Ordered on: {order.date}</p>
              <p className="mb-0 text-muted small">Qty: {order.quantity}</p>
            </div>

            {/* Status + Optional Cancel */}
            <div className="text-end d-flex flex-column align-items-end gap-2">
              <span className={`badge px-3 py-2 
                ${(order.status === "Cancelled" || order.status === "Pending")? "bg-danger" :
                    "bg-success"}`}>
                {order.status}
              </span>

              {order.status === "Pending" &&  (
            <button
              className="btn btn-sm btn-outline-danger mt-1"
              onClick={() => handleCancel(order.productId)} // ✅ wrap in arrow function
            >
              Cancel
            </button>

              )}
            </div>
          </div>
        ))}
      </div>
      <Footer /> 
    </>
  );
};

export default OrderHistory;
