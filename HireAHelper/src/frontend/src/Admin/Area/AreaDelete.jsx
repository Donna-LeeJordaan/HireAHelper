import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function AreaDeletePage() {
    const [areas, setAreas] = useState([]);
    const [selectedAreaId, setSelectedAreaId] = useState("");
    const navigate = useNavigate();

    // Fetch all areas
    useEffect(() => {
        fetch("http://localhost:8080/HireAHelper/areas")
            .then(res => res.json())
            .then(data => setAreas(data))
            .catch(err => console.error("Error fetching areas:", err));
    }, []);

    // Handle delete
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
        <div className="area-delete-container">
            <h2>Delete Area</h2>
            <select
                value={selectedAreaId}
                onChange={(e) => setSelectedAreaId(e.target.value)}
            >
                <option value="">-- Select Area --</option>
                {areas.map(area => (
                    <option key={area.id} value={area.id}>
                        {area.name}
                    </option>
                ))}
            </select>
            <button className="delete-btn" onClick={handleDelete}>
                Delete Area
            </button>
            <button className="back-btn" onClick={() => navigate(-1)}>
                Back
            </button>
        </div>
    );
}

export default AreaDeletePage;
