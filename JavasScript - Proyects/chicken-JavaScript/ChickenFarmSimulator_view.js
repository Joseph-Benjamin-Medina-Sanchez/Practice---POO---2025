const Chicken = require("./Chicken_model");


function main() {
    console.log(" My chicken is ");


    const owner = "Joseph Medina";
    const id = 1;
    const name = "Lucy";
    const color = "Brown";
    const age = 3;
    const isMolting = true;


    const chicken = new Chicken(id, name, color, age, isMolting);

        console.log(`The chicken owner is ${owner}`);
        console.log("The chicken is -->", chicken.toString());
        console.log("Chicken id is -->", chicken.id);
        console.log("Chicken name is -->", chicken.name);
        console.log("Chicken color is -->", chicken.color);
        console.log("Chicken age is -->", chicken.age);
        console.log("Chicken is molting -->", chicken.isMolting);
}

main();
