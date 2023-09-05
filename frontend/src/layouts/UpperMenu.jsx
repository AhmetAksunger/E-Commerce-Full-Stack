import React, { useState } from "react";
import { Button, Dropdown, Input, Label, Menu } from "semantic-ui-react";
import { directionOptions, sortOptions } from "../utils/constants";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import {
  addProductFilters,
  resetProductFilters,
} from "../store/actions/filterActions";
import { Link } from "react-router-dom/cjs/react-router-dom.min";
import CategoryEditor from "../utils/CategoryEditor";

const UpperMenu = ({ categories }) => {
  const categoryEditor = new CategoryEditor(categories, 6);

  const dispatch = useDispatch();

  const [filters, setFilters] = useState({
    search: "",
    categoryIds: [],
    minPrice: "",
    maxPrice: "",
    sort: null,
    direction: null,
  });

  const [categoryOptions, setCategoryOptions] = useState([]);
  const [visibleCategories, setVisibleCategories] = useState([]);
  const [remainingCategories, setRemainingCategories] = useState([]);
  const [priceFilterError, setPriceFilterError] = useState(false);

  /**
   * mapping categories to category options
   */
  useEffect(() => {
    setCategoryOptions(categoryEditor.categoryOptionsCreator());
    setVisibleCategories(categoryEditor.categorySlicer().firstList);
    setRemainingCategories(categoryEditor.categorySlicer().remainingList);
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
      search: "",
      categoryIds: [],
      minPrice: "",
      maxPrice: "",
      sort: "name",
      direction: "ASC",
    });
  };

  return (
    <Menu fluid>
      {visibleCategories.map((category, idx) => (
        <Menu.Item as={Link} to={`/category/${category.id}`}>
          {category.name}
        </Menu.Item>
      ))}
      <Menu.Item>
        <Dropdown text="All Categories" style={{color:"teal"}}>
          <Dropdown.Menu>
            <Dropdown.Header>All Categories</Dropdown.Header>
            {remainingCategories.map((category,idx) => (
              <Dropdown.Item as={Link} to={`/category/${category.id}`}>{category.name}</Dropdown.Item>
            ))}
          </Dropdown.Menu>
        </Dropdown>
      </Menu.Item>
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
                placeholder="Sort"
                pointing="left"
                selection
                fluid
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
              <Dropdown
                placeholder="Direction"
                pointing="left"
                fluid
                selection
                options={directionOptions}
                value={filters.direction}
                onChange={(event, data) =>
                  setFilters((prevFilters) => ({
                    ...prevFilters,
                    direction: data.value,
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
