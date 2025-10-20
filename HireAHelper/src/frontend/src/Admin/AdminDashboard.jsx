// Ameeruddin Arai 230190839

import React, { useEffect, useState } from "react";
import axios from "axios";
import "../css/Dashboard.css";
import Nav from "../components/Nav.jsx";

export default function AdminDashboard() {
    const [user, setUser] = useState(null);
    const [clientCount, setClientCount] = useState(0);
    const [providerCount, setProviderCount] = useState(0);

    useEffect(() => {
        axios.defaults.withCredentials = true;

        const fetchData = async () => {
            try {
                const userRes = await axios.get("http://localhost:8080/HireAHelper/admin/current-admin", {
                    withCredentials: true
                });
                setUser(userRes.data);

                const [clientRes, providerRes] = await Promise.all([
                    axios.get("http://localhost:8080/HireAHelper/client/all", {withCredentials: true}),
                    axios.get("http://localhost:8080/HireAHelper/serviceProvider/all", {withCredentials: true})
                ]);

                setClientCount(Array.isArray(clientRes.data) ? clientRes.data.length : 0);
                setProviderCount(Array.isArray(providerRes.data) ? providerRes.data.length : 0);
            } catch (error) {
                console.error("Error fetching admin data:", error);
                setClientCount(0);
                setProviderCount(0);
            }
        };

        fetchData();
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
