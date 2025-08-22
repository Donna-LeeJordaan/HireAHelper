import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './index.css';
import ServiceProviderDashboard from './serviceProvider/ServiceProviderDashboard.jsx';
import ClientRegister from "./client/ClientRegister.jsx";
import ClientLogin from "./client/ClientLogin.jsx";
import ClientDashboard from "./client/ClientDashboard.jsx"; // Import ClientDashboard
import Home from "./Home.jsx";
import AuthPage from "./AuthPage.jsx";

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/auth" element={<AuthPage />} />
                <Route path="/service-provider-dashboard" element={<ServiceProviderDashboard />} />
                <Route path="/client/register" element={<ClientRegister />} />
                <Route path="/client/login" element={<ClientLogin />} />
                <Route path="/client/dashboard" element={<ClientDashboard />} />
            </Routes>
        </BrowserRouter>
    </StrictMode>,
);

