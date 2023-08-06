import React from "react";
import { Button, Form, Icon, Label, Segment } from "semantic-ui-react";
import FormInput from "../utils/FormInput";
import { Formik } from "formik";
import * as Yup from "yup";

const Login = () => {
  const initialValues = {
    email: "",
    password: "",
  };

  const schema = Yup.object({
    email: Yup.string()
      .required("Email cannot be null")
      .trim()
      .strict(true)
      .email("Invalid email format"),
    password: Yup.string()
      .required("Password cannot be null")
      .trim()
      .strict(true)
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
          onSubmit={(values) => console.log(values)}
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
            <Button color="green" type="submit" style={{ marginTop: "1.5rem" }}>
              Login
            </Button>
          </Form>
        </Formik>
      </Segment>
    </div>
  );
};

export default Login;
