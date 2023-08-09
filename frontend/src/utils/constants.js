export const CUSTOMER = "CUSTOMER";
export const SELLER = "SELLER";

export const orderOptions = [
  { key: "name", text: "Name", value: "name", icon: "sort alphabet down" },
  {
    key: "price",
    text: "Price",
    value: "price",
    icon: "sort numeric ascending",
  },
  {
    key: "createdAt",
    text: "Publish Date",
    value: "createdAt",
    icon: "sort amount down",
  },
  {
    key: "updatedAt",
    text: "Update Date",
    value: "updatedAt",
    icon: "sort amount down",
  },
];

export const sortOptions = [
    {key: "asc", text:"Ascending", value: "asc", icon:"sort up"},
    {key: "desc", text:"Descending", value: "desc", icon:"sort down"},
];
