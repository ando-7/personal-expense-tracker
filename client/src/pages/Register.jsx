import "../css/App.css";
import {Link} from "react-router-dom";
import {useState} from "react";
import {handleRegistration, handleRegistrationForm} from "../services/handleAuth.js";
import {regForm} from "../fields/fields.js";

const Register = () => {

    const [regData, setRegData] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: ""
    });
    const [emailValid, setEmailValid] = useState(false);
    const [existingUserError, setExistingUserError] = useState("");
    const [success, setSuccess] = useState(false);

    return (
        !success ?
            <form onSubmit={e => handleRegistration(e, emailValid, regData, setExistingUserError, setSuccess)}>
                <div className="flex flex-col items-center justify-center p-6 h-screen">
                    <div className="register-login-box">
                        <div>
                            <h1 className="text-center text-shadow-amber-100 font-bold mb-3">Sign up</h1>
                        </div>
                        <div className="flex flex-col items-center justify-between border-amber-50">
                            {regForm.map((item) => (
                                <input key={item.id} type={item.type} name={item.title} placeholder={item.placeholder}
                                       required
                                       className="register-login-input"
                                       onChange={(e) => handleRegistrationForm(e, setRegData, setEmailValid)}
                                />
                            ))}
                            {!emailValid && regData.email.length > 0 &&
                                <p className="text-red-600">Invalid email format</p>}
                            {existingUserError && <p className="text-red-600">{existingUserError}</p>}
                        </div>
                        <div className="flex">
                            <button className="m-4 bg-blue-950" type="submit">Register</button>
                            <Link to="/login">
                                <button className="m-4 text-amber-50 bg-blue-950">Sign in</button>
                            </Link>
                        </div>
                    </div>

                </div>
            </form>
            :
            <div className="flex flex-col items-center justify-center p-6 h-screen">
                <div className="register-login-box">
                    <p>Successfully registered!</p>
                    <Link to="/login">
                        <p>Back to Login page</p>
                    </Link>
                </div>
            </div>

    )
}

export default Register;