import React from "react";
import { useNavigate } from "react-router-dom";
import "../../css/AdminDashboard.css";

export default function AdminDashboard() {
    const navigate = useNavigate();

    return (
        <div className="admin-container">
            <h1>Admin Dashboard</h1>
            <div className="admin-buttons">
                <button className="get-started-btn" onClick={() => navigate("/areas")}>
                    Manage Areas
                </button>
                <button className="get-started-btn" onClick={() => navigate("/service-types")}>
                    Manage Service Types
                </button>
            </div>
        </div>
    );
}
