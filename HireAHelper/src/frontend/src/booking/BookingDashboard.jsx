import React, { useState } from "react";
import "../css/BookingDashboard.css";
import logo from "../assets/logo1.png"; // adjust path if needed

export default function BookingDashboard() {
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

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("Booking Created:", formData);
        alert(`Booking Created for ${formData.clientName}`);
    };

    return (
        <div className="booking-container">
            {/* Logo top-left */}
            <div className="logo-container">
                <img src={logo} alt="Hire A Helper" className="logo" />
            </div>

            <h1>Booking Dashboard</h1>
            <p>Manage and create bookings with ease</p>

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
                    Confirm Booking
                </button>
            </form>
        </div>
    );
}
