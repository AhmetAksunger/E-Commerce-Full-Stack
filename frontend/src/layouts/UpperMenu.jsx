import React, { useState } from "react";
import { Button, Dropdown, Input, Label, Menu } from "semantic-ui-react";
import { orderOptions, sortOptions } from "../utils/constants";
import { useEffect } from "react";

const UpperMenu = ({ categories }) => {
  const [params, setParams] = useState({
    sort: undefined,
    order: undefined,
    searc: undefined,
    categoryIds: undefined,
    minPrice: undefined,
    maxPrice: undefined,
    page: undefined,
    size: undefined,
  });

  const [categoryOptions, setCategoryOptions] = useState([]);
  const [priceFilterError, setPriceFilterError] = useState(false);

  /**
   * mapping categories to category options
   */
  useEffect(() => {
    const cOptions = categories.map((category, idx) => ({
      key: category.id,
      text: category.name,
      value: category.id,
    }));

    setCategoryOptions(cOptions);
  }, [categories]);

  useEffect(() => {
    if (params.minPrice && params.maxPrice) {
      if (isNaN(params.minPrice) || isNaN(params.maxPrice)) {
        setPriceFilterError(true);
      } else {
        setPriceFilterError(false);
      }
    }
  }, [params.minPrice, params.maxPrice]);
  return (
    <Menu fluid>
      {categories.map((category, idx) => (
        <Menu.Item>
          <Dropdown text={category.name} simple>
            <Dropdown.Menu>
              <Dropdown.Item
                text="Description"
                description={category.description}
              />
            </Dropdown.Menu>
          </Dropdown>
        </Menu.Item>
      ))}
      <Menu.Item position="right">
        <Dropdown
          text="Filter & Sort"
          icon="filter"
          floating
          labeled
          button
          className="icon"
        >
          <Dropdown.Menu style={{ width: "200px" }}>
            <Dropdown.Header icon="tags" content="Filter and Sort" />
            <Dropdown.Item>
              <Dropdown
                placeholder="Order"
                pointing="left"
                selection
                fluid
                options={orderOptions}
                value={null}
                onChange={(event, data) =>
                  setParams((prevParams) => ({
                    ...prevParams,
                    order: data.value,
                  }))
                }
              />
            </Dropdown.Item>
            <Dropdown.Item>
              <Dropdown
                placeholder="Sort"
                pointing="left"
                fluid
                selection
                options={sortOptions}
                value={null}
                onChange={(event, data) =>
                  setParams((prevParams) => ({
                    ...prevParams,
                    sort: data.value,
                  }))
                }
              />
            </Dropdown.Item>
            <Dropdown.Item>
              Min Price
              <Input
                placeholder="0"
                style={{ width: "100px", marginLeft: "0.5rem" }}
                onClick={(event) => event.stopPropagation()}
                onChange={(event) =>
                  setParams((prevParams) => ({
                    ...prevParams,
                    minPrice: event.target.value,
                  }))
                }
                value={null}
              />
            </Dropdown.Item>
            <Dropdown.Item>
              Max Price
              <Input
                placeholder="0"
                style={{ width: "100px", marginLeft: "0.5rem" }}
                onClick={(event) => event.stopPropagation()}
                onChange={(event) =>
                  setParams((prevParams) => ({
                    ...prevParams,
                    maxPrice: event.target.value,
                  }))
                }
                value={null}
              />
            </Dropdown.Item>
            {priceFilterError && (
              <div style={{ textAlign: "center" }}>
                <Label pointing="above" color="red" basic>
                  Enter a numeric value
                </Label>
              </div>
            )}
            <Dropdown.Item>
              <Dropdown
                placeholder="Categories"
                pointing="left"
                fluid
                multiple
                selection
                options={categoryOptions}
                value={null}
                onChange={(event, data) =>
                  setParams((prevParams) => ({
                    ...prevParams,
                    categoryIds: data.value,
                  }))
                }
              />
            </Dropdown.Item>
            <Dropdown.Divider />
            <div style={{ textAlign: "center", marginBottom: "0.5rem" }}>
              <Button
                basic
                color="green"
                disabled={priceFilterError}
                onClick={() => null} // get products
              >
                Apply
              </Button>
              <Button basic color="red" onClick={null}>
                Reset
              </Button>
            </div>
          </Dropdown.Menu>
        </Dropdown>
      </Menu.Item>
    </Menu>
  );
};

export default UpperMenu;
