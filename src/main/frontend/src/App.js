import React, { Component } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

import "./App.css";
import AuthService from "./services/authService";
import CreateAccount from "./components/createAccount";
import ViewAccount from "./components/viewAccount";

class App extends Component {
    constructor(props) {
        super(props);
        this.logOut = this.logOut.bind(this);

        this.state = {
            currentUser: { username: "" },
            Navigate: null,
        };
    }


    componentDidMount() {
        const user = AuthService.getCurrentUser();

        if (!user) this.setState({ Navigate: "/login" });

        if (user) {
            this.setState({
                currentUser: user,
            });
        }
    }

    logOut() {
        AuthService.logout();
        this.setState({
            currentUser: null,
        });
    }

    render() {

        return (
            <div className="mainDiv">
                <nav className="navbar navbar-expand navbar-dark bg-dark">
                        <div className="navbar-nav ml-auto">
                            <li className="nav-item">
                                <Link to={"/createaccount"} className="nav-link">
                                    Create account
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link to={"/myaccount"} className="nav-link">
                                    My account
                                </Link>
                            </li>
                        </div>
                </nav>

                    <Routes>
                        <Route path="/createAccount" element={<CreateAccount />} />
                        <Route path="/myaccount" element={<ViewAccount />} />
                    </Routes>

            </div>
        );
    }
}

export default App;