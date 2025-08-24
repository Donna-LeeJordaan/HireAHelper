import React, { useState } from "react";


// Utility function to generate a random userId
const generateUserId = () => `USR-${Math.floor(100000 + Math.random() * 900000)}`;

export default function AreaDashboard() {
    const [areas, setAreas] = useState([
        { id: 1, name: "Cape Town", userId: generateUserId() },
        { id: 2, name: "Durban", userId: generateUserId() },
    ]);

    const [newArea, setNewArea] = useState("");
    const [editingAreaId, setEditingAreaId] = useState(null);
    const [editValue, setEditValue] = useState("");

    // Add area with generated userId
    const addArea = () => {
        if (!newArea.trim()) return;
        setAreas([...areas, { id: Date.now(), name: newArea, userId: generateUserId() }]);
        setNewArea("");
    };

    // Start editing
    const startEditing = (id, currentName) => {
        setEditingAreaId(id);
        setEditValue(currentName);
    };

    // Save updated area
    const updateArea = (id) => {
        setAreas(
            areas.map((area) =>
                area.id === id ? { ...area, name: editValue } : area
            )
        );
        setEditingAreaId(null);
        setEditValue("");
    };

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">Area Dashboard</h1>

            <Card className="p-4 mb-6">
                <h2 className="text-xl font-semibold mb-2">Add New Area</h2>
                <div className="flex gap-2">
                    <Input
                        placeholder="Enter area name"
                        value={newArea}
                        onChange={(e) => setNewArea(e.target.value)}
                    />
                    <Button onClick={addArea}>Add</Button>
                </div>
            </Card>

            <Card className="p-4">
                <h2 className="text-xl font-semibold mb-2">Manage Areas</h2>
                <ul>
                    {areas.map((area) => (
                        <li key={area.id} className="flex justify-between items-center mb-3">
                            {editingAreaId === area.id ? (
                                <>
                                    <Input
                                        value={editValue}
                                        onChange={(e) => setEditValue(e.target.value)}
                                        className="mr-2"
                                    />
                                    <Button size="sm" onClick={() => updateArea(area.id)}>Save</Button>
                                </>
                            ) : (
                                <>
                                    <span>{area.name} <span className="text-gray-500 text-sm">({area.userId})</span></span>
                                    <Button
                                        size="sm"
                                        variant="secondary"
                                        onClick={() => startEditing(area.id, area.name)}
                                    >
                                        Update
                                    </Button>
                                </>
                            )}
                        </li>
                    ))}
                </ul>
            </Card>
        </div>
    );
}
