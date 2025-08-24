import {useEffect, useState} from "react";
import { v4 as uuidv4 } from "uuid";
import "../css/ServiceProviderRegister.css";

function ServiceProviderRegister() {
const [formData, setFormData] = useState({
    userId: uuidv4(),
    name: "",
    email: "",
    password: "",
    mobileNumber: "",
    profileImage: null,
    description: "",
    rate: 0,
    area: null,
    serviceType: null

});

    {/*store sp dropdown selection*/}
const [areas, setAreas] = useState([]);
const [serviceTypes, setServiceTypes] = useState([]);

    {/*area fetching then populating the dropdown*/}
useEffect(() => {
    fetch("http://localhost:8080/HireAHelper/area/all")
        .then(res => res.json())
        .then(data => setAreas(data))
        .catch(err => console.error(err));

    {/*service fetching then populating the dropdown*/}
    fetch("http://localhost:8080/HireAHelper/serviceType/all")
        .then(res => res.json())
        .then(data => setServiceTypes(data))
        .catch(err => console.error(err));
}, []);

const handleChange = (e) => {
    const { name, value, files } = e.target;

    {/*finding and storing the area and service in formdata*/}
    if (name === "areaId") {
        const selectedArea = areas.find(a => a.areaId === value);
        setFormData(prev => ({ ...prev, area: selectedArea }));
    } else if (name === "serviceTypeId") {
        const selectedType = serviceTypes.find(t => t.typeId === value);
        setFormData(prev => ({ ...prev, serviceType: selectedType }));
    } else {
        setFormData(prev => ({ ...prev, [name]: files ? files[0] : value }));
    }
};

const handleSubmit = (e) => {
    e.preventDefault();

    {/*validate if the image is selected*/}
    if (!formData.profileImage) {
        alert("Please select a profile image");
        return;
    }

    {/*read the image as base64 for json*/}
    const reader = new FileReader();
    reader.readAsDataURL(formData.profileImage);
    reader.onloadend = async () => {
        const base64Image = reader.result.split(",")[1];

        {/*build object to send to backend */}
        const sp = {
            userId: formData.userId,
            role: "SERVICE_PROVIDER",
            name: formData.name,
            email: formData.email,
            mobileNumber: formData.mobileNumber,
            password: formData.password,
            profileImage: base64Image,
            rate: parseFloat(formData.rate),
            description: formData.description,
            area: formData.area,
            serviceType: formData.serviceType
        };


        try {
            const res = await fetch(
                "http://localhost:8080/HireAHelper/serviceProvider/create",
                {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(sp)
                }
            );

            if (!res.ok) {
                alert("Registration failed");
                return; // stop here, don't run success alert
            }
            alert("Service Provider Registered");

            {/*reset form*/}
            setFormData({
                userId: uuidv4(),
                name: "",
                email: "",
                password: "",
                mobileNumber: "",
                profileImage: null,
                description: "",
                rate: 0,
                area: null,
                serviceType: null

            });
        } catch (err) {
            console.error(err);
            alert(err.message);
        }
    };
};

    {/*sp form*/}
return (
    <div className="service-provider-register-container">
        <div className="service-provider-register-card">
            <h2>Service Provider Registration</h2>
            <form onSubmit={handleSubmit}>
                <input
                    name="name"
                    placeholder="Name"
                    value={formData.name}
                    onChange={handleChange}
                    required
                />
                <input
                    name="email"
                    type="email"
                    placeholder="Email"
                    value={formData.email}
                    onChange={handleChange}
                    required
                />
                <input
                    name="password"
                    type="password"
                    placeholder="Password"
                    value={formData.password}
                    onChange={handleChange}
                    required
                />
                <input
                    name="mobileNumber"
                    placeholder="Mobile Number"
                    value={formData.mobileNumber}
                    onChange={handleChange}
                    required
                />
                <input
                    name="description"
                    placeholder="Description"
                    value={formData.description}
                    onChange={handleChange}
                />
                <input
                    name="rate"
                    step="0.01"
                    placeholder="Rate"
                    value={formData.rate}
                    onChange={handleChange}
                />

                <select name="areaId"
                        onChange={handleChange}
                        required
                >
                    <option value="">Select Area</option>
                    {areas.map(a => (
                        <option key={a.areaId} value={a.areaId}>{a.name}</option>
                    ))}
                </select>

                <select
                    name="serviceTypeId"
                    onChange={handleChange}
                    required
                >
                    <option value="">Select Service Type</option>
                    {serviceTypes.map(t => (
                        <option key={t.typeId} value={t.typeId}>{t.typeName}</option>
                    ))}
                </select>
                    <p>Upload Profile Image</p>
                <input
                    type="file"
                    name="profileImage"
                    onChange={handleChange}
                />

                <button type="submit" className="service-provider-register-btn">
                    Register
                </button>
            </form>
        </div>
    </div>
);
}

export default ServiceProviderRegister