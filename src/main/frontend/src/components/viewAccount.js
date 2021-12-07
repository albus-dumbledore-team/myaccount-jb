import React, {Component} from "react";
import AuthService from "../services/authService";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import DatePicker from "react-datepicker";

export default class ViewAccount extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            name: "",
            username: "",
            email: "",
            phoneNumber: "",
            address: "",
            dateOfBirth: "",

        };
    }


    render() {

        return (
            <div className="viewAccount">
                <label htmlFor="text">Enter username</label>
                <br />
                <div id="username">
                    <input onChange={this.changeValue}
                           type="text" value={this.state.username} />
                </div>
                <div className="button">
                    <button onClick={this.getAccount}>
                        View Account
                    </button>
                </div>
                <div>
                    <h1>Name: {this.state.name}</h1>
                    <h3>Email: {this.state.email}</h3>
                    <h3>Address: {this.state.address}</h3>
                    <h4>PhoneNumber: {this.state.phoneNumber}</h4>
                    <h5>DateOfBirth: {this.state.dateOfBirth}</h5>

                </div>
            </div>
        );
    }

    changeValue = (event) => {

        this.setState({
            username: event.target.value
        });
    }

    getAccount = () => {

        fetch(`https://localhost:8080/viewAccount/${this.state.username}`)
            .then(response => response.json())
            .then(data => {
                this.setState({
                    name: data.name,
                    email: data.email,
                    address: data.address,
                    phoneNumber: data.phoneNumber,
                    dateOfBirth: data.dateOfBirth
                });
            }).catch(err=>{
            console.log(err)})

    }
}


