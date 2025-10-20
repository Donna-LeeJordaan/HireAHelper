import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../../css/Area.css";
import Nav from "../../components/Nav.jsx";

export default function ServiceTypeDashboard() {
    const [user, setUser] = useState(null);
    const [serviceType, setServiceType] = useState([]);
    const navigate = useNavigate();

    //  Fetch current admin using cookie authentication
    useEffect(() => {
        axios
            .get("http://localhost:8080/HireAHelper/current-admin", { withCredentials: true })
            .then((res) => setUser(res.data))
            .catch((err) => console.error("Error fetching current admin:", err));
    }, []);

    // Fetch all service types with credentials
    useEffect(() => {
        axios
            .get("http://localhost:8080/HireAHelper/serviceType/all", { withCredentials: true })
            .then((res) => setServiceType(res.data))
            .catch((err) => console.error("Error fetching service types:", err));
    }, []);

    const handleDelete = async (typeId) => {
        const serviceDelete = window.confirm("Are you sure you want to delete this service type?");
        if (!serviceDelete) return;

        try {
            await axios.delete(`http://localhost:8080/HireAHelper/serviceType/delete/${typeId}`, { withCredentials: true });
            alert("ServiceType deleted");

            // Remove deleted type from state without refresh
            setServiceType(prevServiceType => prevServiceType.filter(serviceType => serviceType.typeId !== typeId));
        } catch (err) {
            console.error("Error deleting service type:", err);
            alert("Failed to delete service type.");
        }
    };

    return (
        <>
            <Nav user={user} />

            <div className="app-container">
                <h1>ServiceType Dashboard</h1>
                <button className="get-started-btn" onClick={() => navigate("/serviceType/create")}>
                    Create ServiceType
                </button>
                <table className="area-table">
                    <thead>
                    <tr>
                        <th>Service Type ID</th>
                        <th>Name</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {serviceType.map((serviceType) => (
                        <tr key={serviceType.typeId}>
                            <td>{serviceType.typeId}</td>
                            <td>{serviceType.typeName}</td>
                            <td>
                                <button
                                    className="get-started-btn small-btn"
                                    onClick={() => navigate(`/serviceType/update/${serviceType.typeId}`)}
                                >
                                    Update
                                </button>
                                <button
                                    className="get-started-btn small-btn"
                                    style={{ marginLeft: "0.5rem" }}
                                    onClick={() => handleDelete(serviceType.typeId)}
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

