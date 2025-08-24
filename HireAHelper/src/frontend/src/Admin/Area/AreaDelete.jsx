//Ameeruddin Arai 230190839
import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

export default function AreaDelete() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [area, setArea] = useState(null);

    // Fetch the area details (for confirmation)
    useEffect(() => {
        const savedAreas = JSON.parse(localStorage.getItem("areas")) || [];
        const foundArea = savedAreas.find((a) => a.id === parseInt(id));
        setArea(foundArea);
    }, [id]);

    // Delete handler
    const handleDelete = () => {
        const savedAreas = JSON.parse(localStorage.getItem("areas")) || [];
        const updatedAreas = savedAreas.filter((a) => a.id !== parseInt(id));
        localStorage.setItem("areas", JSON.stringify(updatedAreas));
        navigate("/areas"); // Redirect back to dashboard
    };

    if (!area) {
        return <p className="p-4">Area not found.</p>;
    }

    return (
        <div className="max-w-md mx-auto p-6 bg-white shadow rounded-lg mt-6">
            <h2 className="text-xl font-semibold mb-4">Delete Area</h2>
            <p className="mb-4">
                Are you sure you want to delete <strong>{area.name}</strong>?
            </p>

            <div className="flex gap-4">
                <button
                    onClick={handleDelete}
                    className="px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700"
                >
                    Yes, Delete
                </button>
                <button
                    onClick={() => navigate("/areas")}
                    className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
                >
                    Cancel
                </button>
            </div>
        </div>
    );
}
