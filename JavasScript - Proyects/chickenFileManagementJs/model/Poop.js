class Poop {
  /**
   * @param {number} amount
   */
  constructor(amount) {
    this.amount = amount;
  }

  toString() {
    return `Poop{ amount = ${this.amount} }`;
  }
}

module.exports = { Poop };
