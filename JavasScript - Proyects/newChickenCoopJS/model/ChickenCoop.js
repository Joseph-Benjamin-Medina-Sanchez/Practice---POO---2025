/**
 * ChickenCoop class
 * Represents a coop containing multiple chickens.
 */
class ChickenCoop {
  constructor(id, name) {
    this.id = id;
    this.name = name;
    this.chickens = []; 
  }

  addChicken(chicken) {
    this.chickens.push(chicken);
  }

  getChickens() {
    return this.chickens;
  }

  toString() {
    const chickensInfo = this.chickens.map(ch => ch.toString()).join("\n");
    return `\nChickenCoop{ id -> ${this.id}, name -> ${this.name}, chickens -> [${chickensInfo}] }`;
  }
}

module.exports = { ChickenCoop };
