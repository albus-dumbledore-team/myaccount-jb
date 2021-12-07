import React, {Component} from "react";
import AuthService from "../services/authService";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";

import "../styles/view.css"

export default class ViewAccount extends React.Component {
    constructor(props) {
        super(props);
        this.onChange = this.onChange.bind(this);
        this.onSubmitName = this.onSubmitName.bind(this);
        this.handleRegister = this.handleRegister.bind(this);

        this.state = {
            name: "",
            username: "",
            email: "",
            phoneNumber: "",
            address: "",
            dateOfBirth: "",
        };
    }

    onChange(ev) {
        this.setState({username: ev.target.value})
    }

    onSubmitName(ev) {
        this.setState({name: this.state.name})
    }

    handleRegister(e) {
        e.preventDefault();
        AuthService.viewAccount(
            this.state.username,
        ).then(response =>{
            this.setState({
                name: response.data.name,
                username: response.data.username,
                email: response.data.email,
                phoneNumber: response.data.phoneNumber,
                address: response.data.address,
                dateOfBirth: response.data.dateOfBirth,
            })
        },
        error => {
            console.log("Account not found");
            console.log(error);
            return (
                <div className="alert alert-danger" role="alert">
                    Account not found
                </div>
            );
        });
    };

    render() {
        return (
            <div className="viewDiv">
                <div className="cardView">
                    <h2>View account</h2>

                    <Form className="viewForm"
                          onSubmit={this.handleRegister}
                          ref={c => {
                              this.form = c;
                          }}
                    >
                        {(
                            <div>
                                <div className="form-group">
                                    <label htmlFor="username">Username:</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        username="username"
                                        value={this.state.username}
                                        onChange={this.onChange}
                                    />
                                </div>

                                <div className="form-group-btn">
                                    <button className="btnViewAccount btn-primary btn-block">View account</button>
                                </div>

                                <div className="form-group">
                                    <label htmlFor="name">Name: {this.state.name}</label>
                                </div>

                                <div className="form-group">
                                    <label htmlFor="email">Email: {this.state.email}</label>
                                </div>

                                <div className="form-group">
                                    <label htmlFor="phoneNumber">Phone number: {this.state.phoneNumber}</label>
                                </div>

                                <div className="form-group">
                                    <label htmlFor="datePicker">Birthdate: {this.state.dateOfBirth}</label>
                                </div>
                            </div>
                        )}
                    </Form>
                </div>
            </div>
        );
    }
}