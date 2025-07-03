import React, { useEffect, useState } from 'react';
import CategoryCard from '../CategoryCard/CategoryCard';
import BrandCard from '../BrandCard/BrandCard';
import ProductCard from '../ProductCard/ProductCard';
import Footer from '../Footer/Footer';
import CustomNavbar from '../Navbar/CustomNavbar';
import { useNavigate } from 'react-router-dom';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import axios from 'axios';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/navigation';
import { Navigation } from 'swiper/modules';
import { Autoplay } from 'swiper/modules';
import 'swiper/css/navigation';
import 'swiper/css/autoplay';
import { useRef } from 'react';


const HomePage = () => {
  

const token = localStorage.getItem("token");

const navigate = useNavigate();

var [categories, setCategories] = useState([]);
var [brands, setBrands] = useState([]);

useEffect(() => {
  axios.get('http://localhost:8080/api/products/categories',{ 
    headers:{'Authorization':"Bearer "+ token}
    }).then(response => {

    setCategories(response.data)
    console.log(response.data);
  }).catch(error => {
    console.log("Failed to fetch categories", error);
  })
}, []);


useEffect(() =>{

  axios.get('http://localhost:8080/api/products/brands',{
    headers:{'Authorization':"Bearer "+ token}
  }).then(response => {

    setBrands(response.data);
  }).catch(error => {
    console.log("Failed to fetch brands", error);
  })
}, []);

var [newArrivals, setNewArrivals] = useState([]);

useEffect(() =>{

  axios.get('http://localhost:8080/api/products/new-arrivals',{
    headers:{'Authorization': "Bearer "+ token}
  }).then(response =>{
    setNewArrivals(response.data);
  }).catch(error => {
    console.log("Failed to fetch products", error);
  })
}, []);

 const handleCategoryClick = (categoryName) => {
    navigate(`/product-list?category=${categoryName}`);
  };

  const handleBrandClick = (brandName) =>{
    navigate(`/product-list?brand=${brandName}`);
  }

  const handleProductClick = (productId) => {
    navigate(`/product/${productId}`);
  }

  const swiperRef = useRef(null);




  return (
    <>
    <CustomNavbar/>
    
    <div className="container py-5">

      {/* container section */}
  
      <h2 className="my-5 text-center">Shop By Category</h2>

      <div className="row">
          {categories.map((cat, idx) => (
        <div
            className="col-md-3 mb-4"
              key={idx}
              onClick={() => handleCategoryClick(cat.categoryName)}
               style={{ cursor: "pointer" }}
        >
          <CategoryCard title={cat.categoryName} image={`http://localhost:8080/images/${cat.image}`} />
          </div>
        ))}
      </div>


      {/* Brand Section */}
      

      <h2 className="my-5 text-center">Top Brands</h2>


        <Swiper
          modules={[Navigation, Autoplay]}
          navigation
          spaceBetween={5}
          slidesPerView={5}
          onSwiper={(swiper) => (swiperRef.current = swiper)}
          autoplay={{
            delay: 1000,
            disableOnInteraction: false,
            pauseOnMouseEnter: false,
          }}
          breakpoints={{
            640: { slidesPerView: 1 },
            768: { slidesPerView: 2 },
            1024: { slidesPerView: 3 },
            1280: { slidesPerView: 6 },
          }}
          onMouseEnter={() => swiperRef.current?.autoplay?.start()}
          onMouseLeave={() => swiperRef.current?.autoplay?.stop()}
        >
          {brands.map((brand, idx) => (
            <SwiperSlide key={idx}>
              <div onClick={() => handleBrandClick(brand.name)} style={{ cursor: 'pointer' }}>
                <BrandCard name={brand.name} image={brand.image} />
              </div>
            </SwiperSlide>
          ))}
        </Swiper>
    
      <h2 className="my-5 text-center">New Arrivals</h2>

        <div className="row">
          {newArrivals.filter(product => product.status !== 'Hidden')
          .map((product) => (
            <ProductCard
              key={product.id}
              title={product.title}
              image={product.image}
              price={product.price}
              onClick={() => handleProductClick(product.id)}
            />
          ))}
        </div>

    </div>
    <Footer/>
    </>
  );
};

export default HomePage;