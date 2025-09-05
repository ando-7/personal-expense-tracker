import {useAuth} from "../contexts/AuthContext.jsx";
import {registerApi} from "../api/axios.js";

export const handleLogin = async (e, email, password, login, navigator, setSuccessfulLogin) => {
    e.preventDefault();
    try {
       const token = await login(email, password);
       navigator("/me");
    } catch (error) {
        setSuccessfulLogin("Wrong email or password");
    }

}

export const handleLogout = async (e, logout, navigator) => {
    e.preventDefault();
    await logout();
    navigator("/login");
}

export const handleRegistration = async (e, validator, regData, errorSetter, setSuccess) => {
    e.preventDefault();
    errorSetter("");
    if(!validator) {
        return;
    }
    try {
        await registerApi(regData);
        setSuccess(true);
    } catch (err) {
        if(err.response && err.response.data) {
            errorSetter(err.response.data.message);
        } else {
            alert("Something went wrong");
        }
    }

}

export const handleRegistrationForm = (e, stateSetter, setValid) => {
    const {name, value} = e.target;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    stateSetter((prev) => ({
        ...prev,
        [name]: value,
    }));

    if(name === "email") {
        setValid(emailRegex.test(value));
    }
}