import React, { useState } from "react";
import { Button, Dropdown, Input, Label, Menu } from "semantic-ui-react";
import { orderOptions, sortOptions } from "../utils/constants";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { addProductFilters, resetProductFilters } from "../store/actions/filterActions";

const UpperMenu = ({ categories }) => {

  const dispatch = useDispatch();

  const [filters, setFilters] = useState({
    sort: "",
    order: "",
    search: "",
    categoryIds: "",
    minPrice: "",
    maxPrice: "",
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
    if (filters.minPrice && filters.maxPrice) {
      if (isNaN(filters.minPrice) || isNaN(filters.maxPrice)) {
        setPriceFilterError(true);
      } else {
        setPriceFilterError(false);
      }
    }
  }, [filters.minPrice, filters.maxPrice]);

  const onClickResetFilters = () => {
    dispatch(resetProductFilters());
    setFilters({    
      sort: "",
      order: "",
      searc: "",
      categoryIds: "",
      minPrice: "",
      maxPrice: "",
    });
  }

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
                value={filters.order}
                onChange={(event, data) =>
                  setFilters((prevFilters) => ({
                    ...prevFilters,
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
                value={filters.sort}
                onChange={(event, data) =>
                  setFilters((prevFilters) => ({
                    ...prevFilters,
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
                  setFilters((prevFilters) => ({
                    ...prevFilters,
                    minPrice: event.target.value,
                  }))
                }
                value={filters.minPrice}
              />
            </Dropdown.Item>
            <Dropdown.Item>
              Max Price
              <Input
                placeholder="0"
                style={{ width: "100px", marginLeft: "0.5rem" }}
                onClick={(event) => event.stopPropagation()}
                onChange={(event) =>
                  setFilters((prevFilters) => ({
                    ...prevFilters,
                    maxPrice: event.target.value,
                  }))
                }
                value={filters.maxPrice}
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
                value={filters.categoryIds}
                onChange={(event, data) =>
                  setFilters((prevFilters) => ({
                    ...prevFilters,
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
                onClick={() => dispatch(addProductFilters(filters))}
              >
                Apply
              </Button>
              <Button basic color="red" onClick={onClickResetFilters}>
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
