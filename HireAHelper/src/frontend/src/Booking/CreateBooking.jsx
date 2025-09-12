import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../css/CreateBooking.css";
import Nav from "../components/Nav.jsx";

const CreateBooking = () => {
    const navigate = useNavigate();
    const generateBookingId = () => `BOOKING-${Math.floor(100000 + Math.random() * 900000)}`;
    const [serviceProviders, setServiceProviders] = useState([]);
    const [filteredProviders, setFilteredProviders] = useState([]);
    const [serviceType, setServiceType] = useState("");
    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user?.userId;
    const [clientArea, setClientArea] = useState(null);
    const [searchPerformed, setSearchPerformed] = useState(false);
    const [searchError, setSearchError] = useState("");
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
        if (!serviceType.trim()) {
            alert("Enter a service to search");
            return;
        }
        if (!clientArea) {
            alert("Please login to make a booking");
            return;
        }

        setSearchError("");

        const matchedProviders = serviceProviders.filter(
            (sp) =>
                sp.serviceType?.typeName?.toLowerCase() === serviceType.toLowerCase()
        );

        if (matchedProviders.length === 0) {
            setFilteredProviders([]);
            setSearchError(`Service "${serviceType}" does not exist.`);
            setSearchPerformed(true);
            return;
        }

        const providersInArea = matchedProviders.filter(
            (sp) => sp.area?.areaId === clientArea.areaId
        );

        if (providersInArea.length === 0) {
            setFilteredProviders([]);
            setSearchError(`No Service Providers found for "${serviceType}" in your area.`);
            setSearchPerformed(true);
            return;
        }

        setFilteredProviders(providersInArea);
        setStep(2);
        setSearchPerformed(true);
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
                navigate("/client/dashboard");
            })
            .catch((err) => console.error("Error creating booking:", err));
    };

    const selectedProvider = filteredProviders.find(
        (sp) => sp.userId === serviceProviderId
    );

    return (
        <>
            <Nav user={user} />

            <div className="page-wrapper">
                <div className="app-container">

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

                            {searchPerformed && searchError && (
                                <p className="no-results">{searchError}</p>
                            )}
                        </div>
                    )}

                    {step === 2 && filteredProviders.length > 0 && (
                        <div className="provider-grid">
                            {filteredProviders.map((sp) => (
                                <div
                                    key={sp.userId}
                                    className={`provider-card ${serviceProviderId === sp.userId ? "selected" : ""}`}
                                    onClick={() => {
                                        setServiceProviderId(sp.userId);
                                        setStep(3);
                                    }}
                                >
                                    <div className="provider-card-content">
                                        <img
                                            src={sp.profileImage ? sp.profileImage.startsWith("data:image")
                                                    ? sp.profileImage
                                                    : `data:image/png;base64,${sp.profileImage}`
                                                : "/default-avatar.png"}
                                            alt={sp.name}
                                        />
                                        <div className="provider-info">
                                            <h3>{sp.name}</h3>
                                            <p><strong>Service:</strong> {sp.serviceType?.typeName}</p>
                                            <p><strong>Rate:</strong> R{sp.rate}</p>
                                            <p><strong>Email:</strong> {sp.email}</p>
                                            <p><strong>Phone:</strong> {sp.mobileNumber}</p>
                                            <p>{sp.description || "No description available."}</p>
                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}

                    {step === 3 && selectedProvider && (
                        <div className="booking-form-container">
                            {/* Back button */}
                            <button
                                className="get-started-btn back-btn"
                                onClick={() => {
                                    setServiceProviderId("");
                                    setStep(2);
                                }}
                                style={{ marginBottom: "1rem", alignSelf: "flex-start" }}
                            >
                                ← Back to Providers
                            </button>

                            <div className="provider-card selected">
                                <div className="provider-card-content">
                                    <img
                                        src={selectedProvider.profileImage ? selectedProvider.profileImage.startsWith("data:image")
                                                ? selectedProvider.profileImage
                                                : `data:image/png;base64,${selectedProvider.profileImage}`
                                            : "/default-avatar.png"}
                                        alt={selectedProvider.name}
                                    />
                                    <div className="provider-info">
                                        <h3>{selectedProvider.name}</h3>
                                        <p><strong>Service:</strong> {selectedProvider.serviceType?.typeName}</p>
                                        <p><strong>Rate:</strong> R{selectedProvider.rate}</p>
                                        <p><strong>Email:</strong> {selectedProvider.email}</p>
                                        <p><strong>Phone:</strong> {selectedProvider.mobileNumber}</p>
                                        <p>{selectedProvider.description || "No description available."}</p>
                                    </div>
                                </div>
                            </div>

                            <form onSubmit={handleSubmit} className="booking-form">
                                <label>Service Date:</label>
                                <input
                                    type="date"
                                    value={serviceDate}
                                    onChange={(e) => setServiceDate(e.target.value)}
                                    required
                                    min={new Date().toISOString().split("T")[0]}
                                />

                                <label>Notes:</label>
                                <textarea
                                    value={notes}
                                    onChange={(e) => setNotes(e.target.value)}
                                />

                                <button type="submit" className="get-started-btn">
                                    Create Booking
                                </button>
                            </form>
                        </div>
                    )}
                </div>
            </div>
        </>
    );
};

export default CreateBooking;