package view;

import model.*;
import java.util.Scanner;

public class FarmSimulatorView {
    private final Farm farm;
    private final Scanner scanner;

    public FarmSimulatorView() {
        this.farm = new Farm("data/farm.json");
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the Chicken Farm Simulator");
        boolean exit = false;
        while (!exit) {
            System.out.println("\n1. List all coops and chickens");
            System.out.println("2. Add a coop");
            System.out.println("3. Add a chicken");
            System.out.println("4. Edit a chicken");
            System.out.println("5. Remove a chicken");
            System.out.println("6. Remove a coop");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> listAll();
                case 2 -> addCoop();
                case 3 -> addChicken();
                case 4 -> editChicken();
                case 5 -> removeChicken();
                case 6 -> removeCoop();
                case 7 -> {
                    farm.saveData();
                    exit = true;
                }
                default -> System.out.println("Invalid option");
            }
        }
        scanner.close();
    }

    private void listAll() {
        if (farm.getCoops().isEmpty()) {
            System.out.println("No coops available.");
            return;
        }
        for (Coop coop : farm.getCoops()) {
            System.out.println("====================================");
            System.out.println(coop);
        }
    }

    private void addCoop() {
        System.out.print("Enter new coop ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter location: ");
        String location = scanner.nextLine();
        farm.addCoop(new Coop(id, location));
        System.out.println("Coop added successfully.");
    }

    private void addChicken() {
        System.out.print("Enter coop ID: ");
        int coopId = scanner.nextInt();
        scanner.nextLine();
        Coop coop = farm.findCoopById(coopId);
        if (coop == null) {
            System.out.println("Coop not found.");
            return;
        }

        System.out.print("Enter chicken ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter color: ");
        String color = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Is molting? (true/false): ");
        boolean molting = scanner.nextBoolean();
        scanner.nextLine();

        coop.addChicken(new Chicken(id, name, color, age, molting));
        farm.saveData();
        System.out.println("Chicken added successfully.");
    }

    private void editChicken() {
        System.out.print("Enter coop ID: ");
        int coopId = scanner.nextInt();
        scanner.nextLine();
        Coop coop = farm.findCoopById(coopId);
        if (coop == null) {
            System.out.println("Coop not found.");
            return;
        }

        System.out.print("Enter chicken ID: ");
        int chickenId = scanner.nextInt();
        scanner.nextLine();
        Chicken chicken = coop.findChickenById(chickenId);
        if (chicken == null) {
            System.out.println("Chicken not found.");
            return;
        }

        System.out.print("New name (" + chicken.getName() + "): ");
        String name = scanner.nextLine();
        System.out.print("New color (" + chicken.getColor() + "): ");
        String color = scanner.nextLine();
        System.out.print("New age (" + chicken.getAge() + "): ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Is molting? (" + chicken.isMolting() + "): ");
        boolean molting = scanner.nextBoolean();
        scanner.nextLine();

        chicken.setName(name);
        chicken.setColor(color);
        chicken.setAge(age);
        chicken.setIsMolting(molting);
        farm.saveData();
        System.out.println("Chicken updated successfully.");
    }

    private void removeChicken() {
        System.out.print("Enter coop ID: ");
        int coopId = scanner.nextInt();
        scanner.nextLine();
        Coop coop = farm.findCoopById(coopId);
        if (coop == null) {
            System.out.println("Coop not found.");
            return;
        }

        System.out.print("Enter chicken ID to remove: ");
        int chickenId = scanner.nextInt();
        scanner.nextLine();
        coop.removeChicken(chickenId);
        farm.saveData();
        System.out.println("Chicken removed successfully.");
    }

    private void removeCoop() {
        System.out.print("Enter coop ID to remove: ");
        int coopId = scanner.nextInt();
        scanner.nextLine();
        farm.removeCoop(coopId);
        System.out.println("Coop removed successfully.");
    }

    public static void main(String[] args) {
        new FarmSimulatorView().start();
    }
}
