import { createBrowserRouter } from "react-router-dom";
import App from "./App.js";
import Login from './components/Login/Login.jsx';
import SignUp from "./components/SignUp/SignUp.jsx";
import HomePage from "./components/HomePage/HomePage.jsx";
import ProductDetails from "./components/ProductDetails/ProductDetails.jsx";
import Cart from "./components/Cart/Cart.jsx";
import OrderHistory from "./components/OrderHistory/OrderHistory.jsx";
import ChangePassword from "./components/ChangePassword/ChangePassword.jsx";
import UserProfile from "./components/UserProfile/UserProfile.jsx";
import ProductList from "./components/ProductList/ProductList.jsx";
import Landing from "./components/LandingPage/Landing.jsx";
import EditProfile from "./components/EditProfile/EditProfile.jsx";
import ProtectedRoute from "./components/ProtectedRoute/ProtectdRoute.jsx";

const router = createBrowserRouter([
  { path: '', element: <Landing /> },
  { path: 'login', element: <App /> },
  { path: 'signup', element: <SignUp /> },

 
  { path: 'home', element: <ProtectedRoute><HomePage /></ProtectedRoute> },
  { path: 'product/:productId', element: <ProtectedRoute><ProductDetails /></ProtectedRoute> },
  { path: 'cart', element: <ProtectedRoute><Cart /></ProtectedRoute> },
  { path: 'orders', element: <ProtectedRoute><OrderHistory /></ProtectedRoute> },
  { path: 'password', element: <ProtectedRoute><ChangePassword /></ProtectedRoute> },
  { path: 'profile', element: <ProtectedRoute><UserProfile /></ProtectedRoute> },
  { path: 'product-list', element: <ProtectedRoute><ProductList /></ProtectedRoute> },
  { path: 'update-profile', element: <ProtectedRoute><EditProfile /></ProtectedRoute> },
]);

export default router;
