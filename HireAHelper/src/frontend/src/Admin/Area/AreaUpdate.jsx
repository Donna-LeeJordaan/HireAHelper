import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";

export default function AreaUpdate() {
    const { areaId } = useParams();
    const [name, setName] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        if (areaId) {
            axios
                .get(`http://localhost:8080/HireAHelper/area/read/${areaId}`)
                .then((res) => setName(res.data.name))
                .catch((err) => console.error("Error fetching area:", err));
        }
    }, [areaId]);

    const handleSubmit = (e) => {
        e.preventDefault();
        axios
            .put("http://localhost:8080/HireAHelper/area/update", { areaId, name })
            .then(() => navigate("/area"))
            .catch((err) => console.error("Error updating area:", err));
    };

    return (
        <div style={{ padding: "2rem" }}>
            <h1>Update Area</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Area Name:</label>
                    <input
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Update</button>
                <button type="button" onClick={() => navigate("/area")}>
                    Cancel
                </button>
            </form>
        </div>
    );
}
