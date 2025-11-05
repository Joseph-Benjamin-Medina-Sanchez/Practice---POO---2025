const fs = require("fs");
const readline = require("readline");

const FARM_FILE_PATH = "./farm.json";

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
});

function ask(question) {
  return new Promise(resolve => rl.question(question, resolve));
}

let coops = [];
let farmerName = "Farmer";

function loadData() {
  if (fs.existsSync(FARM_FILE_PATH)) {
    const data = fs.readFileSync(FARM_FILE_PATH, "utf-8");
    coops = JSON.parse(data);
    console.log("Loaded existing farm data.");
  } else {
    console.log("No data found. Creating new farm file...");
    coops = [];
    saveData();
  }
}

function saveData() {
  fs.writeFileSync(FARM_FILE_PATH, JSON.stringify(coops, null, 2));
}

function printMenu() {
  console.log(`\n--- ${farmerName}'s Chicken Farm Simulator ---`);
  console.log("1. List all coops and chickens");
  console.log("2. Add a new chicken");
  console.log("3. Add a new coop");
  console.log("4. Run farm simulation");
  console.log("5. Edit a chicken");
  console.log("6. Delete a chicken");
  console.log("7. Exit");
}

async function listCoopsAndChickens() {
  console.log("\n--- Farm Status ---");
  if (coops.length === 0) {
    console.log("There are no coops on this farm.");
    return;
  }

  for (const coop of coops) {
    console.log(`\n ${coop.location} (ID: ${coop.id}) contains:`);
    if (!coop.chickens || coop.chickens.length === 0) {
      console.log(" - (Empty)");
    } else {
      for (const chicken of coop.chickens) {
        console.log(` - ${chicken.name} (ID: ${chicken.id})`);
      }
    }
    console.log("------------------------------------");
  }
}

async function addCoop() {
  console.log("\n--- Add New Coop ---");
  const id = parseInt(await ask("Enter coop ID: "));
  if (coops.some(c => c.id === id)) {
    console.log("Error: Coop with this ID already exists.");
    return;
  }
  const location = await ask("Enter coop location: ");

  const newCoop = { id, location, chickens: [] };
  coops.push(newCoop);
  saveData();
  console.log(`Successfully added coop at ${location}!`);
}

async function addChicken() {
  console.log("\n--- Add New Chicken ---");
  if (coops.length === 0) {
    console.log("You must add a coop first!");
    return;
  }

  const id = parseInt(await ask("Enter chicken ID: "));
  if (coops.some(c => c.chickens.some(ch => ch.id === id))) {
    console.log("Error: A chicken with this ID already exists.");
    return;
  }

  const name = await ask("Enter chicken name: ");
  const color = await ask("Enter chicken color: ");
  const age = parseInt(await ask("Enter chicken age: "));
  const isMoltingInput = await ask("Is the chicken molting? (true/false): ");
  const isMolting = isMoltingInput.toLowerCase() === "true";

  console.log("Available coops:");
  coops.forEach(c => console.log(` - ID ${c.id}: ${c.location}`));
  const coopId = parseInt(await ask("Enter the ID of the coop to add this chicken to: "));
  const coop = coops.find(c => c.id === coopId);

  if (!coop) {
    console.log("Error: Coop not found.");
    return;
  }

  const newChicken = { id, name, color, age, isMolting };
  coop.chickens.push(newChicken);
  saveData();
  console.log(`Successfully added ${name} to coop ${coop.location}!`);
}

function runSimulation() {
  console.log("\n--- Running Farm Simulation ---");
  for (const coop of coops) {
    for (const chicken of coop.chickens) {
      console.log(`Chicken ${chicken.name} (ID ${chicken.id}) in ${coop.location} is clucking, eating, and wandering.`);
    }
  }
  console.log("Simulation finished successfully.");
}

async function editChicken() {
  console.log("\n--- Edit Chicken ---");
  if (coops.length === 0) {
    console.log("No coops available.");
    return;
  }

  await listCoopsAndChickens();
  const chickenId = parseInt(await ask("Enter the ID of the chicken to edit: "));

  const coop = coops.find(c => c.chickens.some(ch => ch.id === chickenId));
  if (!coop) {
    console.log("Chicken not found.");
    return;
  }

  const chicken = coop.chickens.find(ch => ch.id === chickenId);

  const newName = await ask(`Enter new name (${chicken.name}): `) || chicken.name;
  const newColor = await ask(`Enter new color (${chicken.color}): `) || chicken.color;
  const newAgeInput = await ask(`Enter new age (${chicken.age}): `);
  const newAge = newAgeInput ? parseInt(newAgeInput) : chicken.age;
  const newMoltingInput = await ask(`Is molting? (${chicken.isMolting}) (true/false): `);
  const newMolting = newMoltingInput ? newMoltingInput.toLowerCase() === "true" : chicken.isMolting;

  chicken.name = newName;
  chicken.color = newColor;
  chicken.age = newAge;
  chicken.isMolting = newMolting;

  saveData();
  console.log("Chicken updated successfully.");
}

async function deleteChicken() {
  console.log("\n--- Delete Chicken ---");
  if (coops.length === 0) {
    console.log("No coops available.");
    return;
  }

  await listCoopsAndChickens();
  const chickenId = parseInt(await ask("Enter the ID of the chicken to delete: "));

  const coop = coops.find(c => c.chickens.some(ch => ch.id === chickenId));
  if (!coop) {
    console.log("Chicken not found.");
    return;
  }

  coop.chickens = coop.chickens.filter(ch => ch.id !== chickenId);
  saveData();
  console.log("Chicken deleted successfully.");
}

async function main() {
  console.log("Welcome to the Chicken Farm Simulator");
  farmerName = await ask("Please enter the farmer's name: ");
  loadData();

  let running = true;
  while (running) {
    printMenu();
    const choice = await ask("Enter your choice (1-7): ");

    switch (choice) {
      case "1":
        await listCoopsAndChickens();
        break;
      case "2":
        await addChicken();
        break;
      case "3":
        await addCoop();
        break;
      case "4":
        runSimulation();
        break;
      case "5":
        await editChicken();
        break;
      case "6":
        await deleteChicken();
        break;
      case "7":
        running = false;
        console.log("Saving data and exiting. Goodbye!");
        break;
      default:
        console.log("Invalid choice. Please enter a number between 1 and 7.");
    }
  }

  rl.close();
}

main();
