import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import {BrowserRouter as Router, Routes, Route, BrowserRouter} from "react-router-dom";

import "./index.css";
import ServiceProviderRegister from "./serviceProvider/ServiceProviderRegister.jsx";
import ServiceProviderDashboard from "./serviceProvider/ServiceProviderDashboard.jsx";
import ClientRegister from "./client/ClientRegister.jsx";
import Login from "./Auth/Login.jsx";
import ClientDashboard from "./client/ClientDashboard.jsx";
import Home from "./Home/Home.jsx";
import AuthPage from "./Auth/AuthPage.jsx";
import AreaDashboard from "./Admin/Area/AreaDashboard.jsx";
import AreaCreate from "./Admin/Area/AreaCreate";
import AreaUpdate from "./Admin/Area/AreaUpdate.jsx";


createRoot(document.getElementById('root')).render(
    <StrictMode>
        <BrowserRouter>
            <Routes>

                {/* Main Routes */}
                <Route path="/" element={<Home />} />
                <Route path="/auth" element={<AuthPage/>} />
                <Route path="/login" element={<Login />} />

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


                {/*Booking Routes*/}



            </Routes>
        </BrowserRouter>
    </StrictMode>,

)

