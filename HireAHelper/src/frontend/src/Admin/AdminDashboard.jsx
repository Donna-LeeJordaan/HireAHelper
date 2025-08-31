import React from "react";
import "../css/Dashboard.css";
import Nav from "../components/Nav.jsx";


export default function AdminDashboard() {
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
