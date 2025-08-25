import React, { useState, useEffect } from 'react';

const CreateBooking = () => {
    const [serviceType, setServiceType] = useState('');
    const [serviceProviders, setServiceProviders] = useState([]);
    const [selectedProvider, setSelectedProvider] = useState(null);

    const handleSearch = async () => {
        // Fetch service providers based on serviceType
        const response = await fetch(`/api/service-providers?serviceType=${serviceType}`);
        const data = await response.json();
        setServiceProviders(data);
    };

    const handleProviderSelect = (provider) => {
        setSelectedProvider(provider);
    };

    return (
        <div>
            <h1>Create Booking</h1>
            <input
                type="text"
                value={serviceType}
                onChange={(e) => setServiceType(e.target.value)}
                placeholder="Search for a service type"
            />
            <button onClick={handleSearch}>Search</button>

            {serviceProviders.length > 0 && (
                <div>
                    <h2>Service Providers</h2>
                    {serviceProviders.map((provider) => (
                        <div key={provider.id} onClick={() => handleProviderSelect(provider)}>
                            <img src={provider.profileImage} alt={provider.name} />
                            <h3>{provider.name}</h3>
                            <p>{provider.email}</p>
                            <p>Rate: ${provider.rate}</p>
                        </div>
                    ))}
                </div>
            )}

            {selectedProvider && (
                <div>
                    <h2>Selected Provider: {selectedProvider.name}</h2>
                    {/* Additional booking form fields can go here */}
                </div>
            )}
        </div>
    );
};

export default CreateBooking;
