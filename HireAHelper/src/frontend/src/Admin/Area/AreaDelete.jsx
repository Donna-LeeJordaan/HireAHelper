import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../css/Area.css";

function AreaDeletePage() {
    const [areas, setAreas] = useState([]);
    const [selectedAreaId, setSelectedAreaId] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        fetch("http://localhost:8080/HireAHelper/areas")
            .then(res => res.json())
            .then(data => setAreas(data))
            .catch(err => console.error("Error fetching areas:", err));
    }, []);

    const handleDelete = async () => {
        if (!selectedAreaId) {
            alert("Please select an area to delete");
            return;
        }

        if (!window.confirm("Are you sure you want to delete this area?")) return;

        try {
            const res = await fetch(`http://localhost:8080/HireAHelper/areas/${selectedAreaId}`, {
                method: "DELETE"
            });

            if (!res.ok) {
                const errorText = await res.text();
                throw new Error(errorText || "Failed to delete area");
            }

            alert("Area deleted successfully!");
            setAreas(prev => prev.filter(area => area.id !== selectedAreaId));
            setSelectedAreaId("");
        } catch (err) {
            console.error(err);
            alert(err.message);
        }
    };

    return (
        <div className="app-container"> {}
            <h1>Delete Area</h1> {}

            <div
                style={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    backgroundColor: "#ffffffcc",
                    padding: "2rem",
                    borderRadius: "15px",
                    width: "400px",
                    maxWidth: "90%",
                    boxShadow: "0 6px 20px rgba(0,0,0,0.1)",
                    marginBottom: "2rem"
                }}
            >
                <label
                    style={{
                        marginBottom: "0.5rem",
                        color: "#013220",
                        fontWeight: "600"
                    }}
                >
                    Select Area to Delete:
                </label>

                <select
                    value={selectedAreaId}
                    onChange={(e) => setSelectedAreaId(e.target.value)}
                    style={{
                        padding: "0.6rem 1rem",
                        fontSize: "1rem",
                        border: "1px solid #055f5b",
                        borderRadius: "8px",
                        outline: "none",
                        width: "100%",
                        marginBottom: "1.5rem"
                    }}
                >
                    <option value="">-- Select Area --</option>
                    {areas.map(area => (
                        <option key={area.id} value={area.id}>
                            {area.name}
                        </option>
                    ))}
                </select>

                {}
                <div style={{ display: "flex", justifyContent: "space-between", width: "100%" }}>
                    <button
                        className="get-started-btn"
                        style={{ flex: 1, margin: "0 0.5rem" }}
                        onClick={handleDelete}
                    >
                        Delete
                    </button>
                    <button
                        className="get-started-btn"
                        style={{ flex: 1, margin: "0 0.5rem" }}
                        onClick={() => navigate(-1)}
                    >
                        Back
                    </button>
                </div>
            </div>
        </div>
    );
}

export default AreaDeletePage;