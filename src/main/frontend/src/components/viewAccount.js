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
            email: "",
            phoneNumber: "",
            address: "",
            dateOfBirth: "",
            message: ""
        };
    }

    onChange(ev) {
        this.setState({email: ev.target.value})
    }

    onSubmitName(ev) {
        this.setState({name: this.state.name})
    }

    handleRegister(e) {
        e.preventDefault();
        this.setState({
            name: "",
            email: "",
            phoneNumber: "",
            address: "",
            dateOfBirth: "",
            message: ""
        })
        if(this.state.email!="") {
            AuthService.viewAccount(
                this.state.email,
            ).then(response => {
                this.setState({
                    name: response.data.name,
                    email: response.data.email,
                    phoneNumber: response.data.phoneNumber,
                    address: response.data.address,
                    dateOfBirth: response.data.dateOfBirth,
                })
            }).catch(
                error => {
                    console.log("Account not found");
                    this.setState({
                        message: error.response.data
                    })
                });
        }
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
                                    <Input
                                        type="text"
                                        className="inputField"
                                        email="email"
                                        value={this.state.email}
                                        onChange={this.onChange}
                                        placeholder="Insert your email"
                                    />
                                </div>

                                {this.state.message && (
                                    <div className="form-group">
                                        <div role="alert">
                                            {this.state.message}
                                        </div>
                                    </div>
                                )}

                                <div className="form-group-btn">
                                    <button className="btnViewAccount btn-primary btn-block">View account</button>
                                </div>

                                <div className="form-group">
                                    <label htmlFor="name">Name: {this.state.name}</label>
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