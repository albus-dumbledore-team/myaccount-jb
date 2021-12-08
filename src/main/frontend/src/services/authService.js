import axios from "axios";

const API_URL = "http://localhost:8080/";

let config = {
    headers: {
        'Content-Type': 'application/json',
        "Access-Control-Allow-Origin": "http://localhost:3000"
    }
}

const API = axios.create({
    baseURL: "http://localhost:8080",
    // withCredentials: true
})

function formatDate(date) {
    return ((date.getMonth() > 8) ?
            (date.getMonth() + 1) : ('0' + (date.getMonth() + 1)))
        + '-' + ((date.getDate() > 9) ?
            date.getDate() : ('0' + date.getDate()))
        + '-' + date.getFullYear();
}

class AuthService {

    viewAccount(email) {
        return API.get("viewAccount/" + email, {
            headers: {
                "Access-Control-Allow-Origin": "http://localhost:3000"
            }
        });
    }

    updateAccount(email, {name, phoneNumber, address, dateOfBirth}) {
        let data = {
            "name": name,
            "email": email,
            "phoneNumber": phoneNumber,
            "address": address,
            "dateOfBirth": formatDate(dateOfBirth),
        }
        return API.put(`editAccount`, data, config);
    }

    logout() {
        localStorage.removeItem("user");
    }

    register(name, email, password, phoneNumber, address, dateOfBirth) {
        console.log("calling /addAccount");
        let date = formatDate(dateOfBirth);
        console.log(date);
        let data = {
            "name": name,
            "email": email,
            "password": password,
            "phoneNumber": phoneNumber,
            "address": address,
            "dateOfBirth": date,
        }
        return axios.post(API_URL + "addAccount", data, config);
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));
    }
}

export default new AuthService();