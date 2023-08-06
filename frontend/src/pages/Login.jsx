import React from "react";
import { Button, Icon, Label, Segment } from "semantic-ui-react";
import FormInput from "../utils/FormInput";
import { Form, Formik } from "formik";
import * as Yup from "yup";
import AuthenticationService from "../services/authenticationService";
import { useDispatch } from "react-redux";
import { CUSTOMER } from "../utils/constants";
import { customerLoginSuccess } from "../store/actions/authActions";

const Login = () => {

    const dispatch = useDispatch();

  const handleLogin = async (creds) => {
    let authService = new AuthenticationService();
    try {
      const response = await authService.authenticate(creds);
        if(response.data.userType === CUSTOMER){
            dispatch(customerLoginSuccess(response.data));
        }
    } catch (error) {}
  };

  const initialValues = {
    email: "",
    password: "",
  };

  const schema = Yup.object({
    email: Yup.string()
      .required("Email cannot be null")
      .email("Invalid email format"),
    password: Yup.string()
      .required("Password cannot be null")
      .min(5, "Password must be at least 5 characters long.")
      .matches(/^(?=.*\d).{5,}$/, "Password must contain at least one digit."),
  });

  return (
    <div>
      <Segment raised>
        <Label
          color="blue"
          ribbon
          size="massive"
          style={{ marginRight: "1100px" }}
        >
          <Icon name="user" />
          Login
        </Label>
        <Formik
          initialValues={initialValues}
          validationSchema={schema}
          onSubmit={(values) => handleLogin(values)}
        >
          <Form className="ui form">
            <FormInput
              label="Email"
              placeholder="johndoe@gmail.com"
              fieldName="email"
            />
            <FormInput
              label="Password"
              placeholder="Password"
              fieldName="password"
            />
            <Button color="green" type="submit">Login</Button>
          </Form>
        </Formik>
      </Segment>
    </div>
  );
};

export default Login;
