import React from "react";
import { useNavigate } from "react-router-dom";
import "../../css/Dashboard.css";

export default function AdminDashboard() {
    const navigate = useNavigate();

    return (
        <div className="admin-layout">
            <aside className="sidebar">
                <h2 className="sidebar-title">Admin Panel</h2>
                <nav className="sidebar-nav">
                    <button onClick={() => navigate("/area")}>Manage Areas</button>
                    <button onClick={() => navigate("/serviceType")}>Manage Service Types</button>
                </nav>
            </aside>

            <main className="main-content">
                <h1>Welcome, Admin</h1>

            </main>
        </div>
    );
}
