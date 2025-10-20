import Nav from "../components/Nav.jsx";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function ClientDashboard() {
    const navigate = useNavigate();
    const [user, setUser] = useState(null);
    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios.get("http://localhost:8080/HireAHelper/client/current-client", {withCredentials: true})
            .then((res) => {
                setUser(res.data);

                return axios.get(`http://localhost:8080/HireAHelper/booking/client/${res.data.userId}`, {
                withCredentials: true});
    })
        .then((res) => {
            setBookings(res.data);
            setLoading(false);
        })
        .catch((err) => {
            console.error("Error fetching the client or bookings:", err);
            setLoading(false);
        });
}, []);

    const totalBookings = bookings.length;
    const confirmedBookings = bookings.filter(b => b.status === "Confirmed").length;
    const pendingBookings = bookings.filter(b => b.status === "Pending").length;

    return (
        <>
            <Nav user={user} />

            <div className="main-content">
                <div className="dashboard-header">

                    <h1>Welcome, {user?.name || "User"}</h1>

                    <button
                        className="create-booking-btn"
                        onClick={() => navigate("/createBookings")}
                    >
                        Create Booking
                    </button>
                </div>

                {loading ? (
                    <p>Loading bookings...</p>
                ) : (
                    <div className="booking-stats">
                        <div className="stat-card">
                            <h3>Total Bookings</h3>
                            <p>{totalBookings}</p>
                        </div>
                        <div className="stat-card">
                            <h3>Confirmed</h3>
                            <p>{confirmedBookings}</p>
                        </div>
                        <div className="stat-card">
                            <h3>Pending</h3>
                            <p>{pendingBookings}</p>
                        </div>
                    </div>
                )}
            </div>
        </>
    );
}

export default ClientDashboard;



