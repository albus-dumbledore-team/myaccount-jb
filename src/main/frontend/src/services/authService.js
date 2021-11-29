import axios from "axios";

const API_URL = "http://localhost:8080/";

class AuthService {
  login(username, password) {
    return axios
      .post(API_URL + "signin", {
        username,
        password
      })
      .then(response => {
        if (response.data.accessToken) {
          localStorage.setItem("user", JSON.stringify(response.data));
        }

        return response.data;
      });
  }

  logout() {
    localStorage.removeItem("user");
  }

  register(name, username, email, password, phoneNumber, address, dateOfBirth) {
    console.log("calling /addAccount");
    return axios.post(API_URL + "addAccount", {
      name,
      email,
      username,
      password,
      phoneNumber,
      address,
      dateOfBirth,
    },
    {
      headers: {
        // Overwrite Axios's automatically set Content-Type
        'Content-Type': 'application/json'
      }
    })
    .then( response => {
        //localStorage.setItem("user", JSON.stringify(response.data));
        console.log(response.data);
    });
  }

  getCurrentUser() {
    return JSON.parse(localStorage.getItem('user'));;
  }
}

export default new AuthService();