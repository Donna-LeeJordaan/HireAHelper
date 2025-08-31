import Nav from "../components/Nav.jsx";
import React from "react";

function ClientDashboard() {
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

export default ClientDashboard;

