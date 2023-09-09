import React, { useState } from "react";
import { Button, Icon, Label, Segment } from "semantic-ui-react";
import FormInput from "../utils/FormInput";
import { Form, Formik } from "formik";
import * as Yup from "yup";
import AuthenticationService from "../services/authenticationService";
import { useDispatch } from "react-redux";
import { loginSuccess } from "../store/actions/authActions";
import { useHistory } from 'react-router-dom';

const Login = () => {

  const dispatch = useDispatch();
  const history = useHistory();

  const [isUnauthorized, setIsUnauthorized] = useState(false);

  const handleLogin = async (creds) => {
    let authService = new AuthenticationService();
    try {
      const response = await authService.authenticate(creds);
      dispatch(loginSuccess(response.data.response));
      history.push("/");
    } catch (error) {
      setIsUnauthorized(true);
    }
  };

  const initialValues = {
    email: "",
    password: "",
  };

  const schema = Yup.object({
    email: Yup.string()
      .required("Email cannot be null")
      .email("Invalid email format"),
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
            {isUnauthorized && <div style={{marginTop:"1rem"}}><Label basic color="red">Incorrect email or password</Label></div>}
            <Button color="green" type="submit" style={{marginTop:"1rem"}}>
              Login
            </Button>
          </Form>
        </Formik>
      </Segment>
    </div>
  );
};

export default Login;
