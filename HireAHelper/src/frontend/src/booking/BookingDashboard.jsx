import React, { useEffect, useState } from 'react';

const Bookings = () => {
    const [bookings, setBookings] = useState([]);

    useEffect(() => {
        const fetchBookings = async () => {
            const response = await fetch('/api/bookings/client');
            const data = await response.json();
            setBookings(data);
        };
        fetchBookings();
    }, []);

    return (
        <div>
            <h1>Your Bookings</h1>
            {bookings.map((booking) => (
                <div key={booking.id}>
                    <h3>Booking ID: {booking.id}</h3>
                    <button onClick={() => window.location.href = `/booking-details/${booking.id}`}>View Details</button>
                </div>
            ))}
        </div>
    );
};

export default Bookings;
