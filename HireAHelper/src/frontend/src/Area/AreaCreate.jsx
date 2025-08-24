import React, { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Input } from "@/components/ui/input";


const generateUserId = () => `USR-${Math.floor(100000 + Math.random() * 900000)}`;

export default function AreaCreate({ onAdd }) {
    const [areaName, setAreaName] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!areaName.trim()) return;

        const newArea = {
            id: Date.now(),
            name: areaName,
            userId: generateUserId(),
        };

        onAdd(newArea);
        setAreaName("");
    };

    return (
        <Card className="p-4 mb-6">
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
    );
}
