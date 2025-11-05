/**
 * Egg class
 * Represents an egg laid by a chicken.
 */
class Egg {
  /**
   * @param {string} size
   */
  constructor(size) {
    this.size = size;
  }

  toString() {
    return "Egg{ size = " + this.size + " }";
  }

  getSize() {
    return this.size;
  }
}

module.exports = { Egg };
