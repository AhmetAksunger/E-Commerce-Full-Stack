import React from "react";
import { Grid, Pagination, Segment } from "semantic-ui-react";
import ProductCard from "./ProductCard";
import { useState } from "react";
import { useEffect } from "react";
import { useSelector } from "react-redux";

const ProductList = ({ products, onPageChange }) => {
  const [splittedProductContents, setSplittedProductContents] = useState([
    [],
    [],
    [],
  ]);

  const filters = useSelector((state) => state.filter);

  const [activePage, setActivePage] = useState(1);

  const splitProducts = () => {
    const newSplittedProductContents = [[], [], []];
    let j = 0;
    for (let i = 0; i < products.content.length; i++) {
      if (i !== 0 && i % 3 === 0) {
        j++;
      }
      newSplittedProductContents[j].push(products.content[i]);
    }
    setSplittedProductContents(newSplittedProductContents);
  };

  useEffect(() => {
    splitProducts();
  }, [products]);

  useEffect(() => {
    setActivePage(1);
  },[filters])

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
        onPageChange={(event, {activePage}) => {setActivePage(activePage); onPageChange(activePage)}}
        activePage={activePage}
      />
    </Segment>
  );
};

export default ProductList;
