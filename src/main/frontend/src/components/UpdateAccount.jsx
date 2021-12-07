import React from "react";
import AuthService from "../services/authService"
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import DatePicker from "react-datepicker";
import {required, vaddress, vname, vphonenumber} from "./utils";
import {useParams} from "react-router-dom";


class UpdateAccount extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            name: "",
            username: "",
            email: "",
            phoneNumber: "",
            address: "",
            dateOfBirth: new Date(),
            successful: false,
            message: ""
        };
    }

    async componentDidMount() {
        console.log(this.props);
        const account = await AuthService.viewAccount(this.props.username);
        console.log({account})
        this.setState({...account.data, dateOfBirth: new Date(account.data.dateOfBirth)});
    }

    onChangeName = (e) => {
        this.setState({
            name: e.target.value
        });
    }

    onChangeUsername = (e) => {
        this.setState({
            username: e.target.value
        });
    }

    onChangeEmail = (e) => {
        this.setState({
            email: e.target.value
        });
    }

    onChangePhoneNumber = (e) => {
        this.setState({
            phoneNumber: e.target.value
        });
    }

    onChangeAddress = (e) => {
        this.setState({
            address: e.target.value
        });
    }

    onChangeDateOfBirth = (e) => {
        this.setState({
            dateOfBirth: e
        });
    }

    handleUpdate = (e) => {
        e.preventDefault();
        const {
            name,
            email,
            phoneNumber,
            address,
            dateOfBirth
        } = this.state;
        AuthService.updateAccount(
            this.props.username,
            {
                name,
                email,
                phoneNumber,
                address,
                dateOfBirth
            }).then(
            response => {
                this.setState({
                    message: response.data,
                    successful: true
                });
            },
            error => {

                this.setState({
                    successful: false,
                    message: error.response.data
                });
            })
    }

    render() {
        return (
            <div className="registerDiv">
                <div className="cardRegister">
                    <h2>Update account</h2>

                    <Form className="registerForm"
                          onSubmit={this.handleUpdate}
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

                                {/*<div className="form-group">*/}
                                {/*    <label htmlFor="username">Username:</label>*/}
                                {/*    <Input*/}
                                {/*        type="text"*/}
                                {/*        className="form-control"*/}
                                {/*        name="username"*/}
                                {/*        value={this.state.username}*/}
                                {/*        onChange={this.onChangeUsername}*/}
                                {/*        validations={[required, vusername]}*/}
                                {/*    />*/}
                                {/*</div>*/}

                                <div className="form-group">
                                    <label htmlFor="email">Email:</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="email"
                                        value={this.state.email}
                                        onChange={this.onChangeEmail}
                                        validations={[required]}
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
                                    <DatePicker selected={this.state.dateOfBirth}
                                                onChange={(newDate) => this.setState({dateOfBirth: newDate})}/>
                                </div>

                                <div className="form-group-btn">
                                    <button className="btnCreateAccount btn-primary">Update account</button>
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
                    </Form>
                </div>
            </div>
        );
    }
}

export default function () {
    const {username} = useParams()
    return <UpdateAccount username={username}/>
};