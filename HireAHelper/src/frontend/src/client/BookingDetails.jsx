import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

const BookingDetails = () => {
    const { bookingId } = useParams();
    const navigate = useNavigate();
    const [booking, setBooking] = useState(null);

    useEffect(() => {
        axios
            .get(`http://localhost:8080/HireAHelper/booking/read/${bookingId}`)
    .then((res) => setBooking(res.data))
            .catch((err) => console.error("Error fetching booking details:", err));
    }, [bookingId]);

    if (!booking) return <p>Loading booking details...</p>;

    return (
        <div className="booking-details-container">
            <h2>Booking Details</h2>
            <div className="details-card">
                <p><strong>Service Provider:</strong> {booking.serviceProvider?.name}</p>
                <p><strong>Service:</strong> {booking.serviceProvider?.serviceType?.typeName}</p>
                <p><strong>Rate:</strong> R{booking.serviceProvider?.rate}</p>
                <p><strong>Booking Status:</strong> {booking.status}</p>
                <p><strong>Service Date:</strong> {booking.serviceDate}</p>
                <p><strong>Notes:</strong> {booking.notes || "No notes provided."}</p>
                <p><strong>Location:</strong> {booking.serviceProvider?.area?.name}</p>
            </div>
            <button className="btn-back" onClick={() => navigate(-1)}>Back</button>
        </div>
    );
};

export default BookingDetails;