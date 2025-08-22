import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './index.css'
import ServiceProviderDashboard from './serviceProvider/ServiceProviderDashboard.jsx'
import ServiceProviderRegister from "./serviceProvider/ServiceProviderRegister.jsx";
import ClientRegister from "./client/ClientRegister.jsx";
import Home from "./Home.jsx";
import AuthPage from "./AuthPage.jsx";

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <BrowserRouter>
            <Routes>

                <Route path="/" element={<Home />} />
                <Route path="/auth" element={<AuthPage />} />
                <Route path="/serviceProviderRegister" element={<ServiceProviderRegister />} />
                <Route path="/service-provider-dashboard" element={<ServiceProviderDashboard />} />
                <Route path="/clientRegister" element={<ClientRegister />} />

            </Routes>
        </BrowserRouter>
    </StrictMode>,

)
