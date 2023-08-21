import React from "react";
import { useSelector } from "react-redux";
import { Button, Dropdown } from "semantic-ui-react";

const Cart = () => {
    
  const authState = useSelector((state) => state.auth);

  return (
    <Button.Group color="orange">
    <Dropdown
      text="My Cart"
      floating
      labeled
      button
      icon="shopping cart"
      className="icon"
    >
        <Dropdown.Menu>
            <Dropdown.Header>My Cart</Dropdown.Header>
            <Dropdown.Item>a</Dropdown.Item>
        </Dropdown.Menu>
    </Dropdown>
    </Button.Group>
  );
};

export default Cart;
