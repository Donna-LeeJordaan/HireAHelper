import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./index.css";

// Imports
import ServiceProviderDashboard from "./serviceProvider/ServiceProviderDashboard.jsx";
import ClientRegister from "./client/ClientRegister.jsx";
import ClientLogin from "./client/ClientLogin.jsx";
import ClientDashboard from "./client/ClientDashboard.jsx";
import Home from "./homePage/Home.jsx";
import AuthPage from "./authPage/AuthPage.jsx";
import AreaDashboard from "./AreaDashboard";
import AreaCreate from "./AreaCreate";
import AreaUpdate from "./AreaUpdate";
import BookingDetails from './pages/BookingDetails';
import CreateBooking from './pages/CreateBooking';
import BookingDashboard from './pages/BookingDashboard';

// App Component
function App() {
    return (
        <Router>
            <Routes>
                {/* Main Routes */}
                <Route path="/" element={<Home />} />
                <Route path="/auth" element={<AuthPage />} />
                <Route path="/service-provider-dashboard" element={<ServiceProviderDashboard />} />
                <Route path="/client/register" element={<ClientRegister />} />
                <Route path="/client/login" element={<ClientLogin />} />
                <Route path="/client/dashboard" element={<ClientDashboard />} />

                {/* Area Routes */}
                <Route path="/areas" element={<AreaDashboard />} />
                <Route path="/areas/create" element={<AreaCreate />} />
                <Route path="/areas/update/:id" element={<AreaUpdate />} />
                <Route path="/areas/delete/:id" element={<AreaDelete />} />

                {/*Booking Routes*/}
                <Route path="/create-booking" component={CreateBooking} />
                <Route path="/booking-dashboard" component={BookingDashboard} />
                <Route path="/booking-details/:id" component={BookingDetails} />
                <Route path="/" exact component={Home} />
            </Routes>
        </Router>
    );
}

// Mount App
createRoot(document.getElementById("root")).render(
    <StrictMode>
        <App />
    </StrictMode>
);

export default App;

