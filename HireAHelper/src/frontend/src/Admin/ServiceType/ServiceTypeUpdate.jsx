//Service type update
import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import "../../css/Area.css";
import Nav from "../../components/Nav.jsx";

export default function ServiceTypeUpdate() {
    const user = JSON.parse(localStorage.getItem("user"));
    const { typeId } = useParams();
    const [typeName, setTypeName] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        if (typeId) {
            axios
                .get(`http://localhost:8080/HireAHelper/serviceType/read/${typeId}`)
                .then((res) => setTypeName(res.data.name))
                .catch((err) => console.error("Error fetching area:", err));
        }
    }, [typeId]);

    const handleSubmit = (e) => {
        e.preventDefault();
        axios
            .put("http://localhost:8080/HireAHelper/serviceType/update", { typeId, typeName })
            .then(() => navigate("/serviceType"))
            .catch((err) => console.error("Error updating service type:", err));
    };

    return (
        <>
            <Nav user={user} />

            <div className="app-container"> {}

                <h1>Update ServiceType</h1> {}

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
                            ServiceType Name:
                        </label>
                        <input
                            type="text"
                            value={name}
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

                    {/* Buttons container */}
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