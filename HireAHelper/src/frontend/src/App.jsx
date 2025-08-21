import { Routes, Route } from "react-router-dom";
import Home from "./Home.jsx";
import AuthPage from "./AuthPage.jsx";
import ClientRegister from "./client/ClientRegister.jsx";

function App() {
    return (
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/auth" element={<AuthPage />} />
            <Route path="/client-register" element={<ClientRegister />} />
        </Routes>
    );
}

export default App;
