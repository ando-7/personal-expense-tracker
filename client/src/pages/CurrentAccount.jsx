import Navbar from "../component/Navbar.jsx";
import OverviewGeneral from "../component/OverviewGeneral.jsx";
import RecentTransactions from "../component/RecentTransactions.jsx";
import {TransactionContextProvider} from "../contexts/TransactionContext.jsx";

const CurrentAccount = () => {
    // const {logout} = useAuth();
    // const navigate = useNavigate();

    return (
        <TransactionContextProvider>
            <div className="flex h-screen">
                <div className="flex-0.8">
                    <Navbar/>
                </div>
                <div className="flex-9 flex flex-col items-center justify-start overflow-y-auto">
                    <div className="w-full max-w-lg p-4 mb-4 rounded-lg ">
                        <OverviewGeneral/>
                    </div>
                    <div className="w-full max-w-3xl p-4 mb-4 rounded-lg ">
                        <RecentTransactions/>
                    </div>
                </div>
            </div>
        </TransactionContextProvider>
    )
}

export default CurrentAccount;