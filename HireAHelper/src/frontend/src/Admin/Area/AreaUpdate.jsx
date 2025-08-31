import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import "../../css/Area.css";
import Nav from "../../components/Nav.jsx";

export default function AreaUpdate() {
    const user = JSON.parse(localStorage.getItem("user"));
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
        <>
            <Nav user={user} />

        <div className="app-container"> {}

            <h1>Update Area</h1> {}

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
                        Area Name:
                    </label>
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
                        onClick={() => navigate("/area")}
                    >
                        Cancel
                    </button>
                </div>
            </form>
        </div>
            </>
    );
}