import React, { useEffect, useState } from "react";
import Nav from "../components/Nav.jsx";
import "../css/Dashboard.css";
import axios from "axios";

function ServiceProviderDashboard() {
    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user?.userId;

    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios
            .get(`http://localhost:8080/HireAHelper/booking/serviceProvider/${userId}`)
            .then(res => {
                setBookings(res.data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Error fetching bookings:", err);
                setLoading(false);
            });
    }, [userId]);

    const confirmedBookings = bookings.filter(b => b.status === "Confirmed").length;
    const pendingBookings = bookings.filter(b => b.status === "Pending").length;

    const upcomingBookings = bookings.filter(b => {
        const today = new Date();
        const serviceDate = new Date(b.serviceDate);
        return serviceDate > today;
    }).length;

    return (
        <>
            <Nav user={user} />

            <div className="main-content">
                <h1>Welcome, {user?.name || "User"}</h1>

                <div className="booking-stats">
                    <div className="stat-card">
                        <h3>Confirmed</h3>
                        <p>{confirmedBookings}</p>
                    </div>
                    <div className="stat-card">
                        <h3>Pending</h3>
                        <p>{pendingBookings}</p>
                    </div>
                    <div className="stat-card">
                        <h3>Upcoming</h3>
                        <p>{upcomingBookings}</p>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ServiceProviderDashboard;
