import React from "react";
import { Dropdown, Icon, Label } from "semantic-ui-react";

const MyAccount = () => {
  return (
    <Label size="large" color="light blue">
      <Icon name="user circle" />
      <Dropdown floating text="My Account">
        <Dropdown.Menu>
          <Dropdown.Item text="My Orders" />
          <Dropdown.Item text="Account Details" />
          <Dropdown.Divider />
          <Dropdown.Item>
            <Icon name="sign-out"/>
            Logout
          </Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
    </Label>
  );
};

export default MyAccount;
