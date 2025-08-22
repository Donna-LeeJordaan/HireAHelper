import React, { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Tabs, TabsList, TabsTrigger, TabsContent } from "@/components/ui/tabs";
import { Badge } from "@/components/ui/badge";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Search, Filter, MessageSquare, User, Calendar } from "lucide-react";

const MessageDashboard = () => {
    const [messages, setMessages] = useState([]);
    const [filteredMessages, setFilteredMessages] = useState([]);
    const [selectedMessage, setSelectedMessage] = useState(null);
    const [searchTerm, setSearchTerm] = useState("");
    const [activeFilter, setActiveFilter] = useState("all");

    // Mock data for demonstration
    useEffect(() => {
        const mockMessages = [
            {
                messageId: "1",
                timeStamp: new Date("2025-08-05T10:30:00"),
                content: "Hello, when will you be available for the plumbing job?",
                client: { userId: "1", name: "Client A", email: "clientA@example.com" },
                serviceProvider: { userId: "101", name: "Provider X", serviceType: "Plumbing" }
            },
            {
                messageId: "2",
                timeStamp: new Date("2025-08-05T11:15:00"),
                content: "I can come tomorrow at 2 PM. Does that work for you?",
                client: { userId: "1", name: "Client A", email: "clientA@example.com" },
                serviceProvider: { userId: "101", name: "Provider X", serviceType: "Plumbing" }
            },
            {
                messageId: "3",
                timeStamp: new Date("2025-08-04T09:45:00"),
                content: "The electrical issue seems more complex than expected.",
                client: { userId: "2", name: "Client B", email: "clientB@example.com" },
                serviceProvider: { userId: "102", name: "Provider Y", serviceType: "Electrical" }
            },
            {
                messageId: "4",
                timeStamp: new Date("2025-08-03T14:20:00"),
                content: "Thank you for your excellent service!",
                client: { userId: "3", name: "Client C", email: "clientC@example.com" },
                serviceProvider: { userId: "103", name: "Provider Z", serviceType: "Cleaning" }
            },
            {
                messageId: "5",
                timeStamp: new Date("2025-08-02T16:45:00"),
                content: "Can you please send me a quotation for the repair work?",
                client: { userId: "4", name: "Client D", email: "clientD@example.com" },
                serviceProvider: { userId: "104", name: "Provider W", serviceType: "Carpentry" }
            }
        ];
        setMessages(mockMessages);
        setFilteredMessages(mockMessages);
    }, []);

    const filterMessages = (term, filter) => {
        let filtered = messages;

        // Apply search filter
        if (term) {
            filtered = filtered.filter(
                message =>
                    message.content.toLowerCase().includes(term.toLowerCase()) ||
                    message.client.name.toLowerCase().includes(term.toLowerCase()) ||
                    message.serviceProvider.name.toLowerCase().includes(term.toLowerCase()) ||
                    message.serviceProvider.serviceType.toLowerCase().includes(term.toLowerCase())
            );
        }

        // Apply category filter
        if (filter !== "all") {
            filtered = filtered.filter(
                message => message.serviceProvider.serviceType.toLowerCase() === filter.toLowerCase()
            );
        }

        setFilteredMessages(filtered);
    };

    const handleSearchChange = (e) => {
        const term = e.target.value;
        setSearchTerm(term);
        filterMessages(term, activeFilter);
    };

    const handleFilterChange = (filter) => {
        setActiveFilter(filter);
        filterMessages(searchTerm, filter);
    };

    const formatDate = (date) => {
        return new Date(date).toLocaleString();
    };

    const getInitials = (name) => {
        return name.split(' ').map(word => word[0]).join('').toUpperCase();
    };

    return (
        <div className="p-6 space-y-6">
            <div className="flex justify-between items-center">
                <h1 className="text-3xl font-bold">Message Dashboard</h1>
                <Badge variant="outline" className="px-3 py-1">
                    <MessageSquare className="w-4 h-4 mr-2" />
                    {filteredMessages.length} Messages
                </Badge>
            </div>

            <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
                {/* Filters and Search */}
                <Card className="lg:col-span-1">
                    <CardHeader>
                        <CardTitle>Filters</CardTitle>
                    </CardHeader>
                    <CardContent className="space-y-4">
                        <div className="relative">
                            <Search className="absolute left-2 top-2.5 h-4 w-4 text-muted-foreground" />
                            <Input
                                placeholder="Search messages..."
                                value={searchTerm}
                                onChange={handleSearchChange}
                                className="pl-8"
                            />
                        </div>

                        <div>
                            <h3 className="text-sm font-medium mb-2 flex items-center">
                                <Filter className="w-4 h-4 mr-2" />
                                Filter by Service Type
                            </h3>
                            <div className="space-y-2">
                                <Button
                                    variant={activeFilter === "all" ? "default" : "outline"}
                                    size="sm"
                                    onClick={() => handleFilterChange("all")}
                                    className="w-full justify-start"
                                >
                                    All Messages
                                </Button>
                                <Button
                                    variant={activeFilter === "plumbing" ? "default" : "outline"}
                                    size="sm"
                                    onClick={() => handleFilterChange("plumbing")}
                                    className="w-full justify-start"
                                >
                                    Plumbing
                                </Button>
                                <Button
                                    variant={activeFilter === "electrical" ? "default" : "outline"}
                                    size="sm"
                                    onClick={() => handleFilterChange("electrical")}
                                    className="w-full justify-start"
                                >
                                    Electrical
                                </Button>
                                <Button
                                    variant={activeFilter === "cleaning" ? "default" : "outline"}
                                    size="sm"
                                    onClick={() => handleFilterChange("cleaning")}
                                    className="w-full justify-start"
                                >
                                    Cleaning
                                </Button>
                            </div>
                        </div>
                    </CardContent>
                </Card>

                {/* Message List */}
                <Card className="lg:col-span-2">
                    <CardHeader>
                        <CardTitle>Messages</CardTitle>
                    </CardHeader>
                    <CardContent>
                        <div className="space-y-4">
                            {filteredMessages.length > 0 ? (
                                filteredMessages.map((message) => (
                                    <Card
                                        key={message.messageId}
                                        className={`p-4 cursor-pointer transition-all ${
                                            selectedMessage?.messageId === message.messageId
                                                ? 'border-primary bg-muted'
                                                : 'hover:bg-muted/50'
                                        }`}
                                        onClick={() => setSelectedMessage(message)}
                                    >
                                        <div className="flex items-start gap-4">
                                            <Avatar className="h-10 w-10">
                                                <AvatarFallback className="bg-primary/10">
                                                    {getInitials(message.client.name)}
                                                </AvatarFallback>
                                            </Avatar>

                                            <div className="flex-1 min-w-0">
                                                <div className="flex justify-between items-start">
                                                    <div>
                                                        <p className="font-medium">
                                                            {message.client.name} ↔ {message.serviceProvider.name}
                                                        </p>
                                                        <p className="text-sm text-muted-foreground">
                                                            {message.serviceProvider.serviceType} Service
                                                        </p>
                                                    </div>
                                                    <Badge variant="outline" className="flex items-center">
                                                        <Calendar className="w-3 h-3 mr-1" />
                                                        {new Date(message.timeStamp).toLocaleDateString()}
                                                    </Badge>
                                                </div>

                                                <p className="mt-2 text-sm line-clamp-2">
                                                    {message.content}
                                                </p>
                                            </div>
                                        </div>
                                    </Card>
                                ))
                            ) : (
                                <div className="text-center py-8">
                                    <MessageSquare className="w-12 h-12 text-muted-foreground mx-auto mb-4" />
                                    <p className="text-muted-foreground">No messages found</p>
                                    <p className="text-sm text-muted-foreground mt-1">
                                        Try adjusting your search or filter criteria
                                    </p>
                                </div>
                            )}
                        </div>
                    </CardContent>
                </Card>
            </div>

            {/* Message Detail Panel */}
            {selectedMessage && (
                <Card>
                    <CardHeader>
                        <CardTitle>Message Details</CardTitle>
                    </CardHeader>
                    <CardContent>
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                            <div className="md:col-span-2">
                                <Card className="p-4 bg-muted">
                                    <p className="text-lg">{selectedMessage.content}</p>
                                </Card>

                                <div className="mt-4 flex items-center text-sm text-muted-foreground">
                                    <Calendar className="w-4 h-4 mr-1" />
                                    Sent on {formatDate(selectedMessage.timeStamp)}
                                </div>
                            </div>

                            <div className="space-y-4">
                                <Card className="p-4">
                                    <h3 className="font-medium mb-2 flex items-center">
                                        <User className="w-4 h-4 mr-2" />
                                        Client Information
                                    </h3>
                                    <div className="flex items-center gap-3">
                                        <Avatar>
                                            <AvatarFallback className="bg-blue-100">
                                                {getInitials(selectedMessage.client.name)}
                                            </AvatarFallback>
                                        </Avatar>
                                        <div>
                                            <p className="font-medium">{selectedMessage.client.name}</p>
                                            <p className="text-sm text-muted-foreground">{selectedMessage.client.email}</p>
                                        </div>
                                    </div>
                                </Card>

                                <Card className="p-4">
                                    <h3 className="font-medium mb-2 flex items-center">
                                        <User className="w-4 h-4 mr-2" />
                                        Provider Information
                                    </h3>
                                    <div className="flex items-center gap-3">
                                        <Avatar>
                                            <AvatarFallback className="bg-green-100">
                                                {getInitials(selectedMessage.serviceProvider.name)}
                                            </AvatarFallback>
                                        </Avatar>
                                        <div>
                                            <p className="font-medium">{selectedMessage.serviceProvider.name}</p>
                                            <p className="text-sm text-muted-foreground">
                                                {selectedMessage.serviceProvider.serviceType}
                                            </p>
                                        </div>
                                    </div>
                                </Card>

                                <Card className="p-4">
                                    <h3 className="font-medium mb-2">Message Metadata</h3>
                                    <div className="space-y-2 text-sm">
                                        <div className="flex justify-between">
                                            <span className="text-muted-foreground">Message ID:</span>
                                            <span>{selectedMessage.messageId}</span>
                                        </div>
                                        <div className="flex justify-between">
                                            <span className="text-muted-foreground">Client ID:</span>
                                            <span>{selectedMessage.client.userId}</span>
                                        </div>
                                        <div className="flex justify-between">
                                            <span className="text-muted-foreground">Provider ID:</span>
                                            <span>{selectedMessage.serviceProvider.userId}</span>
                                        </div>
                                    </div>
                                </Card>
                            </div>
                        </div>
                    </CardContent>
                </Card>
            )}
        </div>
    );
};

export default MessageDashboard;