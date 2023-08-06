import { Form, Formik } from "formik";
import React from "react";
import { Button, Icon, Label, Segment } from "semantic-ui-react";
import * as Yup from "yup";
import FormInput from "../utils/FormInput";
import AuthenticationService from "../services/authenticationService";
import {useDispatch} from "react-redux"; 
import { customerLoginSuccess } from "../store/actions/authActions";
const CustomerSignUp = () => {

  const dispatch = useDispatch();

  const handleSignUp = async (creds) => {
    let authService = new AuthenticationService();
    try {
      const response = await authService.registerCustomer(creds);
      console.log(response);
      dispatch(customerLoginSuccess(response.data));
    } catch (error) {
      
    }
  }

  var phoneRegEx =
    /(^[0\s]?[\s]?)([(]?)([5])([0-9]{2})([)]?)([\s]?)([0-9]{3})([\s]?)([0-9]{2})([\s]?)([0-9]{2})$/g;

  const initialValues = {
    email: "",
    password: "",
    fullName: "",
    phoneNumber: "",
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
    fullName: Yup.string()
      .required("Full name cannot be null")
      .trim()
      .strict(true)
      .min(3, "Full name must be at least 3 characters long.")
      .max(50, "Full name must not exceed 50 characters."),
    phoneNumber: Yup.string().matches(phoneRegEx, "Phone number is not valid"),
  });

  return (
    <div>
      <Segment raised>
        <Label color="red" ribbon size="massive" style={{marginRight:"1100px"}}>
          <Icon name="shopping cart"/>
          Start Shopping with Us!
        </Label>
        <Formik
          initialValues={initialValues}
          validationSchema={schema}
          onSubmit={(values) => handleSignUp(values)}
        >
          <Form className="ui form">
            <FormInput
              label="Email"
              placeholder="johndoe@gmail.com"
              fieldName="email"
            />
            <div>
              <FormInput
                label="Password"
                placeholder="Password"
                fieldName="password"
              />
            </div>
            <FormInput
              label="Full Name"
              placeholder="John Doe"
              fieldName="fullName"
            />
            <FormInput
              label="Phone Number"
              placeholder="0533 333 33 33"
              fieldName="phoneNumber"
            />
            <Button color="green" type="submit" style={{ marginTop: "1.5rem" }}>
              Sign up!
            </Button>
          </Form>
        </Formik>
      </Segment>
    </div>
  );
};

export default CustomerSignUp;
