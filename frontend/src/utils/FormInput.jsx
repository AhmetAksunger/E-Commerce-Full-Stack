import { ErrorMessage, Field } from "formik";
import React from "react";
import { Label } from "semantic-ui-react";

const FormInput = ({ label, placeholder, fieldName }) => {
  return (
    <div>
      <Label size="large" style={{marginTop:"2rem"}}>{label}</Label>
      <Field name={fieldName} placeholder={placeholder}/>
      <ErrorMessage
        name={fieldName}
        render={(error) => <Label pointing basic color="red" content={error} />}
      />
    </div>
  );
};

export default FormInput;
