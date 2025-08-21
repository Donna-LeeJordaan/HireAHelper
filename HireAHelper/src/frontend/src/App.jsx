import './App.css';
import logo from './assets/logo1.png';

function App() {
    return (
        <div className="app-container">
            <img src={logo} alt="Hire A Helper Logo" className="logo-one" />
            <h1>Welcome to Hire A Helper</h1>
            <p>Find help with services at your fingertips.</p>
            <button className="get-started-btn">Get Started</button>
        </div>
    );
}

export default App;
