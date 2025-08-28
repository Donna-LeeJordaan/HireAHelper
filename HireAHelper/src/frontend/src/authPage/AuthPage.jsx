import { useNavigate } from "react-router-dom";
import logo from "../assets/logo1.png";
import "./AuthPage.css";

function AuthPage() {
    const navigate = useNavigate();

    return (
        <div className="auth-container">
            <div className="auth-card">
                <img src={logo} alt="Hire A Helper Logo" className="auth-logo" />
                <h2>Join Hire A Helper</h2>

                <div className="auth-buttons">
                    <button
                        className="auth-btn"
                        onClick={() => navigate("/client/register")}
                    >
                        Sign Up
                    </button>
                    <button
                        className="auth-btn"
                        onClick={() => navigate("/client/login")}
                    >
                        Login
                    </button>
                </div>

                <p className="provider-text" onClick={() => navigate("/serviceProvider/Register")}>Sign up as a service provider</p>
            </div>
        </div>
    );
}

export default AuthPage;
