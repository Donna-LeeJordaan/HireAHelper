//Ameeruddin Arai 230190839
import React, { useState } from "react";



const generateUserId = () => `USR-${Math.floor(100000 + Math.random() * 900000)}`;

export default function AreaDashboard() {
    const [areas, setAreas] = useState([
        { id: 1, name: "Cape Town", userId: generateUserId() },
        { id: 2, name: "Durban", userId: generateUserId() },
    ]);

    const navigate = useNavigate();

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">Area Dashboard</h1>

            <Button className="mb-4" onClick={() => navigate("/areas/create")}>
                + Add New Area
            </Button>

            <button
                onClick={() => navigate(`/areas/delete/${area.id}`)}
                className="px-3 py-1 bg-red-600 text-white rounded hover:bg-red-700"
            >
                Delete
            </button>


            <Card className="p-4">
                <Table>
                    <TableHeader>
                        <TableRow>
                            <TableHead>ID</TableHead>
                            <TableHead>Name</TableHead>
                            <TableHead>User ID</TableHead>
                            <TableHead>Actions</TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {areas.map((area) => (
                            <TableRow key={area.id}>
                                <TableCell>{area.id}</TableCell>
                                <TableCell>{area.name}</TableCell>
                                <TableCell>{area.userId}</TableCell>
                                <TableCell>
                                    <Button
                                        size="sm"
                                        variant="secondary"
                                        onClick={() => navigate(`/areas/update/${area.id}`, { state: { area } })}
                                    >
                                        Update
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </Card>
        </div>
    );
}
