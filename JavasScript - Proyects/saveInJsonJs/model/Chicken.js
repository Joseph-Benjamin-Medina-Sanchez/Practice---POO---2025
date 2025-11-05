const { Egg } = require("./Egg");
const { Poop } = require("./Poop");

class Chicken {
  constructor(id, name, color, age, isMolting, actions = []) {
    this.id = id;
    this.name = name;
    this.color = color;
    this.age = age;
    this.isMolting = isMolting;
    this.actions = actions; // historial de acciones
  }

  cluck() {
    console.log(`chicken ${this.name} is clucking, cluck cluck cluck`);
  }

  eat() {
    console.log(`chicken ${this.name} is eating grains`);
  }

  wander() {
    console.log(`chicken ${this.name} is wandering`);
  }

  drink() {
    console.log(`chicken ${this.name} is drinking water`);
  }

  poop(amount) {
    const poop = new Poop(amount);
    console.log(`chicken ${this.name} is pooping a ${poop.toString()}`);
    this.actions.push({ type: "poop", amount }); // ✅ sin fecha
    return poop;
  }

  layAnEgg(size) {
    const egg = new Egg(size);
    console.log(`chicken ${this.name} is laying a ${egg.getSize()} size egg`);
    this.actions.push({ type: "egg", size }); // ✅ sin fecha
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
    this.layAnEgg("M");
    this.layAnEgg("L");
  }

  toString() {
    return `
Chicken{
  id -> \t\t${this.id},
  name -> \t${this.name},
  color -> \t${this.color},
  age -> \t\t${this.age},
  isMolting -> \t${this.isMolting}
}`;
  }
}

module.exports = { Chicken };
