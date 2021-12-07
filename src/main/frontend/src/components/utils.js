export const required = value => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};

export const vname = value => {
    if (value.length < 3 || value.length > 20 || !/^[a-zA-Z ]+$/.test(value)) {
        return (
            <div className="alert alert-danger" role="alert">
                The name must contain at least 3 letters
            </div>
        );
    }
};

export const vusername = value => {
    if (value.length < 3 || value.length > 20) {
        return (
            <div className="alert alert-danger" role="alert">
                The username must be between 3 and 20 characters.
            </div>
        );
    }
};

export const vpassword = value => {
    if (value.length < 6 || value.length > 40) {
        return (
            <div className="alert alert-danger" role="alert">
                The password must be between 6 and 40 characters.
            </div>
        );
    }
};

export const vphonenumber = value => {
    if (!/^07[[0-9]{8}$/.test(value)) {
        return (
            <div className="alert alert-danger" role="alert">
                Invalid phone number.
            </div>
        );
    }
};

export const vaddress = value => {
    if (value.length < 3 || !/^[a-zA-Z0-9. ]{3,}$/.test(value)) {
        return (
            <div className="alert alert-danger" role="alert">
                Invalid address
            </div>
        );
    }
};
