const readline = require("readline");

const terminal = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

console.log("-----WELCOME TO THE MULTIPLICATION TABLES-----");
console.log("-----Tables available from 1 to 12-----\n");

let continueProgram = true;

function startProgram() {
    if (!continueProgram) {
        terminal.close();
        return;
    }

    terminal.question("Enter the number you want to see the multiplication table of --> ", (inputNumber) => {
        const multiplicationNumber = parseInt(inputNumber);

        if (isNaN(multiplicationNumber) || multiplicationNumber < 1 || multiplicationNumber > 12) {
            console.log("Make sure your value is between 1 and 12");
            startProgram();
            return;
        }

        console.log(`You have selected ${multiplicationNumber}`);
        console.log("—".repeat(16));

        for (let multiplier = 1; multiplier <= 12; multiplier++) {
            console.log(`| ${multiplicationNumber} * ${multiplier} = ${multiplicationNumber * multiplier} |`);
        }

        console.log("—".repeat(16));

        terminal.question("Do you wish to continue? (1 --> Yes, 2 --> No) --> ", (answer) => {
            if (answer.trim() === "1") {
                startProgram();
            } else {
                console.log("Thanks for using us - JM");
                continueProgram = false;
                terminal.close();
            }
        });
    });
}

startProgram();
