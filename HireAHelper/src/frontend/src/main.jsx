import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './index.css'
import App from './App.jsx'
import ServiceProviderDashboard from './serviceProvider/ServiceProviderDashboard.jsx'
import ClientDashboard from './client/ClientDashboard.jsx'
import ServiceProviderRegister from "./serviceProvider/ServiceProviderRegister.jsx";
import ClientRegister from "./client/ClientRegister.jsx";

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <BrowserRouter>
            <Routes>

                <Route path="/" element={<App />} />
                <Route path="/serviceProviderRegister" element={<ServiceProviderRegister />} />
                <Route path="/service-provider-dashboard" element={<ServiceProviderDashboard />} />
                <Route path="/clientRegister" element={<ClientRegister />} />
                <Route path="/client-dashboard" element={<ClientDashboard />} />

            </Routes>
        </BrowserRouter>
    </StrictMode>,

)
