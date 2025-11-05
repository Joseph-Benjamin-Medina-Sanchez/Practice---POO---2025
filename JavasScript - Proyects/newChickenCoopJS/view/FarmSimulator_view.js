const { Chicken } = require("../model/Chicken");
const { ChickenCoop } = require("../model/ChickenCoop");

console.log(" Chicken Coop Farm Simulator \n");

const owner = "Joseph Medina";

const chickenCoops = [
  new ChickenCoop(1, "Coop #1 "),
  new ChickenCoop(2, "Coop #2 "),
];

const chickens = [
  new Chicken(1, "Lucy", "White", 2, false),
  new Chicken(2, "Maruja", "Brown", 1, true),
  new Chicken(3, "Clara", "Golden", 3, false),
  new Chicken(4, "Tina", "Black", 1, false),
  new Chicken(5, "Nina", "Gray", 2, true),
  new Chicken(6, "Sofi", "White", 4, false),
  new Chicken(7, "Luna", "Golden", 3, true),
  new Chicken(8, "Mimi", "Black", 1, false),
  new Chicken(9, "Rita", "Brown", 2, false),
  new Chicken(10, "Coco", "White", 1, true),
];

for (let i = 0; i < 5; i++) {
  chickenCoops[0].addChicken(chickens[i]);
}
for (let i = 5; i < chickens.length; i++) {
  chickenCoops[1].addChicken(chickens[i]);
}

for (const coop of chickenCoops) {
  console.log(`\n ${coop.name} contains:`);
  for (const chicken of coop.getChickens()) {
    console.log(` - ${chicken.name}`);
  }
  console.log("------------------------------------");
}

console.log("\n All chickens in the farm are now doing their routines:\n");

for (const chicken of chickens) {
  console.log(`The chicken is --> ${chicken.toString()}`);
  console.log(`His owner and friend is --> ${owner}`);
  chicken.doStuff();
  console.log("------------------------------\n");
}

console.log(" Simulation finished successfully.");
