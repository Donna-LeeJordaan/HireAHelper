import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function AreaCreate() {
    const [name, setName] = useState("");
    const navigate = useNavigate();

    const generateId = () => `AREA-${Math.floor(100000 + Math.random() * 900000)}`;

    const handleSubmit = (e) => {
        e.preventDefault();
        const newArea = { areaId: generateId(), name };
        axios.post("http://localhost:8080/HireAHelper/area/create", newArea)
            .then(() => navigate("/area"))
            .catch(err => console.error(err));
    };

    return (
        <div style={{ padding: "2rem" }}>
            <h1>Create Area</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Area Name:</label>
                    <input value={name} onChange={(e) => setName(e.target.value)} required />
                </div>
                <button type="submit">Save</button>
                <button type="button" onClick={() => navigate("/area")}>Cancel</button>
            </form>
        </div>
    );
}