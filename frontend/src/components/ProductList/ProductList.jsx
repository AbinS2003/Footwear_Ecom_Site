import React, { useEffect, useState } from 'react';
import ProductCard from '../ProductCard/ProductCard';
import CustomNavbar from '../Navbar/CustomNavbar';
import Footer from '../Footer/Footer';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';

const ProductList = () => {


  const[products, setProducts] = useState([]);
  const itemsPerPage = 12;
  const [currentPage, setCurrentPage] = useState(1);
  const totalPages = Math.ceil(products.length / itemsPerPage);
  const indexOfLast = currentPage * itemsPerPage;
  const indexOfFirst = indexOfLast - itemsPerPage;
  const currentProducts = products.slice(indexOfFirst, indexOfLast);
  const navigate = useNavigate();

  const token = localStorage.getItem("token");

  const changePage = (pageNumber) => {
    if (pageNumber >= 1 && pageNumber <= totalPages) {
      setCurrentPage(pageNumber);
    }
  };



  const location = useLocation();

  const queryParams = new URLSearchParams(location.search);
  const category = queryParams.get("category");
  const brand = queryParams.get("brand");
  const query = queryParams.get("query");

  console.log(token);
  useEffect(() => {
  let apiUrl = "http://localhost:8080/api/products";

  if (query) {
    axios
      .get(`http://localhost:8080/api/products/search`, {
        params: { query },
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        setProducts(response.data);
      })
      .catch((error) => {
        console.log("Search fetch failed", error);
      });
  } else {
    if (brand) {
      apiUrl += `/brand/${brand}`;
    } else if (category) {
      apiUrl += `/category/${category}`;
    }

    axios
      .get(apiUrl, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        setProducts(response.data);
      })
      .catch((error) => {
        console.log("Fetch failed", error);
      });
  }
}, [brand, category, query, token]);


    const handleProductClick = (productId) => {
    navigate(`/product/${productId}`);
  }

  return (

    <>
    <CustomNavbar/>
    
     <div className="container mt-5"> 
        <div className="row mt-5">
          {currentProducts.length === 0 ? (
            <div className="text-center text-white fs-1 mt-5 mb5">No products available.</div>
          ) : (
            currentProducts.map((product) => (
              <ProductCard
                key={product.id}
                title={product.title}
                image={product.image}
                price={product.price}
                onClick={() => handleProductClick(product.id)}
              />
            ))
          )}
        </div>

      

      {/* Pagination */}
      <nav className="d-flex justify-content-center mt-4">
        <ul className="pagination">
          <li className={`page-item ${currentPage === 1 && "disabled"}`}>
            <button className="page-link" onClick={() => changePage(currentPage - 1)}>
              Previous
            </button>
          </li>
          {Array.from({ length: totalPages }, (_, i) => (
            <li
              key={i}
              className={`page-item ${currentPage === i + 1 ? "active" : ""}`}
            >
              <button className="page-link" onClick={() => changePage(i + 1)}>
                {i + 1}
              </button>
            </li>
          ))}
          <li className={`page-item ${currentPage === totalPages && "disabled"}`}>
            <button className="page-link" onClick={() => changePage(currentPage + 1)}>
              Next
            </button>
          </li>
        </ul>
      </nav>
    </div>
    <Footer />
    </>
  );
};

export default ProductList;
