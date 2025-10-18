//Ameeruddin Arai 230190839
import React, { useEffect, useState } from "react";
import axios from "axios";
import "../css/Dashboard.css";
import Nav from "../components/Nav.jsx";

export default function AdminDashboard() {
    const user = JSON.parse(localStorage.getItem("user"));
    const [clientCount, setClientCount] = useState(0);
    const [providerCount, setProviderCount] = useState(0);

    useEffect(() => {
        const fetchCounts = async () => {
            try {
                // Fetch both clients and service providers at once
                const [clientRes, providerRes] = await Promise.all([
                    axios.get("http://localhost:8080/client/all"),
                    axios.get("http://localhost:8080/serviceProvider/all")
                ]);

                // Use the array length as count
                setClientCount(Array.isArray(clientRes.data) ? clientRes.data.length : 0);
                setProviderCount(Array.isArray(providerRes.data) ? providerRes.data.length : 0);
            } catch (error) {
                console.error("Error fetching counts:", error);
                setClientCount(0);
                setProviderCount(0);
            }
        };

        fetchCounts();
    }, []);

    return (
        <>
            <Nav user={user} />

            <div className="main-content">
                <div className="dashboard-header">
                    <h1>Welcome, {user?.name || "Admin"}</h1>
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
