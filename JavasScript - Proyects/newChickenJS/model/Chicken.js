/**
 * Chicken class
 * Represents a chicken with its actions and properties.
 */

const { Poop } = require("./Poop");
const { Egg } = require("./Egg");

class Chicken {
  /**
   * @param {number} id - Chicken ID
   * @param {string} name - Chicken name
   * @param {string} color - Chicken color
   * @param {number} age - Chicken age (in years)
   * @param {boolean} isMolting - True if the chicken is molting
   */
  constructor(id, name, color, age, isMolting) {
    this.id = id;
    this.name = name;
    this.color = color;
    this.age = age;
    this.isMolting = isMolting;
  }

  cluck() {
    console.log("chicken " + this.name + " is clucking, cluck cluck cluck");
  }

  eat() {
    console.log("chicken " + this.name + " is eating, grains");
  }

  wander() {
    console.log("chicken " + this.name + " is wandering");
  }

  drink() {
    console.log("chicken " + this.name + " is drinking");
  }

  /**
   * Chicken poops a certain amount
   * @param {number} amount - poop quantity
   */
  poop(amount) {
    const poop = new Poop(amount);
    console.log("chicken " + this.name + " is pooping a " + poop.toString());
    return poop;
  }

  /**
   * Chicken lays an egg of a certain size
   * @param {string} size - size of the egg ('S', 'M', 'L')
   */
  layAnEgg(size) {
    const egg = new Egg(size);
    console.log("chicken " + this.name + " is laying a " + egg.getSize() + " size egg");
    return egg;
  }

  doStuff() {
    this.cluck();
    this.eat();
    this.cluck();
    this.poop(2);
    this.poop(3);
    this.eat();
    this.wander();
    this.drink();
    this.layAnEgg('M');
    this.layAnEgg('L');
  }
  toString() {
    return (
      "\nChicken{" +
      "\nid -> \t\t" + this.id +
      ", \nname -> \t" + this.name +
      ", \ncolor -> \t" + this.color +
      ", \nage -> \t\t" + this.age +
      ", \nisMolting -> \t" + this.isMolting +
      "\n}"
    );
  }
}
module.exports = { Chicken };
