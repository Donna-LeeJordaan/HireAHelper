ClientBookings.jsx:

import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const ClientBookings = () => {
    const [bookings, setBookings] = useState([]);
    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user?.userId;
    const navigate = useNavigate();

    useEffect(() => {
        axios
            .get(http://localhost:8080/HireAHelper/booking/client/${userId})
    .then((res) => setBookings(res.data))
            .catch((err) => console.error("Error fetching bookings:", err));
    }, [userId]);

    return (
        <div className="bookings-container">
            <h2>My Bookings</h2>
            {bookings.length === 0 ? (
                <p>You have no bookings yet.</p>
            ) : (
                <div className="bookings-list">
                    {bookings.map((b) => (
                        <div key={b.bookingId} className="booking-card">
                            <p><strong>Service Provider:</strong> {b.serviceProvider?.name}</p>
                            <p><strong>Service:</strong> {b.serviceProvider?.serviceType?.typeName}</p>
                            <p><strong>Status:</strong> {b.status}</p>
                            <p><strong>Date:</strong> {b.serviceDate}</p>
                            <button
                                className="btn-details"
                                onClick={() => navigate(/clientBooking/${b.bookingId})}
                            >
                                View Details
                            </button>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default ClientBookings;