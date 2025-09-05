import { getTotalBalance} from "../api/axios.js";
import {useEffect, useState} from "react";
import {useAuth} from "../contexts/AuthContext.jsx";
import MonthSwitcher from "./MonthSwitcher.jsx";
import {useTransaction} from "../contexts/TransactionContext.jsx";

const OverviewGeneral = () => {

    const {token} = useAuth();
    const {currentMonthExpenseSum, currentMonthIncomeSum} = useTransaction();
    const [totalIncome, setTotalIncome] = useState(0);
    const [totalExpenses, setTotalExpenses] = useState(0);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (!token) return;
        const fetchBalance = async () => {
            try {
                const {totalIncome, totalExpenses} = await getTotalBalance();
                setTotalIncome(totalIncome);
                setTotalExpenses(totalExpenses);
                setLoading(false);
            } catch (error) {
                console.error("Failed to fetch total balance:", error);
            }
        };

        fetchBalance();

    }, [token]);


    return (
        <div className="flex items-center justify-center flex-col">
            <div className="flex items-center justify-center flex-col">
                <h2 className="text-xl">Total balance</h2>
                {loading ? (<h1>Loading...</h1>) : (<h1> € {totalIncome - totalExpenses}</h1>)}
            </div>
            <div className="flex flex-col items-center justify-evenly bg-black h-30 w-full max-w-md mt-4 rounded-lg">
                <div>
                    <span className="text-xl">Monthly review</span>
                </div>

                <div className="flex items-center justify-evenly w-full">
                    <div className="flex flex-col items-center justify-center">
                        <span>Income</span>
                        <span className="text-green-500">€ {currentMonthIncomeSum}</span>
                    </div>
                    <div className="flex flex-col items-center justify-center">
                        <span>Expenses</span>
                        <span className="text-red-600">€ {currentMonthExpenseSum}</span>
                    </div>
                    <div className="flex flex-col items-center justify-center">
                        <span>Total</span>
                        <span>€ {currentMonthIncomeSum - currentMonthExpenseSum}</span>
                    </div>
                </div>

            </div>
            <div>
                <MonthSwitcher/>
            </div>

        </div>)
}

export default OverviewGeneral;