import { useState, useEffect } from "react";

function ClientDashboard() {
    const [clientData, setClientData] = useState(null);
    const [editing, setEditing] = useState(false);
    const [formData, setFormData] = useState({});
    const [areas, setAreas] = useState([]);
    const [serviceTypes, setServiceTypes] = useState([]);
    const [selectedService, setSelectedService] = useState("");
    const [providers, setProviders] = useState([]);
    const [loadingProviders, setLoadingProviders] = useState(false);

    // Fetch client data from localStorage after login
    useEffect(() => {
        const storedClient = JSON.parse(localStorage.getItem("client"));
        if (storedClient) {
            setClientData(storedClient);
            setFormData({
                ...storedClient,
                area: storedClient.area
            });
        }
    }, []);

    // Fetch all areas from DB
    useEffect(() => {
        fetch("http://localhost:8080/HireAHelper/area/all")
            .then(res => res.json())
            .then(data => setAreas(data))
            .catch(err => console.error(err));
    }, []);

    // Fetch all service types from DB
    useEffect(() => {
        fetch("http://localhost:8080/HireAHelper/serviceType/all")
            .then(res => res.json())
            .then(data => setServiceTypes(data))
            .catch(err => console.error(err));
    }, []);

    // Fetch providers based on selected service type and client area
    useEffect(() => {
        if (selectedService && clientData) {
            setLoadingProviders(true);
            fetch(`http://localhost:8080/HireAHelper/serviceProvider/byServiceAndArea?service=${selectedService}&area=${clientData.area.areaId}`)
                .then(res => res.json())
                .then(data => {
                    setProviders(data);
                    setLoadingProviders(false);
                })
                .catch(err => {
                    console.error(err);
                    setLoadingProviders(false);
                });
        }
    }, [selectedService, clientData]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name === "area") {
            const areaObj = areas.find(a => a.areaId === value);
            setFormData(prev => ({ ...prev, area: areaObj }));
        } else {
            setFormData(prev => ({ ...prev, [name]: value }));
        }
    };

    const handleUpdate = async (e) => {
        e.preventDefault();
        try {
            const res = await fetch("http://localhost:8080/HireAHelper/client/update", {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(formData)
            });
            if (!res.ok) throw new Error("Failed to update details");
            const updatedClient = await res.json();
            setClientData(updatedClient);
            localStorage.setItem("client", JSON.stringify(updatedClient));
            setEditing(false);
            alert("Details updated successfully!");
        } catch (err) {
            console.error(err);
            alert(err.message);
        }
    };

    if (!clientData) return <p>Loading client data...</p>;

    return (
        <div className="dashboard-container">
            <header className="dashboard-header">
                <img src="/logo192.png" alt="Logo" className="dashboard-logo" />
                <h1>Welcome, {clientData.name}!</h1>
            </header>

            <section className="client-area">
                <h2>Your Area: {clientData.area.name}</h2>
                <button onClick={() => setEditing(!editing)} className="edit-btn">
                    {editing ? "Cancel" : "Edit Details"}
                </button>

                {editing && (
                    <form className="edit-form" onSubmit={handleUpdate}>
                        <input
                            type="text"
                            name="name"
                            placeholder="Name"
                            value={formData.name}
                            onChange={handleChange}
                            required
                        />
                        <input
                            type="email"
                            name="email"
                            placeholder="Email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                        />
                        <input
                            type="password"
                            name="password"
                            placeholder="Password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                        />
                        <input
                            type="text"
                            name="mobileNumber"
                            placeholder="Mobile Number"
                            value={formData.mobileNumber}
                            onChange={handleChange}
                            required
                        />
                        <select name="area" value={formData.area.areaId} onChange={handleChange} required>
                            {areas.map(area => (
                                <option key={area.areaId} value={area.areaId}>
                                    {area.name}
                                </option>
                            ))}
                        </select>
                        <button type="submit" className="save-btn">Save Changes</button>
                    </form>
                )}
            </section>

            <section className="service-search">
                <h2>Find a Service Provider</h2>
                <select
                    value={selectedService}
                    onChange={(e) => setSelectedService(e.target.value)}
                >
                    <option value="">Select a service</option>
                    {serviceTypes.map(service => (
                        <option key={service.id} value={service.name}>
                            {service.name}
                        </option>
                    ))}
                </select>

                {loadingProviders ? (
                    <p>Loading providers...</p>
                ) : (
                    <ul className="provider-list">
                        {providers.map(p => (
                            <li key={p.userId}>
                                <p>{p.name} ({p.email})</p>
                                <p>Mobile: {p.mobileNumber}</p>
                                <button className="book-btn">Book</button>
                                <button className="message-btn">Message</button>
                            </li>
                        ))}
                    </ul>
                )}
            </section>
        </div>
    );
}

export default ClientDashboard;

