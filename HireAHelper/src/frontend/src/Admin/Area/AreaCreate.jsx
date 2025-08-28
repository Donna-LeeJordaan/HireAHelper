import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../../css/Area.css";
import logo from "../../assets/logo1.png";

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
        <div className="app-container">

            <img src={logo} alt="Logo" className="logo" />

            <h1>Create Area</h1>
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
                <div style={{ display: "flex", flexDirection: "column", marginBottom: "1.5rem", width: "100%", textAlign: "left" }}>
                    <label style={{ marginBottom: "0.5rem", color: "#013220", fontWeight: "600" }}>Area Name:</label>
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        placeholder="Enter area name"
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
                    <button type="submit" className="get-started-btn" style={{ flex: 1, margin: "0 0.5rem" }}>Save</button>
                    <button
                        type="button"
                        className="get-started-btn"
                        style={{ flex: 1, margin: "0 0.5rem" }}
                        onClick={() => navigate("/area")}
                    >
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    );
}
