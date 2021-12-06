import axios from "axios";

const API_URL = "http://localhost:8080/";

let config = {
  headers: {
    'Content-Type': 'application/json',
  }
}

class AuthService {

  logout() {
    localStorage.removeItem("user");
  }

  register(name, username, email, password, phoneNumber, address, dateOfBirth) {
    console.log("calling /addAccount");
    let data = {
      "name": name,
      "email": email,
      "username": username,
      "password": password,
      "phoneNumber": phoneNumber,
      "address": address,
      "dateOfBirth": dateOfBirth,
    }
    return axios.post(API_URL + "addAccount", data, config);
  }

  getCurrentUser() {
    return JSON.parse(localStorage.getItem('user'));;
  }
}

export default new AuthService();