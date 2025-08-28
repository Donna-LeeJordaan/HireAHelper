// BookingDetails.jsx
import React from 'react';
import "../css/BookingDashboard.css"; // Reusing the same CSS file

const BookingDetails = ({ booking }) => {
    // Sample booking object if no props passed
    const sampleBooking = {
        id: 'BKG12345',
        clientName: 'John Doe',
        service: 'Home Cleaning',
        date: '2025-08-27',
        time: '14:00',
        status: 'Confirmed',
        notes: 'Client prefers eco-friendly products'
    };

    const currentBooking = booking || sampleBooking;

    return (
        <div className="booking-container">
            <div className="logo-container">
                <img src="/logo.png" alt="Logo" className="logo" />
            </div>
            <h1>Booking Details</h1>
            <p>View all details of your booking below.</p>

            <div className="booking-form">
                <input type="text" value={`Booking ID: ${currentBooking.id}`} readOnly />
                <input type="text" value={`Client Name: ${currentBooking.clientName}`} readOnly />
                <input type="text" value={`Service: ${currentBooking.service}`} readOnly />
                <input type="text" value={`Date: ${currentBooking.date}`} readOnly />
                <input type="text" value={`Time: ${currentBooking.time}`} readOnly />
                <input type="text" value={`Status: ${currentBooking.status}`} readOnly />
                <textarea value={`Notes: ${currentBooking.notes}`} readOnly />
            </div>
        </div>
    );
};

export default BookingDetails;
