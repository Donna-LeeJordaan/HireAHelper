import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../../css/Area.css";

export default function AreaDashboard() {
    const [areas, setAreas] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get("http://localhost:8080/HireAHelper/area/all")
            .then(res => setAreas(res.data))
            .catch(err => console.error(err));
    }, []);

    return (
        <div className="app-container">
            <h1>Area Dashboard</h1>
            <button className="get-started-btn" onClick={() => navigate("/areas/create")}>
                Create Area
            </button>
            <table className="area-table">
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
                            <button
                                className="get-started-btn small-btn"
                                onClick={() => navigate(`/areas/update/${area.areaId}`)}
                            >
                                Update
                            </button>
                            <button
                                className="get-started-btn small-btn"
                                style={{ marginLeft: "0.5rem" }}
                                onClick={() => navigate(`/areas/delete/${area.areaId}`)} // ← updated here
                            >
                                Delete
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>

            </table>
        </div>
    );
}
