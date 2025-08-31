import React from "react";
import Nav from "../components/Nav.jsx";
import "../css/Dashboard.css";

function ServiceProviderDashboard() {
    const user = JSON.parse(localStorage.getItem("user"));

    return (
        <>
            <Nav user={user} />

            <div className="main-content">
                <h1>Welcome, {user?.name || "User"}</h1>
            </div>

        </>

    );
}

export default ServiceProviderDashboard;
