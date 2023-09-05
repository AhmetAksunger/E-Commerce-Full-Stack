export default class CategoryEditor {

  VISIBLE_CATEGORIES_IN_MENU = 0;
  categories = [];

  /**
   * 
   * @param {Array} categories Categories array 
   * @param {Number} VISIBLE_CATEGORIES_IN_MENU Visible category count in the menu 
   */
  constructor(categories,VISIBLE_CATEGORIES_IN_MENU = 5) {
    this.categories = categories;
    this.VISIBLE_CATEGORIES_IN_MENU = VISIBLE_CATEGORIES_IN_MENU;
}

  /**
   * Converts the categories to category options
   * @returns categoryOptions
   */
  categoryOptionsCreator() {
    return this.categories.map((category, idx) => ({
      key: category.id,
      text: category.name,
      value: category.id,
    }));
  }

  /**
   * Slices categories based on the specified {@link VISIBLE_CATEGORIES_IN_MENU} in instance
   * 
   * @returns An object of arrays, including the sliced lists.
   */
  categorySlicer() {
    const firstList = this.categories.slice(0,this.VISIBLE_CATEGORIES_IN_MENU);
    const remainingList = this.categories.slice(this.VISIBLE_CATEGORIES_IN_MENU);
    return {
        firstList,
        remainingList
    };
  }
}
