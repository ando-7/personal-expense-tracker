import "../css/App.css";
import {Link, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {useAuth} from "../contexts/AuthContext.jsx";
import {handleLogin} from "../services/handleAuth.js";

const Login = () => {

    const { user, login } = useAuth();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();
    const [successfulLogin, setSuccessfulLogin] = useState("");

    useEffect(() => {
        if(user) {
            navigate("/me");
        }
    }, [user]);

    return (
        <form onSubmit={(e) => handleLogin(e, email, password, login, navigate, setSuccessfulLogin)}>
            <div className="flex flex-col items-center justify-center p-6 h-screen">
                <div className="register-login-box">
                    <div>
                        <h1 className="text-center text-shadow-amber-100 font-bold mb-3">Login</h1>
                    </div>
                    <div className="flex flex-col items-center justify-between h-30 border-amber-50">
                        <input type="text" placeholder="Email" className="register-login-input"
                               onChange={e => setEmail(e.target.value)}/>
                        <input type="password" placeholder="Password" className="register-login-input"
                               onChange={e => setPassword(e.target.value)}/>
                    </div>
                    {successfulLogin && <p className="text-red-600">{successfulLogin}</p>}
                    <div className="flex">
                        <button className="m-4 bg-gray-900" type="submit">Login</button>
                        <Link to="/register">
                            <button className="m-4 text-amber-50 bg-gray-900">Sign up</button>
                        </Link>
                    </div>
                </div>

            </div>
        </form>
    )
}

export default Login;