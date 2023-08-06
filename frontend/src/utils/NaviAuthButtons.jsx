import React from "react";
import { Button, Menu } from "semantic-ui-react";
import { Link } from "react-router-dom/";

const NaviAuthButtons = () => {
  return (
    <>
      <Menu.Item>
        <Button secondary>
          <Link to="/sign-up">Sign up</Link>
        </Button>
      </Menu.Item>
      <Menu.Item>
        <Button>
          <Link to="/login">Log-in</Link>
        </Button>
      </Menu.Item>
    </>
  );
};

export default NaviAuthButtons;
