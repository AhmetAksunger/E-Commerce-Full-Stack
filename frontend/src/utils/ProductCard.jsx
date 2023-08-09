import React from "react";
import { Card, Icon, Image, Label } from "semantic-ui-react";

const ProductCard = ({product}) => {
  return (
    <Card href={`/products/${product.id}`} style={{width:"300px",height:"300px"}}>
      <Card.Content>
        <Image size="tiny" src={product.logo} style={{marginBottom:"1rem"}}/>
        <Card.Header style={{ maxWidth: '100%', whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>{product.name}</Card.Header>
      </Card.Content>
      <Card.Content extra>
        Categories
        {product.categories.map((category,idx) => (
          <p style={{ maxWidth: '100%', whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>{category.name}</p>
        ))}
      </Card.Content>
      <Card.Content extra>
        <Label color="orange" tag>
          {`${product.price}₺`}
        </Label>
      </Card.Content>
    </Card>
  );
};

export default ProductCard;
