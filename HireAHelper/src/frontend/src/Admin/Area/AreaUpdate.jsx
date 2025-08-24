//Ameeruddin Arai 230190839
import React, { useState } from "react";

import { useLocation, useNavigate, useParams } from "react-router-dom";

export default function AreaUpdate() {
    const { id } = useParams();
    const { state } = useLocation();
    const navigate = useNavigate();

    const [updatedName, setUpdatedName] = useState(state?.area?.name || "");

    const handleUpdate = (e) => {
        e.preventDefault();
        if (!updatedName.trim()) return;

        const updatedArea = { ...state.area, name: updatedName };

        console.log("Updated Area:", updatedArea); // Replace with API call
        navigate("/areas");
    };

    return (
        <div className="p-6">
            <Card className="p-4">
                <h2 className="text-xl font-semibold mb-2">Update Area</h2>
                <form onSubmit={handleUpdate} className="flex gap-2">
                    <Input
                        value={updatedName}
                        onChange={(e) => setUpdatedName(e.target.value)}
                    />
                    <Button type="submit">Save</Button>
                    <Button type="button" variant="secondary" onClick={() => navigate("/areas")}>
                        Cancel
                    </Button>
                </form>
            </Card>
        </div>
    );
}
