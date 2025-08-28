import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./index.css";
import ServiceProviderRegister from "./serviceProvider/ServiceProviderRegister.jsx";
import ServiceProviderDashboard from "./serviceProvider/ServiceProviderDashboard.jsx";
import ClientRegister from "./client/ClientRegister.jsx";
import Login from "./Auth/Login.jsx";
import ClientDashboard from "./client/ClientDashboard.jsx";
import Home from "./homePage/Home.jsx";
import AuthPage from "./Auth/AuthPage.jsx";
import AreaDashboard from "./Admin/Area/AreaDashboard.jsx";
import AreaCreate from "./Admin/Area/AreaCreate";
import AreaUpdate from "./Admin/Area/AreaUpdate.jsx";
import AreaDelete from "./Admin/Area/AreaDelete.jsx";

function App() {
    return (
        <Router>
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
                <Route path="/areas" element={<AreaDashboard />} />
                <Route path="/areas/create" element={<AreaCreate />} />
                <Route path="/areas/update/:id" element={<AreaUpdate />} />
                <Route path="/areas/delete/:id" element={<AreaDelete />} />

                {/*Booking Routes*/}



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

