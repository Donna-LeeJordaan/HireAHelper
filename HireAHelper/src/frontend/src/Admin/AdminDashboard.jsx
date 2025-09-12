//Admin dashboard
import React, { useEffect, useState } from "react";
import axios from "axios";
import "../css/Dashboard.css";
import Nav from "../components/Nav.jsx";

export default function AdminDashboard() {
    const user = JSON.parse(localStorage.getItem("user"));
    const [clientCount, setClientCount] = useState(0);
    const [providerCount, setProviderCount] = useState(0);

    useEffect(() => {
        // Fetches client counts
        axios
            .get("http://localhost:8080/HireAHelper/client/count")
            .then((res) => setClientCount(res.data))
            .catch((err) => console.error("Error fetching client count:", err));

        // Fetches provider counts
        axios
            .get("http://localhost:8080/HireAHelper/serviceProvider/count")
            .then((res) => setProviderCount(res.data))
            .catch((err) => console.error("Error fetching provider count:", err));
    }, []);

    return (
        <>
            <Nav user={user} />

            <div className="main-content">
                <div className="dashboard-header">
                    <h1>Welcome, {user?.name || "User"}</h1>
                </div>

                <div className="booking-stats">
                    <div className="stat-card">
                        <h3>Total Clients</h3>
                        <p>{clientCount}</p>
                    </div>

                    <div className="stat-card">
                        <h3>Total Service Providers</h3>
                        <p>{providerCount}</p>
                    </div>
                </div>
            </div>
        </>
    );
}
