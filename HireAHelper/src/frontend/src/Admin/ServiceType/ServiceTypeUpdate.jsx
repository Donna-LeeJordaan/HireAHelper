import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import "../../css/Area.css";
import Nav from "../../components/Nav.jsx";

export default function ServiceTypeUpdate() {
    const [user, setUser] = useState(null);
    const { typeId } = useParams();
    const [typeName, setTypeName] = useState("");
    const navigate = useNavigate();

    // ✅ Fetch current admin using cookie-based authentication
    useEffect(() => {
        axios
            .get("http://localhost:8080/HireAHelper/current-admin", { withCredentials: true })
            .then((res) => setUser(res.data))
            .catch((err) => console.error("Error fetching current admin:", err));
    }, []);

    // ✅ Fetch service type details securely
    useEffect(() => {
        if (typeId) {
            axios
                .get(`http://localhost:8080/HireAHelper/serviceType/read/${typeId}`, { withCredentials: true })
                .then((res) => setTypeName(res.data.typeName))
                .catch((err) => console.error("Error fetching service type:", err));
        }
    }, [typeId]);

    const handleSubmit = (e) => {
        e.preventDefault();
        const updatedServiceType = { typeId, typeName };

        axios
            .put("http://localhost:8080/HireAHelper/serviceType/update", updatedServiceType, { withCredentials: true })
            .then((res) => {
                alert("Service type updated successfully!");
                setTypeName(res.data.typeName);
                navigate("/serviceType");
            })
            .catch((err) => console.error("Error updating service type:", err));
    };

    return (
        <>
            <Nav user={user} />

            <div className="app-container">
                <h1>Update Service Type</h1>

                <form
                    onSubmit={handleSubmit}
                    style={{
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                        backgroundColor: "#ffffffcc",
                        padding: "2rem",
                        borderRadius: "15px",
                        width: "400px",
                        maxWidth: "90%",
                        boxShadow: "0 6px 20px rgba(0,0,0,0.1)"
                    }}
                >
                    <div
                        style={{
                            display: "flex",
                            flexDirection: "column",
                            marginBottom: "1.5rem",
                            width: "100%",
                            textAlign: "left"
                        }}
                    >
                        <label
                            style={{
                                marginBottom: "0.5rem",
                                color: "#013220",
                                fontWeight: "600"
                            }}
                        >
                            Service Type Name:
                        </label>
                        <input
                            type="text"
                            value={typeName}
                            onChange={(e) => setTypeName(e.target.value)}
                            placeholder="Enter service type name"
                            required
                            style={{
                                padding: "0.6rem 1rem",
                                fontSize: "1rem",
                                border: "1px solid #055f5b",
                                borderRadius: "8px",
                                outline: "none"
                            }}
                        />
                    </div>

                    <div style={{ display: "flex", justifyContent: "space-between", width: "100%" }}>
                        <button
                            type="submit"
                            className="get-started-btn"
                            style={{ flex: 1, margin: "0 0.5rem" }}
                        >
                            Update
                        </button>
                        <button
                            type="button"
                            className="get-started-btn"
                            style={{ flex: 1, margin: "0 0.5rem" }}
                            onClick={() => navigate("/serviceType")}
                        >
                            Cancel
                        </button>
                    </div>
                </form>
            </div>
        </>
    );
}

