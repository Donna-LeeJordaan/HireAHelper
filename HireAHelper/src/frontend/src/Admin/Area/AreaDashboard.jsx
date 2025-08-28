import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function AreaDashboard() {
    const [areas, setAreas] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get("http://localhost:8080/HireAHelper/area/all")
            .then(res => setAreas(res.data))
            .catch(err => console.error(err));
    }, []);

    return (
        <div style={{ padding: "2rem" }}>
            <h1>Area Dashboard</h1>
            <button onClick={() => navigate("/areas/create")}>Create Area</button>
            <table border="1" cellPadding="8" style={{ marginTop: "1rem", width: "100%" }}>
                <thead>
                <tr>
                    <th>Area ID</th>
                    <th>Name</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {areas.map(area => (
                    <tr key={area.areaId}>
                        <td>{area.areaId}</td>
                        <td>{area.name}</td>
                        <td>
                            <button onClick={() => navigate(`/areas/update/${area.areaId}`)}>Update</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}