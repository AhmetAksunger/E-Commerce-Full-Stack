import React from "react";
import { Button, Menu } from "semantic-ui-react";
import { Link } from "react-router-dom/";
import { useHistory } from 'react-router-dom';

const NaviAuthButtons = () => {

  const history = useHistory();

  return (
    <>
      <Menu.Item>
        <Button secondary onClick={() => history.push("/sign-up")}>
          Sign up
        </Button>
      </Menu.Item>
      <Menu.Item>
        <Button onClick={() => history.push("/login")}>
          Log-in
        </Button>
      </Menu.Item>
    </>
  );
};

export default NaviAuthButtons;
