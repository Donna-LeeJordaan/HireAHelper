import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./index.css";

import ServiceProviderDashboard from "./serviceProvider/ServiceProviderDashboard.jsx";
import ClientRegister from "./client/ClientRegister.jsx";
import ClientLogin from "./client/ClientLogin.jsx";
import ClientDashboard from "./client/ClientDashboard.jsx";
import Home from "./Home.jsx";
import AuthPage from "./AuthPage.jsx";
import AreaDashboard from "./AreaDashboard";
import AreaCreate from "./AreaCreate";
import AreaUpdate from "./AreaUpdate";

function App() {
    return (
        <Router>
            <Routes>
                {/* Main Routes */}
                <Route path="/" element={<Home />} />
                <Route path="/auth" element={<AuthPage />} />

                {/* Service Provider Routes */}
                <Route path="/service-provider-dashboard" element={<ServiceProviderDashboard />} />

                {/* Client Routes */}
                <Route path="/client/register" element={<ClientRegister />} />
                <Route path="/client/login" element={<ClientLogin />} />
                <Route path="/client/dashboard" element={<ClientDashboard />} />

                {/* Area Routes */}
                <Route path="/areas" element={<AreaDashboard />} />
                <Route path="/areas/create" element={<AreaCreate />} />
                <Route path="/areas/update/:id" element={<AreaUpdate />} />
                <Route path="/areas/delete/:id" element={<AreaDelete />} />

            </Routes>
        </Router>
    );
}

createRoot(document.getElementById("root")).render(
    <StrictMode>
        <App />
    </StrictMode>
);

export default App;

