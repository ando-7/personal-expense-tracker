import {useState} from "react";
import {useTransaction} from "../contexts/TransactionContext.jsx";
import {CircleMinus, CirclePlus} from "lucide-react";


const RecentTransactions = () => {

    const {currentMonthIncome, currentMonthExpense} = useTransaction();
    const [transactionType, setTransactionType] = useState("expense" || "income");

    return (
        <div className="flex items-center justify-center flex-col">
            <div className="flex justify-end h-fit w-full m-4 max-w-none rounded-lg">
                <div className="bg-black rounded-lg">
                    <button className="p-2 border-none" onClick={() => setTransactionType("expense")}>
                        <CircleMinus className="text-red-400 border-none"/>
                    </button>
                    <button className="p-2 border-none" onClick={() => setTransactionType("income")}>
                        <CirclePlus className="text-green-500 border-none "/>
                    </button>
                </div>

            </div>
            <div className="flex flex-col items-center justify-evenly bg-black h-fit w-full max-w-none rounded-lg">
                <table className="table-auto border-collapse w-full text-center">
                    <thead className="text-2xl">
                        <tr>
                            <th className="px-4 py-2">Name</th>
                            <th className="px-4 py-2">Date</th>
                            {transactionType === "expense" && (
                                <th className="px-4 py-2">Category</th>
                            )}
                            <th className="px-4 py-2">Amount</th>
                        </tr>
                    </thead>

                    <tbody className="text-lg">
                    {transactionType === "income"
                        ? currentMonthIncome.map((element) => (
                            <tr key={element.id} className="border-t">
                                <td className="px-4 py-2">
                                    <div className="flex items-center justify-center gap-2">
                                        <img
                                            alt="increase-logo"
                                            src={"increase-logo.png"}
                                            className="w-6 h-6"
                                        />
                                        <span className="truncate max-w-[120px]">{element.name}</span>
                                    </div>
                                </td>
                                <td className="px-4 py-2">{element.date}</td>
                                <td className="px-4 py-2">€ {element.amount}</td>
                            </tr>
                        ))
                        : currentMonthExpense.map((element) => (
                            <tr key={element.id} className="border-t">
                                <td className="px-4 py-2">
                                    <div className="flex items-center justify-center gap-2">
                                        <img
                                            alt="decrease-logo"
                                            src={"decrease-logo.png"}
                                            className="w-6 h-6"
                                        />
                                        <span className="truncate max-w-[120px]">{element.name}</span>
                                    </div>
                                </td>
                                <td className="px-4 py-2">{element.date}</td>
                                <td className="px-4 py-2">{element.category}</td>
                                <td className="px-4 py-2">€ {element.amount}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>

    )
}

export default RecentTransactions;