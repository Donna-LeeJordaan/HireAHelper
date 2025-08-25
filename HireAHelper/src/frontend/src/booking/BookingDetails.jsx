import React, { useEffect, useState } from 'react';

const BookingDetails = ({ match }) => {
    const [booking, setBooking] = useState(null);

    useEffect(() => {
        const fetchBookingDetails = async () => {
            const response = await fetch(`/api/bookings/${match.params.id}`);
            const data = await response.json();
            setBooking(data);
        };
        fetchBookingDetails();
    }, [match.params.id]);

    if (!booking) return <div>Loading...</div>;

    return (
        <div>
            <h1>Booking Details</h1>
            <p>Booking ID: {booking.id}</p>
            <p>Service Provider: {booking.serviceProvider.name}</p>
            <p>Client: {booking.client.name}</p>
            <p>Date: {booking.date}</p>
            <p>Status: {booking.status}</p>
        </div>
    );
};

export default BookingDetails;
