import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import Nav from "../components/Nav.jsx";
import "../css/BookingDetails.css";

const BookingDetails = () => {
    const [user, setUser] = useState(null);
    const [booking, setBooking] = useState([]);
    const navigate = useNavigate();
    const { bookingId } = useParams();

    useEffect(() => {
        axios.get("http://localhost:8080/HireAHelper/user/user", {withCredentials: true})
            .then((res) => { setUser(res.data);

                return axios.get(`http://localhost:8080/HireAHelper/booking/read/${bookingId}`,{withCredentials: true});
            })
            .then((res) => {setBooking(res.data);})
            .catch((err) => {
                console.error("Error fetching booking details:", err);
            });
    }, [bookingId]);

    if (!booking) return <p>Loading booking details...</p>;
    return (
        <>
            <Nav user={user} />

            <div className="booking-details-container">
                <h2>Booking Details</h2>
                <div className="details-card">
                    <strong>Service Provider:</strong> {booking.serviceProvider?.name}
                    <strong>Service:</strong> {booking.serviceProvider?.serviceType?.typeName}
                    <strong>Rate:</strong> R{booking.serviceProvider?.rate}
                    <strong>Booking Status:</strong> {booking.status}
                    <strong>Service Date:</strong> {booking.serviceDate}
                    <strong>Timeslot:</strong>{booking.timeSlot}
                    <strong>Notes:</strong> {booking.notes || "No notes provided."}
                    <strong>Location:</strong> {booking.serviceProvider?.area?.name}
                </div>
                <button className="btn-back" onClick={() => navigate(-1)}>Back</button>
            </div>
        </>
    );
};

export default BookingDetails;