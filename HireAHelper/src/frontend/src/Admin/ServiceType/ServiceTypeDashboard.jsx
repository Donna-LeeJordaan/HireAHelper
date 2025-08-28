import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../../css/Area.css";


export default function ServiceTypeDashboard() {
    const [serviceType, setServiceType] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get("http://localhost:8080/HireAHelper/serviceType/all")
            .then(res => setServiceType(res.data))
            .catch(err => console.error(err));
    }, []);

    const handleDelete = async (typeId) => {
        const serviceDelete = window.confirm("Are you sure you want to delete this service type?");
        if (!serviceDelete) return;

        try {
            await axios.delete(`http://localhost:8080/HireAHelper/serviceType/delete/${typeId}`);
            alert("ServiceType deleted");

            setServiceType(prevServiceType => prevServiceType.filter(serviceType => serviceType.typeId !== typeId));
        } catch (err) {
            console.error("Error deleting service type:", err);
            alert("Failed to delete service type.");
        }
    };

    return (
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
                {serviceType.map(serviceType => (
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
    );
}