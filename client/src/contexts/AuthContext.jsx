import { createContext, useContext, useState, useEffect } from "react";
import {loginApi, apiMain, logoutApi} from "../api/axios.js";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(localStorage.getItem("token") || null);

    const login = async (email, password) => {
        const token = await loginApi(email, password);
        localStorage.setItem("token", token);

        setToken(token);

        // Set token for future API calls
        apiMain.defaults.headers.common["Authorization"] = `Bearer ${token}`;

        const profile = await apiMain.get("/api/v1/users/me");
        setUser(profile.data);
    }

    const logout = async (skipServer = false) => {
        if(!skipServer) {
            try {
                await logoutApi();
            } catch (err) {
                console.log("Logout API failed (maybe token expired):", err);
            }
        }
            // Always remove local token and clear state
            localStorage.removeItem("token");
            setUser(null);
            setToken(null);
            delete apiMain.defaults.headers.common["Authorization"];
    }

    useEffect(() => {
        if (token) {
            // attach token to all requests
            apiMain.defaults.headers.common["Authorization"] = `Bearer ${token}`;

            // you can also fetch /me here if you want
            setUser({ token });
        }
    }, [token]);

    // interceptor to catch expired/invalid tokens
    useEffect(() => {
        const initAuth = async () => {
            if(token) {
                try {
                    apiMain.defaults.headers.common["Authorization"] = `Bearer ${token}`;
                    const response = await apiMain.get("/api/v1/users/me");
                    setUser(response.data);
                } catch (err) {
                    console.log("Stored token is invalid, logging out");
                    await logout(true);
                }
            }
        };

        initAuth();
    }, []); // runs once when AuthProvider mounts

    useEffect(() => {
        const interceptor = apiMain.interceptors.response.use(
            response => response,
            error => {
                if (error.response?.status === 401 || error.response.status === 403) {
                    console.log("Token expired or unauthorized → logging out");
                    logout(true);
                }
                return Promise.reject(error);
            }
        );

        return () => {
            apiMain.interceptors.response.eject(interceptor);
        };
    }, [logout]);

    return (
        <AuthContext.Provider value={{user, token, login, logout}}>
            {children}
        </AuthContext.Provider>
    )
};

export const useAuth = () => useContext(AuthContext);