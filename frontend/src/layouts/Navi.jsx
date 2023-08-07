import React from "react";
import { Input, Menu, Container, Button, Icon } from "semantic-ui-react";
import logo from "../logo.png";
import { Link } from "react-router-dom/";
import { useSelector } from "react-redux";
import MyAccount from "../utils/MyAccount";
import NaviAuthButtons from "../utils/NaviAuthButtons";

const Navi = () => {
  const authState = useSelector((state) => state.auth);
  return (
    <div>
      <Container>
        <Menu inverted>
          <Menu.Item>
            <img src={logo} />
          </Menu.Item>
          <Menu.Item>
            <Icon name="home" />
            <Link to="/">Home</Link>
          </Menu.Item>
          <Menu.Menu position="right">
            <Menu.Item>
              <Input
                style={{ width: "350px" }}
                icon="search"
                placeholder="Search product, category or a brand"
              />
            </Menu.Item>
            {!authState.isLoggedIn ? (
              <NaviAuthButtons />
            ) : (
              <Menu.Item>
                <MyAccount />
              </Menu.Item>
            )}
          </Menu.Menu>
        </Menu>
      </Container>
    </div>
  );
};

export default Navi;
