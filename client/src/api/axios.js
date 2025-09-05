import axios from "axios";

const BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

export const apiMain = axios.create({
    baseURL: BASE_URL
});

export const loginApi = async (email, password) => {
    const response = await apiMain.post("/api/v1/auth/login", {email, password}, {
        headers: {
            "Content-Type": "application/json"
        }
    });

    return response.data.token;
}

export const logoutApi = async () => {
    const token = localStorage.getItem("token");
    return apiMain.post("/api/v1/auth/logout", {}, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
};

export const registerApi = async (registerData) => {
    const response = await apiMain.post("/api/v1/auth/register", registerData, {
        headers: {"content-type": "application/json"}
    });
    return response.data;
}

export const getTotalBalance = async () => {
    const token = localStorage.getItem("token");

    const  {data: totalExpenses} = await apiMain.get("/api/v1/transactions/expenses/total", {
        headers: {Authorization: `Bearer ${token}`}
    });

    const {data: totalIncome} = await apiMain.get("/api/v1/transactions/income/total", {
        headers: {Authorization: `Bearer ${token}`}
    });

    return {totalExpenses, totalIncome};
}

export const getBalanceByYearMonth = async (year, month) => {
    const token = localStorage.getItem("token");

    const expensesReq = await apiMain.get(`/api/v1/transactions/expenses/month/${year}/${month}`, {
        headers: {Authorization: `Bearer ${token}`}
    });

    const incomeReq = await apiMain.get(`/api/v1/transactions/income/month/${year}/${month}`, {
        headers: {Authorization: `Bearer ${token}`}
    });

    return {expensesReq: expensesReq.data, incomeReq: incomeReq.data};
}


