import React, { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Badge } from "@/components/ui/badge";
import { Plus, Search, Edit, Trash2, Users, AlertCircle, CheckCircle } from "lucide-react";
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
} from "@/components/ui/dialog";
import { Label } from "@/components/ui/label";

const ServiceTypeDashboard = () => {
    const [serviceTypes, setServiceTypes] = useState([]);
    const [filteredServiceTypes, setFilteredServiceTypes] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [isAddDialogOpen, setIsAddDialogOpen] = useState(false);
    const [isEditDialogOpen, setIsEditDialogOpen] = useState(false);
    const [isDeleteDialogOpen, setIsDeleteDialogOpen] = useState(false);
    const [selectedServiceType, setSelectedServiceType] = useState(null);
    const [newServiceType, setNewServiceType] = useState({
        typeId: "",
        typeName: "",
        description: ""
    });
    const [editServiceType, setEditServiceType] = useState({
        typeId: "",
        typeName: "",
        description: ""
    });

    // Mock data for demonstration
    useEffect(() => {
        const mockServiceTypes = [
            {
                typeId: "1",
                typeName: "Plumbing",
                description: "Fixing pipes, leaks, and drainage issues",
                serviceProviders: [
                    { userId: "101", name: "John's Plumbing", rate: 45.50 },
                    { userId: "102", name: "Quick Fix Plumbers", rate: 50.00 }
                ]
            },
            {
                typeId: "2",
                typeName: "Electrical",
                description: "Wiring, outlets, and electrical repairs",
                serviceProviders: [
                    { userId: "103", name: "Sparky Electric", rate: 60.00 },
                    { userId: "104", name: "Safe Wire Electrical", rate: 55.75 }
                ]
            },
            {
                typeId: "3",
                typeName: "Cleaning",
                description: "Residential and commercial cleaning services",
                serviceProviders: [
                    { userId: "105", name: "Clean Sweep", rate: 35.00 },
                    { userId: "106", name: "Spotless Solutions", rate: 40.00 },
                    { userId: "107", name: "Fresh & Clean", rate: 38.50 }
                ]
            },
            {
                typeId: "4",
                typeName: "Carpentry",
                description: "Woodworking, furniture repair, and installations",
                serviceProviders: [
                    { userId: "108", name: "Woodcraft Masters", rate: 52.25 }
                ]
            }
        ];
        setServiceTypes(mockServiceTypes);
        setFilteredServiceTypes(mockServiceTypes);
    }, []);

    const filterServiceTypes = (term) => {
        if (!term) {
            setFilteredServiceTypes(serviceTypes);
            return;
        }

        const filtered = serviceTypes.filter(
            type =>
                type.typeName.toLowerCase().includes(term.toLowerCase()) ||
                type.description.toLowerCase().includes(term.toLowerCase())
        );
        setFilteredServiceTypes(filtered);
    };

    const handleSearchChange = (e) => {
        const term = e.target.value;
        setSearchTerm(term);
        filterServiceTypes(term);
    };

    const handleAddServiceType = () => {
        //api call
        const newType = {
            typeId: String(serviceTypes.length + 1),
            typeName: newServiceType.typeName,
            description: newServiceType.description,
            serviceProviders: []
        };

        setServiceTypes([...serviceTypes, newType]);
        setFilteredServiceTypes([...serviceTypes, newType]);
        setNewServiceType({ typeId: "", typeName: "", description: "" });
        setIsAddDialogOpen(false);
    };

    const handleEditServiceType = () => {
        //  API call
        const updatedTypes = serviceTypes.map(type =>
            type.typeId === editServiceType.typeId
                ? { ...type, typeName: editServiceType.typeName, description: editServiceType.description }
                : type
        );

        setServiceTypes(updatedTypes);
        setFilteredServiceTypes(updatedTypes);
        setIsEditDialogOpen(false);
    };

    const handleDeleteServiceType = () => {
        //API call
        const updatedTypes = serviceTypes.filter(type => type.typeId !== selectedServiceType.typeId);

        setServiceTypes(updatedTypes);
        setFilteredServiceTypes(updatedTypes);
        setIsDeleteDialogOpen(false);
    };

    const openEditDialog = (serviceType) => {
        setEditServiceType({
            typeId: serviceType.typeId,
            typeName: serviceType.typeName,
            description: serviceType.description
        });
        setIsEditDialogOpen(true);
    };

    const openDeleteDialog = (serviceType) => {
        setSelectedServiceType(serviceType);
        setIsDeleteDialogOpen(true);
    };

    return (
        <div className="p-6 space-y-6">
            <div className="flex justify-between items-center">
                <div>
                    <h1 className="text-3xl font-bold">Service Type Dashboard</h1>
                    <p className="text-muted-foreground">Manage all service types and their providers</p>
                </div>
                <Badge variant="outline" className="px-3 py-1">
                    <Users className="w-4 h-4 mr-2" />
                    {serviceTypes.reduce((total, type) => total + type.serviceProviders.length, 0)} Providers
                </Badge>
            </div>

            <div className="flex flex-col sm:flex-row gap-4">
                <div className="relative flex-1">
                    <Search className="absolute left-2 top-2.5 h-4 w-4 text-muted-foreground" />
                    <Input
                        placeholder="Search service types..."
                        value={searchTerm}
                        onChange={handleSearchChange}
                        className="pl-8"
                    />
                </div>
                <Button onClick={() => setIsAddDialogOpen(true)}>
                    <Plus className="w-4 h-4 mr-2" />
                    Add Service Type
                </Button>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {filteredServiceTypes.map((serviceType) => (
                    <Card key={serviceType.typeId} className="overflow-hidden">
                        <CardHeader className="pb-3">
                            <div className="flex justify-between items-start">
                                <CardTitle className="text-xl">{serviceType.typeName}</CardTitle>
                                <Badge variant="secondary" className="flex items-center">
                                    <Users className="w-3 h-3 mr-1" />
                                    {serviceType.serviceProviders.length}
                                </Badge>
                            </div>
                            <CardDescription>{serviceType.description}</CardDescription>
                        </CardHeader>
                        <CardContent>
                            <div className="space-y-4">
                                <div>
                                    <h4 className="text-sm font-medium mb-2">Service Providers</h4>
                                    {serviceType.serviceProviders.length > 0 ? (
                                        <div className="space-y-2">
                                            {serviceType.serviceProviders.slice(0, 3).map((provider) => (
                                                <div key={provider.userId} className="flex justify-between items-center text-sm">
                                                    <span className="truncate">{provider.name}</span>
                                                    <Badge variant="outline">${provider.rate}/hr</Badge>
                                                </div>
                                            ))}
                                            {serviceType.serviceProviders.length > 3 && (
                                                <div className="text-xs text-muted-foreground">
                                                    +{serviceType.serviceProviders.length - 3} more providers
                                                </div>
                                            )}
                                        </div>
                                    ) : (
                                        <div className="text-sm text-muted-foreground flex items-center">
                                            <AlertCircle className="w-4 h-4 mr-1" />
                                            No providers yet
                                        </div>
                                    )}
                                </div>

                                <div className="flex gap-2">
                                    <Button
                                        variant="outline"
                                        size="sm"
                                        className="flex-1"
                                        onClick={() => openEditDialog(serviceType)}
                                    >
                                        <Edit className="w-4 h-4 mr-1" />
                                        Edit
                                    </Button>
                                    <Button
                                        variant="outline"
                                        size="sm"
                                        className="text-destructive hover:text-destructive"
                                        onClick={() => openDeleteDialog(serviceType)}
                                    >
                                        <Trash2 className="w-4 h-4 mr-1" />
                                        Delete
                                    </Button>
                                </div>
                            </div>
                        </CardContent>
                    </Card>
                ))}
            </div>

            {filteredServiceTypes.length === 0 && (
                <Card className="text-center py-12">
                    <CardContent>
                        <Search className="w-12 h-12 text-muted-foreground mx-auto mb-4" />
                        <h3 className="text-lg font-medium mb-2">No service types found</h3>
                        <p className="text-muted-foreground">
                            {searchTerm ? "Try adjusting your search query" : "Get started by adding your first service type"}
                        </p>
                    </CardContent>
                </Card>
            )}

            {/* Add Service Type Dialog */}
            <Dialog open={isAddDialogOpen} onOpenChange={setIsAddDialogOpen}>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>Add New Service Type</DialogTitle>
                        <DialogDescription>
                            Create a new service type that providers can offer to clients.
                        </DialogDescription>
                    </DialogHeader>
                    <div className="grid gap-4 py-4">
                        <div className="grid gap-2">
                            <Label htmlFor="typeName">Service Type Name</Label>
                            <Input
                                id="typeName"
                                placeholder="e.g., Plumbing, Electrical, Cleaning"
                                value={newServiceType.typeName}
                                onChange={(e) => setNewServiceType({...newServiceType, typeName: e.target.value})}
                            />
                        </div>
                        <div className="grid gap-2">
                            <Label htmlFor="description">Description</Label>
                            <Input
                                id="description"
                                placeholder="Describe what this service includes"
                                value={newServiceType.description}
                                onChange={(e) => setNewServiceType({...newServiceType, description: e.target.value})}
                            />
                        </div>
                    </div>
                    <DialogFooter>
                        <Button variant="outline" onClick={() => setIsAddDialogOpen(false)}>
                            Cancel
                        </Button>
                        <Button onClick={handleAddServiceType} disabled={!newServiceType.typeName}>
                            Add Service Type
                        </Button>
                    </DialogFooter>
                </DialogContent>
            </Dialog>

            {/* Edit Service Type Dialog */}
            <Dialog open={isEditDialogOpen} onOpenChange={setIsEditDialogOpen}>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>Edit Service Type</DialogTitle>
                        <DialogDescription>
                            Update the details of this service type.
                        </DialogDescription>
                    </DialogHeader>
                    <div className="grid gap-4 py-4">
                        <div className="grid gap-2">
                            <Label htmlFor="editTypeName">Service Type Name</Label>
                            <Input
                                id="editTypeName"
                                value={editServiceType.typeName}
                                onChange={(e) => setEditServiceType({...editServiceType, typeName: e.target.value})}
                            />
                        </div>
                        <div className="grid gap-2">
                            <Label htmlFor="editDescription">Description</Label>
                            <Input
                                id="editDescription"
                                value={editServiceType.description}
                                onChange={(e) => setEditServiceType({...editServiceType, description: e.target.value})}
                            />
                        </div>
                    </div>
                    <DialogFooter>
                        <Button variant="outline" onClick={() => setIsEditDialogOpen(false)}>
                            Cancel
                        </Button>
                        <Button onClick={handleEditServiceType} disabled={!editServiceType.typeName}>
                            Save Changes
                        </Button>
                    </DialogFooter>
                </DialogContent>
            </Dialog>

            {/* Delete Confirmation Dialog */}
            <Dialog open={isDeleteDialogOpen} onOpenChange={setIsDeleteDialogOpen}>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>Are you sure?</DialogTitle>
                        <DialogDescription>
                            This action cannot be undone. This will permanently delete the{" "}
                            <span className="font-medium">{selectedServiceType?.typeName}</span> service type
                            and remove it from our systems.
                        </DialogDescription>
                    </DialogHeader>
                    <div className="bg-destructive/10 p-4 rounded-lg flex items-start gap-3">
                        <AlertCircle className="w-5 h-5 text-destructive mt-0.5" />
                        <div className="text-sm">
                            <strong>Warning:</strong> Deleting this service type will affect{" "}
                            {selectedServiceType?.serviceProviders.length} providers who offer this service.
                        </div>
                    </div>
                    <DialogFooter>
                        <Button variant="outline" onClick={() => setIsDeleteDialogOpen(false)}>
                            Cancel
                        </Button>
                        <Button variant="destructive" onClick={handleDeleteServiceType}>
                            Delete Service Type
                        </Button>
                    </DialogFooter>
                </DialogContent>
            </Dialog>
        </div>
    );
};

export default ServiceTypeDashboard;