import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from './pages/Login.jsx';
import Main from './pages/Main.jsx';
import Register from "./pages/Register.jsx";
import CurrentAccount from "./pages/CurrentAccount.jsx";
import PrivateRoute from "./component/common/PrivateRoute.jsx";

function App() {

    return (
        <div className="w-screen min-h-screen h-screen">
            <BrowserRouter basename="/">
                <Routes>
                    <Route path="/" element={<Main/>}/>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/register" element={<Register/>}/>
                    <Route path="/me" element={
                        <PrivateRoute>
                            <CurrentAccount/>
                        </PrivateRoute>
                    }/>
                </Routes>
            </BrowserRouter>
        </div>
    )
}

export default App;
