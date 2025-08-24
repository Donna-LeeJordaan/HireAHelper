import React, { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Input } from "@/components/ui/input";

export default function AreaUpdate({ area, onUpdate, onCancel }) {
    const [updatedName, setUpdatedName] = useState(area.name);

    const handleUpdate = (e) => {
        e.preventDefault();
        if (!updatedName.trim()) return;

        const updatedArea = { ...area, name: updatedName };
        onUpdate(updatedArea);
    };

    return (
        <Card className="p-4 mb-4">
            <h2 className="text-lg font-semibold mb-2">Update Area</h2>
            <form onSubmit={handleUpdate} className="flex gap-2">
                <Input
                    value={updatedName}
                    onChange={(e) => setUpdatedName(e.target.value)}
                />
                <Button type="submit">Save</Button>
                <Button type="button" variant="secondary" onClick={onCancel}>
                    Cancel
                </Button>
            </form>
        </Card>
    );
}
