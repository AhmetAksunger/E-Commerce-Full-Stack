import React from "react";
import { useDispatch } from "react-redux";
import { Button, Dropdown, Icon, Label } from "semantic-ui-react";
import { logoutSuccess } from "../store/actions/authActions";

const MyAccount = () => {

  const dispatch = useDispatch();

  return (
    <Label size="large" color="light blue">
      <Icon name="user circle" />
      <Dropdown floating text="My Account">
        <Dropdown.Menu>
          <Dropdown.Item text="My Orders" />
          <Dropdown.Item text="Account Details" />
          <Dropdown.Divider />
          <Dropdown.Item>
            <Button basic color="red" onClick={() => dispatch(logoutSuccess())}>
              <Icon name="sign-out" />
              Logout
            </Button>
          </Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
    </Label>
  );
};

export default MyAccount;
