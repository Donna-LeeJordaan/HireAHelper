import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import Nav from "../components/Nav.jsx";

const UpdateBookingStatus = () => {
    const user = JSON.parse(localStorage.getItem("user"));
    const { bookingId } = useParams();
    const navigate = useNavigate();
    const [booking, setBooking] = useState(null);
    const [status, setStatus] = useState("");

    useEffect(() => {
        axios
            .get(`http://localhost:8080/HireAHelper/booking/read/${bookingId}`)
            .then((res) => {
                setBooking(res.data);
                setStatus(res.data.status);
            })
            .catch((err) => console.error("Error fetching booking:", err));
    }, [bookingId]);

    const StatusUpdate = () => {
        axios
            .put("http://localhost:8080/HireAHelper/booking/update", {
                ...booking,
                status,
            })
            .then(() => {
                alert("Booking status updated");
                navigate(-1);
            })
            .catch((err) => console.error("Error updating status:", err));
    };

    if (!booking) return <p>Loading booking...</p>;

    return (
        <>
            <Nav user={user} />

        <div>
            <h2>Update Booking Status</h2>
            <p><strong>Booking ID:</strong> {booking.bookingId}</p>
            <p><strong>Client:</strong> {booking.client?.name}</p>
            <p><strong>Current Status:</strong> {booking.status}</p>

            <div>
                <label htmlFor="status-select">New Status:</label>
                <select
                    id="status-select"
                    value={status}
                    onChange={(e) => setStatus(e.target.value)}
                >
                    <option value="Pending">Pending</option>
                    <option value="Confirmed">Confirmed</option>
                    <option value="Completed">Completed</option>
                </select>
            </div>

            <div>
                <button onClick={StatusUpdate}>Update Status</button>
                <button onClick={() => navigate(-1)}>Cancel</button>
            </div>
        </div>
            </>
    );
};

export default UpdateBookingStatus;
