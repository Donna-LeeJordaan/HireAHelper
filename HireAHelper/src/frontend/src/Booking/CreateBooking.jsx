import React, { useState, useEffect } from "react";
import axios from "axios";
import "../css/CreateBooking.css";
import logo from "../assets/logo1.png";

const CreateBooking = () => {
    const generateBookingId = () => `BOOKING-${Math.floor(100000 + Math.random() * 900000)}`;
    const [serviceProviders, setServiceProviders] = useState([]);
    const [filteredProviders, setFilteredProviders] = useState([]);
    const [serviceType, setServiceType] = useState("");
    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user?.userId;
    const [clientArea, setClientArea] = useState(null);
    const [serviceProviderId, setServiceProviderId] = useState("");
    const [serviceDate, setServiceDate] = useState("");
    const [notes, setNotes] = useState("");
    const [step, setStep] = useState(1);

    useEffect(() => {
        axios
            .get(`http://localhost:8080/HireAHelper/client/read/${userId}`)
            .then((res) => setClientArea(res.data.area))
            .catch((err) => console.error("Error fetching client:", err));
    }, [userId]);

    useEffect(() => {
        axios
            .get("http://localhost:8080/HireAHelper/serviceProvider/all")
            .then((res) => {
                const providers = res.data.map((sp) => ({
                    ...sp,
                    area: sp.area || sp.user?.area || null,
                }));
                setServiceProviders(providers);
            })
            .catch((err) => console.error("Error fetching providers:", err));
    }, []);

    const handleSearch = () => {
        if (!clientArea) {
            alert("Client area not set!");
            return;
        }
        const filtered = serviceProviders.filter(
            (sp) =>
                sp.serviceType?.typeName.toLowerCase().includes(serviceType.toLowerCase()) &&
                sp.area?.areaId === clientArea.areaId
        );
        setFilteredProviders(filtered);
        setStep(2);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!serviceProviderId) {
            alert("Please select a service provider");
            return;
        }
        const booking = {
            bookingId: generateBookingId(),
            client: { userId },
            serviceProvider: { userId: serviceProviderId },
            serviceDate,
            notes,
            status: "Pending",
        };
        axios
            .post("http://localhost:8080/HireAHelper/booking/create", booking)
            .then(() => {
                alert("Booking created successfully!");
                setStep(1);
                setServiceType("");
                setFilteredProviders([]);
                setServiceProviderId("");
                setServiceDate("");
                setNotes("");
            })
            .catch((err) => console.error("Error creating booking:", err));
    };

    const selectedProvider = filteredProviders.find(
        (sp) => sp.userId === serviceProviderId
    );
    return (
        <div className="page-wrapper">
            <div className="app-container">
                <img src={logo} alt="Logo" className="logo" />
                <h1>Create Booking</h1>

                {step === 1 && (
                    <div className="search-container">
                        <input
                            type="text"
                            value={serviceType}
                            onChange={(e) => setServiceType(e.target.value)}
                            placeholder="Search service"
                            className="search-input"
                        />
                        <button onClick={handleSearch} className="get-started-btn">Search</button>
                    </div>
                )}

                {step === 2 && filteredProviders.length > 0 && (
                    <div className="provider-grid">
                        {filteredProviders.map((sp) => (
                            <div
                                key={sp.userId}
                                className={`provider-card ${serviceProviderId === sp.userId ? "selected" : ""}`}
                                onClick={() => setServiceProviderId(sp.userId)}>

                                <img src={sp.profileImage ? sp.profileImage.startsWith("data:image")
                                        ? sp.profileImage : `data:image/png;base64,${sp.profileImage}`
                                    : "/default-avatar.png"} alt={sp.name}/>
                                <h3>{sp.name}</h3>
                                <p><strong>Service:</strong> {sp.serviceType?.typeName}</p>
                                <p><strong>Rate:</strong> R{sp.rate}</p>
                                <p>{sp.description || "No description available."}</p>
                                <p><strong>Location:</strong> {sp.area?.name}</p>
                            </div>
                        ))}
                        {serviceProviderId && (
                            <button className="get-started-btn" onClick={() => setStep(3)}>
                                Next
                            </button>
                        )}
                    </div>
                )}

                {step === 3 && selectedProvider && (
                    <div className="booking-form-container">
                        <div className="provider-card selected">
                            <><img src={selectedProvider.profileImage ? selectedProvider.profileImage.startsWith("data:image")
                            ? selectedProvider.profileImage : `data:image/png;base64,${selectedProvider.profileImage}`
                            : "/default-avatar.png"} alt={selectedProvider.name} />
                                <h3>{selectedProvider.name}</h3>
                                <p><strong>Service:</strong> {selectedProvider.serviceType?.typeName}</p>
                                <p><strong>Rate:</strong> R{selectedProvider.rate}</p>
                                <p>{selectedProvider.description || "No description available."}</p>
                                <p><strong>Location:</strong> {selectedProvider.area?.name}</p>
                            </>
                        </div>

                        <form onSubmit={handleSubmit} className="booking-form">
                            <label>Service Date:</label>
                            <input
                                type="date"
                                value={serviceDate}
                                onChange={(e) => setServiceDate(e.target.value)}
                                required
                            />

                            <label>Notes:</label>
                            <textarea
                                value={notes}
                                onChange={(e) => setNotes(e.target.value)}
                            />

                            <button type="submit" className="get-started-btn">Create Booking</button>
                        </form>
                    </div>
                )}
            </div>
        </div>
    );
};

export default CreateBooking;
