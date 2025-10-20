import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Nav from "../components/Nav.jsx";
import "../css/Bookings.css";

const ServiceProviderBookings = () => {
    const [bookings, setBookings] = useState([]);
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        axios
            .get("http://localhost:8080/HireAHelper/serviceProvider/current-serviceProvider", {withCredentials: true})
            .then((res) => {setUser(res.data);

        const serviceProviderId = res.data.userId;
        return axios.get(`http://localhost:8080/HireAHelper/booking/serviceProvider/${serviceProviderId}`,{withCredentials: true});
            })
            .then((res) => {setBookings(res.data);})
            .catch((err) => {
                console.error("Error fetching service provider or bookings:", err);
            });
    }, []);

    return (
        <>
            <Nav user={user} />

            <div className="bookings-container">
                <h2>Bookings</h2>
                {bookings.length === 0 ? (
                    <p>No bookings yet.</p>
                ) : (
                    <div className="bookings-list">
                        {bookings.map((b) => (
                            <div key={b.bookingId} className="booking-card">
                                <p><strong>Client:</strong> {b.client?.name || b.client?.userId}</p>
                                <p><strong>Service:</strong> {b.serviceProvider?.serviceType?.typeName}</p>
                                <p><strong>Date:</strong> {b.serviceDate}</p>
                                <p><strong>Booking Status:</strong> {b.status}</p>
                                <button
                                    className="btn-details"
                                    onClick={() => navigate(`/serviceProviderBookingDetails/${b.bookingId}`)}
                                >
                                    View Details
                                </button>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </>
    );
};

export default ServiceProviderBookings;
