import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../../css/Area.css";
import Nav from "../../components/Nav.jsx";

export default function AreaDashboard() {
    const [user, setUser] = useState(null);
    const [areas, setAreas] = useState([]);
    const navigate = useNavigate();

    // ✅ Fetch current admin via cookie-based authentication
    useEffect(() => {
        axios.get("http://localhost:8080/HireAHelper/current-admin", { withCredentials: true })
            .then(res => setUser(res.data))
            .catch(err => {
                console.error("Error fetching current admin:", err);
                navigate("/login"); // redirect to login if not authenticated
            });
    }, [navigate]);

    // ✅ Fetch all areas with cookies
    useEffect(() => {
        axios.get("http://localhost:8080/HireAHelper/area/all", { withCredentials: true })
            .then(res => setAreas(res.data))
            .catch(err => console.error("Error fetching areas:", err));
    }, []);

    const handleDelete = async (areaId) => {
        const confirmDelete = window.confirm("Are you sure you want to delete this area?");
        if (!confirmDelete) return;

        try {
            await axios.delete(`http://localhost:8080/HireAHelper/area/delete/${areaId}`, { withCredentials: true });
            alert("Area deleted successfully!");

            // Remove deleted area from state without page reload
            setAreas(prevAreas => prevAreas.filter(area => area.areaId !== areaId));
        } catch (err) {
            console.error("Error deleting area:", err);
            alert("Failed to delete area.");
        }
    };

    return (
        <>
            <Nav user={user} />

            <div className="app-container">
                <h1>Area Dashboard</h1>
                <button
                    className="areaCreate-button"
                    onClick={() => navigate("/area/create")}
                >
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
                                    onClick={() => navigate(`/area/update/${area.areaId}`)}
                                >
                                    Update
                                </button>
                                <button
                                    className="get-started-btn small-btn"
                                    style={{ marginLeft: "0.5rem" }}
                                    onClick={() => handleDelete(area.areaId)}
                                >
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </>
    );
}
