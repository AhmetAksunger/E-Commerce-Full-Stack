import Lottie from "lottie-react";
import React from "react";
import animationData from "../assets/CartEmpty.json";
import { Button, Divider, Header, Icon } from "semantic-ui-react";
import { Link } from "react-router-dom/cjs/react-router-dom.min";

export default function CartEmpty() {
  return (
    <>
      <Header as="h2" textAlign="left">
        <Icon name="shopping cart" />
        <Header.Content>{`My Cart (0 Products)`}</Header.Content>
      </Header>
      <Divider/>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <Lottie animationData={animationData} style={{ width: "600px" }} />
      </div>
      <Button size="massive" color="orange" as={Link} to="/">Start Shopping!</Button>
    </>
  );
}
