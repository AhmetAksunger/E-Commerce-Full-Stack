import React from "react";
import { Grid, Pagination, Segment } from "semantic-ui-react";
import ProductCard from "./ProductCard";

const ProductList = ({products,splittedProductContents,onPageChange}) => {
  return (
    <Segment raised>
      <Grid columns={3}>
        <Grid.Row>
          {splittedProductContents[0].map((product, idx) => (
            <Grid.Column>
              <ProductCard product={product} />
            </Grid.Column>
          ))}
        </Grid.Row>
        <Grid.Row>
          {splittedProductContents[1].map((product, idx) => (
            <Grid.Column>
              <ProductCard product={product} />
            </Grid.Column>
          ))}
        </Grid.Row>
        <Grid.Row>
          {splittedProductContents[2].map((product, idx) => (
            <Grid.Column>
              <ProductCard product={product} />
            </Grid.Column>
          ))}
        </Grid.Row>
      </Grid>
      <Pagination
        style={{ marginTop: "1rem" }}
        boundaryRange={0}
        defaultActivePage={1}
        ellipsisItem={null}
        firstItem={null}
        lastItem={null}
        siblingRange={1}
        totalPages={products.totalPages}
        onPageChange={onPageChange}
      />
    </Segment>
  );
};

export default ProductList;
