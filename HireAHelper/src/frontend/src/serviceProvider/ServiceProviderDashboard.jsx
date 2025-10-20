import React, { useEffect, useState } from "react";
import Nav from "../components/Nav.jsx";
import "../css/Dashboard.css";
import axios from "axios";

function ServiceProviderDashboard() {
    const [user, setUser] = useState(null);
    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios.get("http://localhost:8080/HireAHelper/serviceProvider/current-serviceProvider", {withCredentials: true})
            .then((res) => {
                setUser(res.data);

        return axios.get(`http://localhost:8080/HireAHelper/booking/serviceProvider/${res.data.userId}`, {
                withCredentials: true});
            })
            .then((res) => {
                setBookings(res.data);
                setLoading(false);
            })
            .catch((err) => {
                console.error("Error fetching the service provider or bookings:", err);
                setLoading(false);
            });
    }, []);

    const confirmedBookings = bookings.filter(b => b.status === "Confirmed").length;
    const pendingBookings = bookings.filter(b => b.status === "Pending").length;
    const completedBookings = bookings.filter(b => b.status === "Completed").length;

    return (
        <> <Nav user={user} />
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
                        <h3>Completed</h3>
                        <p>{completedBookings}</p>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ServiceProviderDashboard;
