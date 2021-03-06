import React, {Component} from "react";
import {Route, Routes} from "react-router-dom";
import "./App.css";
import AuthService from "./services/authService";
import CreateAccount from "./components/CreateAccount";
import ViewAccount from "./components/viewAccount";
import UpdateAccount from "./components/UpdateAccount";

class App extends Component {
    constructor(props) {
        super(props);
        this.logOut = this.logOut.bind(this);

        this.state = {
            currentUser: {username: ""},
            Navigate: null,
        };
    }


    componentDidMount() {
        const user = AuthService.getCurrentUser();

        if (!user) this.setState({Navigate: "/login"});

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
            <div>
                <div className="">
                    <Routes>
                        <Route path="/createAccount" element={<CreateAccount/>}/>
                        <Route path="/myaccount" element={<ViewAccount/>}/>
                        <Route path="/updateAccount/:email" element={<UpdateAccount/>}/>
                    </Routes>
                </div>

            </div>
        );
    }
}

export default App;