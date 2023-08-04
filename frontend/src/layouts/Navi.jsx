import React from "react";
import { Input, Menu, Container, Button } from "semantic-ui-react";
import logo from "../logo.png";
import { Link } from "react-router-dom/";

const Navi = () => {
  return (
    <div>
      <Container>
        <Menu inverted>
          <Menu.Item>
            <img src={logo} />
          </Menu.Item>
          <Menu.Item name="home" />
          <Menu.Menu position="right">
            <Menu.Item>
              <Input
                style={{ width: "350px" }}
                icon="search"
                placeholder="Search product, category or a brand"
              />
            </Menu.Item>
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
          </Menu.Menu>
        </Menu>
      </Container>
    </div>
  );
};

export default Navi;
