import { useState, useEffect } from "react";
import { v4 as uuidv4 } from "uuid";

function ClientRegister() {
    const [formData, setFormData] = useState({
        userId: uuidv4(),
        name: "",
        email: "",
        password: "",
        mobileNumber: "",
        area: null
    });

    const [areas, setAreas] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/HireAHelper/area/all")
            .then(res => res.json())
            .then(data => setAreas(data))
            .catch(err => console.error("Failed to fetch areas:", err));
    }, []);

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

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!formData.area) {
            alert("Please select a valid area");
            return;
        }

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
            const res = await fetch("http://localhost:8080/HireAHelper/client/create", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(client)
            });

            if (!res.ok) throw new Error("Client registration failed");
            alert("Client registered successfully!");

            setFormData({
                userId: uuidv4(),
                name: "",
                email: "",
                password: "",
                mobileNumber: "",
                area: null
            });
        } catch (err) {
            console.error(err);
            alert(err.message);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input name="name" placeholder="Name" value={formData.name} onChange={handleChange} required />
            <input name="email" type="email" placeholder="Email" value={formData.email} onChange={handleChange} required />
            <input name="password" type="password" placeholder="Password" value={formData.password} onChange={handleChange} required />
            <input name="mobileNumber" placeholder="Mobile Number" value={formData.mobileNumber} onChange={handleChange} required />

            <select name="areaId" value={formData.areaId} onChange={handleChange} required>
                <option value="">Select Area</option>
                {areas.map(area => (
                    <option key={area.areaId} value={area.areaId}>{area.name}</option>
                ))}
            </select>

            <button type="submit">Register</button>
        </form>
    );
}

export default ClientRegister;