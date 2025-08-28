import React, { useState } from "react";
import axios from "axios";
import "./BookingDashboard.css"; // reuse same CSS
import logo from "../assets/logo1.png"; // adjust path if needed

export default function CreateBooking() {
    const [formData, setFormData] = useState({
        bookingId: "",
        date: "",
        status: "Scheduled",
        notes: "",
        clientName: "",
        providerName: "",
        area: "",
        serviceType: "",
    });

    const [message, setMessage] = useState("");

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            // Map form data to backend booking object
            const bookingPayload = {
                bookingId: formData.bookingId,
                date: formData.date,
                status: formData.status,
                notes: formData.notes,
                client: {
                    name: formData.clientName,
                    area: { name: formData.area },
                    email: `${formData.clientName.toLowerCase()}@example.com`
                },
                provider: {
                    name: formData.providerName,
                    area: { name: formData.area },
                    serviceType: { typeName: formData.serviceType },
                    email: `${formData.providerName.toLowerCase()}@example.com`
                }
            };

            const response = await axios.post(
                "http://localhost:8080/api/bookings",
                bookingPayload
            );

            setMessage(`✅ Booking Created: ${response.data.bookingId}`);
            setFormData({
                bookingId: "",
                date: "",
                status: "Scheduled",
                notes: "",
                clientName: "",
                providerName: "",
                area: "",
                serviceType: "",
            });
        } catch (error) {
            console.error("Error creating booking:", error);
            setMessage("❌ Failed to create booking. Please check backend.");
        }
    };

    return (
        <div className="booking-container">
            {/* Logo top-left */}
            <div className="logo-container">
                <img src={logo} alt="Hire A Helper" className="logo" />
            </div>

            <h1>Create New Booking</h1>
            <p>Fill in the details below to schedule a booking</p>

            <form className="booking-form" onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="bookingId"
                    placeholder="Booking ID"
                    value={formData.bookingId}
                    onChange={handleChange}
                    required
                />
                <input
                    type="date"
                    name="date"
                    value={formData.date}
                    onChange={handleChange}
                    required
                />
                <select name="status" value={formData.status} onChange={handleChange}>
                    <option value="Scheduled">Scheduled</option>
                    <option value="Completed">Completed</option>
                    <option value="Cancelled">Cancelled</option>
                </select>
                <textarea
                    name="notes"
                    placeholder="Special Notes"
                    value={formData.notes}
                    onChange={handleChange}
                />
                <input
                    type="text"
                    name="clientName"
                    placeholder="Client Name"
                    value={formData.clientName}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="providerName"
                    placeholder="Service Provider Name"
                    value={formData.providerName}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="area"
                    placeholder="Area"
                    value={formData.area}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="serviceType"
                    placeholder="Service Type (e.g. Gardener)"
                    value={formData.serviceType}
                    onChange={handleChange}
                    required
                />

                <button type="submit" className="submit-btn">
                    Create Booking
                </button>
            </form>

            {message && <p className="status-message">{message}</p>}
        </div>
    );
}
