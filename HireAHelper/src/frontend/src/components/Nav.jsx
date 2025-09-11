import React from "react";
import { useNavigate } from "react-router-dom";
import "../css/Nav.css";

const Nav = ({ user }) => {
    const navigate = useNavigate();

    const handleDashboardClick = () => {
        if (!user) return;
        if (user.role === "CLIENT") navigate("/client/dashboard");
        else if (user.role === "ADMIN") navigate("/admin/dashboard");
        else if (user.role === "SERVICE_PROVIDER") navigate("/serviceProvider/dashboard");
    };

    const handleBookingClick = () => {
        if (!user) return;
        if (user.role === "CLIENT") navigate("/clientBookings");
        else if (user.role === "SERVICE_PROVIDER") navigate("/serviceProviderBookings");
    };

    const handleAreaClick = () => {
        if (!user) return;
        if (user.role === "ADMIN") navigate("/area");
    };

    const handleServiceTypeClick = () => {
        if (!user) return;
        if (user.role === "ADMIN") navigate("/serviceType");
    };

    const handleLogout = () => {
        navigate ("/");
    };

    return (
        <nav className="navigation">
            <h2>Hire A Helper</h2>

            <ul className="nav-list">
                <li><button onClick={handleDashboardClick}>Dashboard</button></li>

                {/* for sp and client bookings */}
                {(user?.role === "CLIENT" || user?.role === "SERVICE_PROVIDER") && (
                    <li><button onClick={handleBookingClick}>Bookings</button></li>
                )}

                {/* admin nav */}
                {user?.role === "ADMIN" && (
                    <>
                    <li><button onClick={handleAreaClick}>Manage Areas</button></li>
                    <li><button onClick={handleServiceTypeClick}>Manage Services</button></li>
                    </>
                )}
                <li><button onClick={handleLogout}>Logout</button></li>
            </ul>
        </nav>
    );
};

export default Nav;
