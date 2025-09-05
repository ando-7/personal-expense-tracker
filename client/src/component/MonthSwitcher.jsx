import {ChevronLeft, ChevronRight} from "lucide-react";
import {useTransaction} from "../contexts/TransactionContext.jsx";

export default function MonthSwitcher() {
    const {date, setDate} = useTransaction();
    const monthNames = [
        "Jan.", "Feb.", "Mar.", "Apr.", "May.", "Jun.",
        "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."
    ];

    const handlePrev = () => {
        setDate(prev => new Date(prev.getFullYear(), prev.getMonth() - 1));
    };

    const handleNext = () => {
        setDate(prev => new Date(prev.getFullYear(), prev.getMonth() + 1));
    };

    return (
        <div className="flex items-center justify-center p-2 rounded-md w-full max-w-sm mt-2">
            <button onClick={handlePrev} className="p-2 border-none">
                <ChevronLeft className="text-white border-none"/>
            </button>

            <span className="text-white text-lg font-medium">
                {monthNames[date.getMonth()]} {date.getFullYear()}
            </span>

            <button onClick={handleNext} className="p-2 border-none">
                <ChevronRight className="text-white border-none"/>
            </button>
        </div>
    );
}
