//Ameeruddin Area 230190839
import React, { useState } from "react";


const generateUserId = () => `USR-${Math.floor(100000 + Math.random() * 900000)}`;

export default function AreaCreate() {
    const [areaName, setAreaName] = useState("");
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!areaName.trim()) return;

        const newArea = {
            id: Date.now(),
            name: areaName,
            userId: generateUserId(),
        };

        console.log("New Area Created:", newArea); // Replace with API call
        setAreaName("");
        navigate("/areas"); // Go back to dashboard
    };

    return (
        <div className="p-6">
            <Card className="p-4">
                <h2 className="text-xl font-semibold mb-2">Create New Area</h2>
                <form onSubmit={handleSubmit} className="flex gap-2">
                    <Input
                        placeholder="Enter area name"
                        value={areaName}
                        onChange={(e) => setAreaName(e.target.value)}
                    />
                    <Button type="submit">Add</Button>
                </form>
            </Card>
        </div>
    );
}
