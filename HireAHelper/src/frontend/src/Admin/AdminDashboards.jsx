import React, { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Tabs, TabsList, TabsTrigger, TabsContent } from "@/components/ui/tabs";

export default function AdminDashboard() {
    const [activeTab, setActiveTab] = useState("serviceTypes");


    const [serviceTypes, setServiceTypes] = useState([]);
    const [areas, setAreas] = useState([]);
    const [clients, setClients] = useState([]);
    const [providers, setProviders] = useState([]);


    const [newServiceType, setNewServiceType] = useState("");
    const [newArea, setNewArea] = useState("");


    useEffect(() => {
        setClients([{ id: 1, name: "Client A" }, { id: 2, name: "Client B" }]);
        setProviders([{ id: 1, name: "Provider X" }, { id: 2, name: "Provider Y" }]);
        setServiceTypes([{ id: 1, name: "Plumbing" }, { id: 2, name: "Electrical" }]);
        setAreas([{ id: 1, name: "Cape Town" }, { id: 2, name: "Durban" }]);
    }, []);


    const addServiceType = () => {
        if (!newServiceType) return;
        setServiceTypes([...serviceTypes, { id: Date.now(), name: newServiceType }]);
        setNewServiceType("");
    };

    const deleteServiceType = (id) => {
        setServiceTypes(serviceTypes.filter((s) => s.id !== id));
    };

    const addArea = () => {
        if (!newArea) return;
        setAreas([...areas, { id: Date.now(), name: newArea }]);
        setNewArea("");
    };

    const deleteArea = (id) => {
        setAreas(areas.filter((a) => a.id !== id));
    };

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">Admin Dashboard</h1>

            <Tabs value={activeTab} onValueChange={setActiveTab}>
                <TabsList>
                    <TabsTrigger value="serviceTypes">Service Types</TabsTrigger>
                    <TabsTrigger value="areas">Areas</TabsTrigger>
                    <TabsTrigger value="clients">Clients</TabsTrigger>
                    <TabsTrigger value="providers">Service Providers</TabsTrigger>
                </TabsList>

                {/* Service Types */}
                <TabsContent value="serviceTypes">
                    <Card className="p-4">
                        <h2 className="text-xl font-semibold mb-2">Manage Service Types</h2>
                        <div className="flex gap-2 mb-4">
                            <Input
                                placeholder="New Service Type"
                                value={newServiceType}
                                onChange={(e) => setNewServiceType(e.target.value)}
                            />
                            <Button onClick={addServiceType}>Add</Button>
                        </div>
                        <ul>
                            {serviceTypes.map((s) => (
                                <li key={s.id} className="flex justify-between mb-2">
                                    {s.name}
                                    <Button variant="destructive" size="sm" onClick={() => deleteServiceType(s.id)}>
                                        Delete
                                    </Button>
                                </li>
                            ))}
                        </ul>
                    </Card>
                </TabsContent>

                {/* Areas */}
                <TabsContent value="areas">
                    <Card className="p-4">
                        <h2 className="text-xl font-semibold mb-2">Manage Areas</h2>
                        <div className="flex gap-2 mb-4">
                            <Input
                                placeholder="New Area"
                                value={newArea}
                                onChange={(e) => setNewArea(e.target.value)}
                            />
                            <Button onClick={addArea}>Add</Button>
                        </div>
                        <ul>
                            {areas.map((a) => (
                                <li key={a.id} className="flex justify-between mb-2">
                                    {a.name}
                                    <Button variant="destructive" size="sm" onClick={() => deleteArea(a.id)}>
                                        Delete
                                    </Button>
                                </li>
                            ))}
                        </ul>
                    </Card>
                </TabsContent>

                {/* Clients */}
                <TabsContent value="clients">
                    <Card className="p-4">
                        <h2 className="text-xl font-semibold mb-2">All Clients</h2>
                        <ul>
                            {clients.map((c) => (
                                <li key={c.id} className="mb-2">{c.name}</li>
                            ))}
                        </ul>
                    </Card>
                </TabsContent>

                {/* Providers */}
                <TabsContent value="providers">
                    <Card className="p-4">
                        <h2 className="text-xl font-semibold mb-2">All Service Providers</h2>
                        <ul>
                            {providers.map((p) => (
                                <li key={p.id} className="mb-2">{p.name}</li>
                            ))}
                        </ul>
                    </Card>
                </TabsContent>
            </Tabs>
        </div>
    );
}
