import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import Nav from "../components/Nav.jsx";
import "../css/BookingDetails.css";

const ServiceProviderBookingDetails = () => {
    const { bookingId } = useParams();
    const [booking, setBooking] = useState(null);
    const navigate = useNavigate();
    const [user, setUser] = useState(null);

    useEffect(() => {
        axios
            .get("http://localhost:8080/HireAHelper/serviceProvider/current-serviceProvider", {withCredentials: true})
            .then((res) => {setUser(res.data);

            return axios.get(`http://localhost:8080/HireAHelper/booking/read/${bookingId}`, {withCredentials: true});
            })
            .then((res) => {setBooking(res.data);})
            .catch((err) => {
                console.error("Error fetching the booking details:", err);
            });
    }, [bookingId]);

    if (!booking) return <p>Loading booking details...</p>;

    return (
        <>
            <Nav user={user} />

            <div className="booking-details-container">
                <h2>Booking Details</h2>
                <div className="details-card">
                    <p><strong>Client:</strong> {booking.client?.name}</p>
                    <p><strong>Client Email:</strong> {booking.client?.email}</p>
                    <p><strong>Client Mobile:</strong> {booking.client?.mobileNumber}</p>
                    <p><strong>Service:</strong> {booking.serviceProvider?.serviceType?.typeName}</p>
                    <p><strong>Rate:</strong> R{booking.serviceProvider?.rate}</p>
                    <p><strong>Booking Status:</strong> {booking.status}</p>
                    <p><strong>Service Date:</strong> {booking.serviceDate}</p>
                    <p><strong>Time:</strong>{booking.timeSlot}</p>
                    <p><strong>Notes:</strong> {booking.notes || "No notes provided."}</p>
                    <p><strong>Location:</strong> {booking.serviceProvider?.area?.name}</p>
                </div>
                <button
                    onClick={() => navigate(`/updateBooking/${booking.bookingId}`)}
                >
                    Update Status
                </button>

                <button className="btn-back" onClick={() => navigate(-1)}>Back</button>
            </div>
        </>
    );
};

export default ServiceProviderBookingDetails;
