import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import {required, vaddress, vpassword, vemail, vname, vphonenumber} from "./utils";
import CheckButton from "react-validation/build/button";

import AuthService from "../services/authService";
import "../styles/createAccount.css"

export default class CreateAccount extends Component {
  constructor(props) {
    super(props);
    this.handleRegister = this.handleRegister.bind(this);
    this.onChangeName = this.onChangeName.bind(this);
    this.onChangeEmail = this.onChangeEmail.bind(this);
    this.onChangePassword = this.onChangePassword.bind(this);
    this.onChangePhoneNumber = this.onChangePhoneNumber.bind(this);
    this.onChangeDateOfBirth = this.onChangeDateOfBirth.bind(this);
    this.onChangeAddress = this.onChangeAddress.bind(this);

    this.state = {
      name: "",
      username: "",
      email: "",
      password: "",
      phoneNumber: "",
      address: "",
      dateOfBirth: new Date(),
      successful: false,
      message: ""
    };
  }

  onChangeName(e) {
    this.setState({
      name: e.target.value
    });
  }

  onChangeEmail(e) {
    this.setState({
      email: e.target.value
    });
  }

  onChangePassword(e) {
    this.setState({
      password: e.target.value
    });
  }

  onChangePhoneNumber(e) {
    this.setState({
      phoneNumber: e.target.value
    });
  }

  onChangeAddress(e) {
    this.setState({
      address: e.target.value
    });
  }

  onChangeDateOfBirth(e) {
    this.setState({
      dateOfBirth: e
    });
  }

  handleRegister(e) {
    console.log("handleRegister")
    e.preventDefault();

    this.setState({
      message: '',
      successful: false,
    });

    this.form.validateAll();
    console.log("message length", this.state.message.length);
    if (this.checkBtn.context._errors.length === 0)
      AuthService.register(
        this.state.name,
        this.state.email,
        this.state.password,
        this.state.phoneNumber,
        this.state.address,
        this.state.dateOfBirth,
      ).then(
        response => {
          this.setState({
            message: response.data,
            successful: true
          });
        },
        error => {
          console.log("Create account error");
          console.log(error);

          this.setState({
            successful: false,
            message: error.response.data
          });
          
        }
      );
  }

  render() {
    return (
      <div className="registerDiv">
        <div className="cardRegister">
          <h2>Create account</h2>

          <Form className="registerForm"
            onSubmit={this.handleRegister}
            ref={c => {
              this.form = c;
            }}
          >
            {!this.state.successful && (
              <div>
                <div className="form-group">
                  <label htmlFor="name">Name:</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="name"
                    value={this.state.name}
                    onChange={this.onChangeName}
                    validations={[required, vname]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="email">Email:</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="email"
                    value={this.state.email}
                    onChange={this.onChangeEmail}
                    validations={[required, vemail]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="password">Password:</label>
                  <Input
                    type="password"
                    className="form-control"
                    name="password"
                    value={this.state.password}
                    onChange={this.onChangePassword}
                    validations={[required, vpassword]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="phoneNumber">Phone number:</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="phoneNumber"
                    value={this.state.phoneNumber}
                    onChange={this.onChangePhoneNumber}
                    validations={[required, vphonenumber]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="address">Address:</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="address"
                    value={this.state.address}
                    onChange={this.onChangeAddress}
                    validations={[required, vaddress]}
                  />
                </div>

                <div className="form-group">
                    <label htmlFor="datePicker">Birthdate:</label>
                    <DatePicker selected={this.state.dateOfBirth} onChange={(newDate) => this.setState({dateOfBirth: newDate})} />
                </div>

                <div className="form-group-btn">
                  <button className="btnCreateAccount btn-primary">Create account</button>
                  {/* <input type="button" className="btnCreateAccount btn-primary" value="Create account" onClick={this.handleRegister}/> */}
                </div>
              </div>
            )}

            {this.state.message && (
              <div className="form-group">
                <div
                  className={
                    this.state.successful
                      ? "alert alert-success"
                      : "alert alert-danger"
                  }
                  role="alert"
                >
                  {this.state.message}
                </div>
              </div>
            )}
            <CheckButton
              style={{ display: "none" }}
              ref={c => {
                this.checkBtn = c;
              }}
            />
          </Form>
        </div>
      </div>
    );
  }
}