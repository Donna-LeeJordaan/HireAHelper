import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { v4 as uuidv4 } from "uuid"; //// Import a library to generate unique IDs
import "./ClientRegister.css";

function ClientRegister() {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        userId: uuidv4(),
        name: "",
        email: "",
        password: "",
        mobileNumber: "",
        areaId: "",
        area: null
    });

    // State to store list of areas fetched from backend
    const [areas, setAreas] = useState([]);

    // Fetch list of areas when the component first loads
    useEffect(() => {
        fetch("http://localhost:8080/HireAHelper/area/all")
            .then(res => res.json())  // convert response to JSON
            .then(data => setAreas(data))  // store areas in state
            .catch(err => console.error("Failed to fetch areas:", err));
    }, []);

    // Handles input changes (runs when user types in a field or selects an option)
    const handleChange = (e) => {
        const { name, value } = e.target;

        if (name === "areaId") {
            const selectedArea = areas.find(a => a.areaId === value);
            setFormData(prev => ({
                ...prev,
                areaId: value,
                area: selectedArea || null
            }));
        } else {
            setFormData(prev => ({
                ...prev,
                [name]: value
            }));
        }
    };

    // Handles form submission
    const handleSubmit = async (e) => {
        e.preventDefault();  // prevent page refresh


        // Validation to make sure a valid area was selected
        if (!formData.area) {
            alert("Please select a valid area");
            return;
        }

        // Create the client object to send to backend
        const client = {
            userId: formData.userId,
            role: "CLIENT",
            name: formData.name,
            email: formData.email,
            password: formData.password,
            mobileNumber: formData.mobileNumber,
            area: formData.area
        };

        try {
            // Send POST request to backend API
            const res = await fetch("http://localhost:8080/HireAHelper/client/create", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(client)
            });

            if (!res.ok) throw new Error("Client registration failed");

            alert("Client registered successfully!");

            // Reset form
            setFormData({
                userId: uuidv4(),
                name: "",
                email: "",
                password: "",
                mobileNumber: "",
                areaId: "",
                area: null
            });

            // Automatically navigate to login page
            navigate("/client/login");

        } catch (err) {
            console.error(err);
            alert(err.message);
        }
    };

    // JSX for rendering the form
    return (
        <div className="client-register-container">
            <div className="client-register-card">
                <h2>Client Registration</h2>
                <form onSubmit={handleSubmit}>
                    <input
                        name="name"
                        placeholder="Name"
                        value={formData.name}
                        onChange={handleChange}
                        required
                    />
                    <input
                        name="email"
                        type="email"
                        placeholder="Email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                    />
                    <input
                        name="password"
                        type="password"
                        placeholder="Password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />
                    <input
                        name="mobileNumber"
                        placeholder="Mobile Number"
                        value={formData.mobileNumber}
                        onChange={handleChange}
                        required
                    />

                    <select
                        name="areaId"
                        value={formData.areaId}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Select Area</option>
                        {areas.map(area => (
                            <option key={area.areaId} value={area.areaId}>
                                {area.name}
                            </option>
                        ))}
                    </select>

                    <button type="submit" className="client-register-btn">
                        Register
                    </button>
                </form>
            </div>
        </div>
    );
}

export default ClientRegister;
