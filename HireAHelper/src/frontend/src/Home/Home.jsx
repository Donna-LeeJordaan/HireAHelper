import { useNavigate } from "react-router-dom";
import logo from "../assets/logo1.png";
import "../css/Home.css";

function Home() {
    const navigate = useNavigate();

    return (
        <div className="home-container">
            <img src={logo} alt="Hire A Helper Logo" className="logo-one" />
            <h1>Welcome to Hire A Helper</h1>
            <p>Find help with services at your fingertips.</p>
            <p className="sub-text">
                Hire A Helper allows users to search for local service providers and book appointments seamlessly through an easy booking system.
            </p>
            <button className="get-started-btn" onClick={() => navigate("/auth")}>
                Get Started
            </button>
        </div>
    );
}

export default Home;
