const fs = require("fs");
const { Chicken } = require("./Chicken");
const { ChickenCoop } = require("./ChickenCoop");

const CHICKENS_FILE_PATH = "./chickens.json";
const COOPS_FILE_PATH = "./coops.json";

function loadData() {
  const coops = loadCoops();
  const chickens = loadChickens();

  const chickenMap = new Map(chickens.map(ch => [ch.id, ch]));
  coops.forEach(coop => {
    const chickenIds = coop.chickens || [];
    coop.chickens = chickenIds.map(id => chickenMap.get(id)).filter(ch => ch);
  });

  return { coops, chickens };
}

function loadCoops() {
  if (fs.existsSync(COOPS_FILE_PATH)) {
    const data = fs.readFileSync(COOPS_FILE_PATH, "utf-8");
    const coopsData = JSON.parse(data);
    return coopsData.map(c => {
      const coop = new ChickenCoop(c.id, c.name);
      coop.chickens = c.chickens;
      return coop;
    });
  }
  return [];
}

function loadChickens() {
  if (fs.existsSync(CHICKENS_FILE_PATH)) {
    const data = fs.readFileSync(CHICKENS_FILE_PATH, "utf-8");
    const chickensData = JSON.parse(data);
    return chickensData.map(
      ch =>
        new Chicken(
          ch.id,
          ch.name,
          ch.color,
          ch.age,
          ch.isMolting,
          ch.actions || []
        )
    );
  }
  return [];
}

function saveData(coops, chickens) {
  const serializableChickens = chickens.map(ch => ({
    id: ch.id,
    name: ch.name,
    color: ch.color,
    age: ch.age,
    isMolting: ch.isMolting,
    actions: ch.actions || []
  }));

  const serializableCoops = coops.map(coop => ({
    id: coop.id,
    name: coop.name,
    chickens: coop.chickens.map(ch => ch.id)
  }));

  fs.writeFileSync(CHICKENS_FILE_PATH, JSON.stringify(serializableChickens, null, 2));
  fs.writeFileSync(COOPS_FILE_PATH, JSON.stringify(serializableCoops, null, 2));
}

module.exports = { loadData, saveData };
