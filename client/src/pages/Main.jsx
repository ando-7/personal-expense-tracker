import {Link} from "react-router-dom";

const Main = () => {
    return (
        <div className="flex flex-col items-center justify-center h-screen  text-white">
            <h1 className="text-3xl font-bold mb-4">Personal Expense Tracker</h1>
            <p className="text-white mb-8">
                Track your income and expenses, stay on top of your finances.
            </p>

            <div className="flex gap-4">
                <Link to="/login">
                    <button className="px-6 py-2 text-white bg-blue-600 rounded-xl shadow-lg hover:shadow-xl transition">
                        Login
                    </button>
                </Link>
                <Link to="/register">
                    <button className="px-6 py-2 text-white bg-yellow-500 rounded-xl shadow-lg hover:shadow-xl transition ">
                        Register
                    </button>
                </Link>
            </div>
        </div>
    );
}

export default Main;