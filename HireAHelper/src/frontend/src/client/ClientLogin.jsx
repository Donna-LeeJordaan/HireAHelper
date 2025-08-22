import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./ClientRegister.css"; // Reuse your registration CSS

function ClientLogin() {
    const [formData, setFormData] = useState({
        email: "",
        password: ""
    });

    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const res = await fetch("http://localhost:8080/HireAHelper/client/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(formData)
            });

            if (!res.ok) {
                const errorText = await res.text();
                throw new Error(errorText || "Invalid email or password");
            }

            const data = await res.json();
            alert("Login successful!");

            // Save client info to localStorage
            localStorage.setItem("client", JSON.stringify(data));

            // Redirect to client dashboard
            navigate("/client/dashboard");
        } catch (err) {
            console.error(err);
            alert(err.message);
        }
    };

    return (
        <div className="client-register-container">
            <div className="client-register-card">
                <h2>Client Login</h2>
                <form onSubmit={handleSubmit}>
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
                    <button type="submit" className="client-register-btn">
                        Login
                    </button>
                </form>
            </div>
        </div>
    );
}

export default ClientLogin;
