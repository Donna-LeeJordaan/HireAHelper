import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../css/ClientRegister.css";
import axios from "axios";

function Login() {
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
            const formBody = new URLSearchParams();
            formBody.append("email", formData.email);
            formBody.append("password", formData.password);

            const { data } = await axios.post(
            "http://localhost:8080/HireAHelper/login",
            formBody.toString(),
    { headers: { "Content-Type": "application/x-www-form-urlencoded" },
            withCredentials: true,
    });

            console.log("Logged in user:", data);

        switch (data.role) {
            case "CLIENT":
                navigate("/client/dashboard");
                break;
            case "SERVICE_PROVIDER":
                navigate("/serviceProvider/dashboard");
                break;
            case "ADMIN":
                navigate("/admin/dashboard");
                break;
            default:
                alert("Unknown role. Contact support.");
                break;
        }
    } catch (err) {
        console.error(err);
        alert(err.response?.data || err.message || "Login failed");
    }
};

    return (
        <div className="client-register-container">
            <div className="client-register-card">
                <h2>Login</h2>
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

export default Login;
