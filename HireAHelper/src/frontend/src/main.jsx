import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import {BrowserRouter as Router, Routes, Route, BrowserRouter} from "react-router-dom";
import "./index.css";

{/* main routes */}
import Home from "./Home/Home.jsx";
import AuthPage from "./Auth/AuthPage.jsx";
import Login from "./Auth/Login.jsx";
{/* sp routes */}
import ServiceProviderRegister from "./serviceProvider/ServiceProviderRegister.jsx";
import ServiceProviderDashboard from "./serviceProvider/ServiceProviderDashboard.jsx";
import ServiceProviderBookings from "./serviceProvider/ServiceProviderBooking.jsx";
import ServiceProviderBookingDetails from "./serviceProvider/ServiceProviderBookingDetails.jsx";
{/* client routes */}
import ClientRegister from "./client/ClientRegister.jsx";
import ClientDashboard from "./client/ClientDashboard.jsx";
import CreateBooking from "./Booking/CreateBooking.jsx"
import ClientBookings from "./client/ClientBookings.jsx";
import BookingDetails from "./client/BookingDetails.jsx";
{/* admin routes */}
import AdminDashboard from "./Admin/AdminDashboard.jsx";
{/* admin area routes */}
import AreaDashboard from "./Admin/Area/AreaDashboard.jsx";
import AreaCreate from "./Admin/Area/AreaCreate";
import AreaUpdate from "./Admin/Area/AreaUpdate.jsx";
{/* admin servicetype routes */}
import ServiceTypeDashboard from "./Admin/ServiceType/ServiceTypeDashboard.jsx";
import ServiceTypeCreate from "./Admin/ServiceType/ServiceTypeCreate.jsx";
import ServiceTypeUpdate from "./Admin/ServiceType/ServiceTypeUpdate.jsx";


createRoot(document.getElementById('root')).render(
    <StrictMode>
        <BrowserRouter>
            <Routes>

                {/* Main Routes */}
                <Route path="/" element={<Home />} />
                <Route path="/auth" element={<AuthPage/>} />
                <Route path="/login" element={<Login />} />

                {/* Admin Routes */}
                <Route path="/admin/Dashboard" element={<AdminDashboard/>} />

                {/* Service Provider Routes */}
                <Route path="/serviceProvider/Register" element={<ServiceProviderRegister/>} />
                <Route path="/serviceProvider/Dashboard" element={<ServiceProviderDashboard />} />

                {/* Client Routes */}
                <Route path="/client/register" element={<ClientRegister />} />
                <Route path="/client/dashboard" element={<ClientDashboard />} />

                {/* Area Routes */}
                <Route path="/area" element={<AreaDashboard />} />
                <Route path="/area/create" element={<AreaCreate />} />
                <Route path="/area/update/:areaId" element={<AreaUpdate />} />

                {/* ServiceType Routes */}
                <Route path="/serviceType" element={<ServiceTypeDashboard />} />
                <Route path="/serviceType/create" element={<ServiceTypeCreate />} />
                <Route path="/serviceType/update/:typeId" element={<ServiceTypeUpdate/>} />

                {/*Booking Routes*/}
                {/*Client Routes*/}
                <Route path="/createBookings" element={<CreateBooking/>} />
                <Route path="/clientBookings" element={<ClientBookings/>} />
                <Route path="/clientBooking/:bookingId" element={<BookingDetails/>}/>
                {/*ServiceProvider Routes*/}
                <Route path="/serviceProviderBookings" element={<ServiceProviderBookings />} />
                <Route path="/serviceProviderBookingDetails/:bookingId" element={<ServiceProviderBookingDetails />} />

            </Routes>
        </BrowserRouter>
    </StrictMode>,

)

