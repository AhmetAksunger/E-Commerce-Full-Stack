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

export const defaultProduct = {
  "id": 0,
  "name": "",
  "price": 0.0,
  "quantity": 0,
  "orderCount": 0,
  "logo": "",
  "createdAt": 0,
  "updatedAt": null,
  "seller": {
      "sellerId": 0,
      "companyName": "",
      "contactNumber": "",
      "logo": ""
  },
  "categories": [
      {
          "id": 0,
          "name": "",
          "description": ""
      },
      {
          "id": 0,
          "name": "",
          "description": ""
      }
  ]
};