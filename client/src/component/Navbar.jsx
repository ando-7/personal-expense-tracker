import {useAuth} from "../contexts/AuthContext.jsx";
import {Link, useNavigate} from "react-router-dom";
import {navbarItems} from "../fields/fields.js";
import {handleLogout} from "../services/handleAuth.js";

const Navbar = () => {

    const {logout} = useAuth();
    const navigate = useNavigate();

    return (
        <div className="w-40 h-screen bg-green-700 flex flex-col">
            <nav className="mt-4 mx-4">
                <div className="mb-5">
                    <Link to={'/income'}>
                        <img alt={"home"} src={"home-icon.png"}/>
                    </Link>
                </div>

                {navbarItems.map((item, index) => (
                    <div className="my-2" key={index}>
                        <Link to={item.link}>
                            <div className="bg-amber-100  rounded-2xl flex justify-evenly">
                                <img alt={item.alt} src={item.src} className="w-10 h-10"/>
                                <p className="my-1.5 text-black">{item.title}</p>
                            </div>
                        </Link>
                    </div>
                ))}

                <div>
                    <button
                        type="button"
                        className=" flex w-full items-center gap-2 rounded-2xl px-3 py-2 bg-amber-600 text-black whitespace-nowrap"
                        onClick={(e) => handleLogout(e, logout, navigate)}
                    >
                        <img alt="sign out" src={"icons8-sign-out-96.png"} className="w-6 h-6"/>
                        <span>Sign out</span>
                    </button>
                </div>
            </nav>
        </div>
    )
}

export default Navbar;