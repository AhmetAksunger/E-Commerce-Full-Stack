import { ErrorMessage, Field } from "formik";
import React from "react";
import { Label } from "semantic-ui-react";

const FormInput = ({ label, placeHolder, fieldName }) => {
  return (
    <div>
      <Label size="large">{label}</Label>
      <Field name={fieldName} placeholder={placeHolder}/>
      <ErrorMessage
        name={fieldName}
        render={(error) => <Label pointing basic color="red" content={error} />}
      />
    </div>
  );
};

export default FormInput;
