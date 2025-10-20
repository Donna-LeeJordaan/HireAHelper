import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Nav from "../components/Nav.jsx";
import "../css/Bookings.css";

const ClientBookings = () => {
    const [user, setUser] = useState(null);
    const [bookings, setBookings] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        axios
            .get("http://localhost:8080/HireAHelper/client/current-client", {withCredentials: true})
            .then((res) => {setUser(res.data);

                return axios.get(`http://localhost:8080/HireAHelper/booking/client/${res.data.userId}`, {withCredentials: true});
            })
            .then((res) => {setBookings(res.data);})
            .catch((err) => {
                console.error("Error fetching client or bookings:", err);
            });
    }, []);

    return (
        <>
            <Nav user={user} />

            <div className="page-wrapper">
                <div className="bookings-container">
                    <h1>My Bookings</h1>

                    {bookings.length === 0 ? (
                        <p className="no-bookings">You have no bookings yet.</p>
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
                                        onClick={() => navigate(`/clientBooking/${b.bookingId}`)}
                                    >
                                        View Details
                                    </button>
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            </div>
        </>
    );
};

export default ClientBookings;
