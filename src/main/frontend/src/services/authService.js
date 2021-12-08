import axios from "axios";

const API_URL = "http://localhost:8080/";

let config = {
  headers: {
    'Content-Type': 'application/json',
  }
}


class AuthService {

  viewAccount(email){
    return axios.get(API_URL+"viewAccount/"+email);
  }

  logout() {
    localStorage.removeItem("user");
  }

  register(name, email, password, phoneNumber, address, dateOfBirth) {
    console.log("calling /addAccount");
    let date = ((dateOfBirth.getMonth() > 8) ?
        (dateOfBirth.getMonth() + 1) : ('0' + (dateOfBirth.getMonth() + 1)))
        + '-' + ((dateOfBirth.getDate() > 9) ?
            dateOfBirth.getDate() : ('0' + dateOfBirth.getDate()))
        + '-' + dateOfBirth.getFullYear();

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
    return JSON.parse(localStorage.getItem('user'));;
  }
}

export default new AuthService();