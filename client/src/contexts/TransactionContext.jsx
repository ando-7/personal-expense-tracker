import {createContext, useContext, useState, useEffect} from "react";
import {getBalanceByYearMonth} from "../api/axios.js";

const TransactionContext = createContext();

export const TransactionContextProvider = ({children}) => {

    const [date, setDate] = useState(new Date());
    const [currentMonthIncome, setCurrentMonthIncome] = useState([]);
    const [currentMonthExpense, setCurrentMonthExpense] = useState([]);
    const [currentMonthExpenseSum, setCurrentMonthExpenseSum] = useState(0);
    const [currentMonthIncomeSum, setCurrentMonthIncomeSum] = useState(0);

    useEffect(() => {
        const fetchBalanceByMonth = async () => {
            try {
                const {incomeReq, expensesReq} = await getBalanceByYearMonth(date.getFullYear(), date.getMonth() + 1);
                const income = incomeReq.reduce((acc, cur) => acc + cur.amount, 0);
                const expenses = expensesReq.reduce((acc, cur) => acc + cur.amount, 0);
                setCurrentMonthIncome(incomeReq);
                setCurrentMonthExpense(expensesReq);
                setCurrentMonthExpenseSum(expenses);
                setCurrentMonthIncomeSum(income);
            } catch (error) {
                console.error("Failed to fetch current month balance:", error);
            }
        }

        fetchBalanceByMonth();
    }, [date]);

    return (
        <TransactionContext.Provider value={{date, setDate, currentMonthIncome, currentMonthExpense, currentMonthExpenseSum, currentMonthIncomeSum }}>
            {children}
        </TransactionContext.Provider>
    )
}

export const useTransaction = () => useContext(TransactionContext);