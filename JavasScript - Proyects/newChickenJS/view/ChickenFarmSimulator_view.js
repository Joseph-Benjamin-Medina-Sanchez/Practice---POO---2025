const readline = require("readline");
const { Chicken } = require("../model/Chicken");

function ask(question) {
  return new Promise((resolve) => {
    rl.question(question, (answer) => resolve(answer));
  });
}

function calculateAge(birthDate) {
  const today = new Date();
  let age = today.getFullYear() - birthDate.getFullYear();
  const m = today.getMonth() - birthDate.getMonth();
  if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
    age--;
  }
  return age;
}

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
});

const chickens = [
  new Chicken(1, "Lucy", "White and Brown", 2, false),
  new Chicken(2, "Maruja", "White", 1, true),
];

async function main() {
  console.log("This is my Chicken Farm Simulator\n");

  const owner = "Joseph Medina";

  for (const chicken of chickens) {
    console.log("The chicken is --> " + chicken.toString());
    console.log("His owner and friend is --> " + owner);
    chicken.doStuff();
    console.log("------------------------------\n");
  }

  while (true) {
    const addMore = (await ask("Do you want to add a new chicken? (yes/no): ")).toLowerCase();
    if (addMore !== "yes") break;

    const name = await ask("Enter chicken name: ");
    const color = await ask("Enter chicken color: ");
    const isMoltingAnswer = await ask("Is it molting? (yes/no): ");
    const isMolting = isMoltingAnswer.toLowerCase() === "yes";
    const birthStr = await ask("Enter birth date (YYYY-MM-DD): ");
    const birthDate = new Date(birthStr);
    const age = calculateAge(birthDate);

    const id = chickens.length + 1;
    const newChicken = new Chicken(id, name, color, age, isMolting);
    chickens.push(newChicken);

    console.log("\nThe chicken is --> " + newChicken.toString());
    console.log("His owner and friend is --> " + owner);
    newChicken.doStuff();
    console.log("------------------------------\n");
  }

  console.log("Simulation ended.");
  rl.close();
}

main();
