from model.farm import Farm
from model.coop import Coop
from model.chicken import Chicken

class FarmSimulatorView:
    def __init__(self, farmer_name):
        self.farmer_name = farmer_name
        self.farm = Farm("data/farm.json")

    def print_menu(self):
        print(f"\n--- {self.farmer_name}'s Chicken Farm Simulator ---")
        print("1. List all coops and chickens")
        print("2. Add a new chicken")
        print("3. Add a new coop")
        print("4. Run farm simulation")
        print("5. Edit a chicken")
        print("6. Delete a chicken")
        print("7. Exit")

    def list_coops_and_chickens(self):
        print("\n--- Farm Status ---")
        coops = self.farm.list_coops()
        if not coops:
            print("There are no coops on this farm.")
            return
        for coop in coops:
            print(coop)

    def add_coop(self):
        coop_id = int(input("Enter coop ID: "))
        location = input("Enter coop location: ")
        self.farm.add_coop(Coop(coop_id, location))

    def add_chicken(self):
        if not self.farm.coops:
            print("You must add a coop first!")
            return
        chicken_id = int(input("Enter chicken ID: "))
        name = input("Enter chicken name: ")
        color = input("Enter chicken color: ")
        age = int(input("Enter chicken age: "))
        is_molting = input("Is molting? (true/false): ").lower() == "true"
        print("Available coops:")
        for c in self.farm.coops:
            print(f" - ID {c.id}: {c.location}")
        coop_id = int(input("Enter coop ID: "))
        chicken = Chicken(chicken_id, name, color, age, is_molting)
        self.farm.add_chicken(coop_id, chicken)

    def run_simulation(self):
        print("\n--- Running Farm Simulation ---")
        for coop in self.farm.coops:
            for chicken in coop.chickens:
                print(f"Chicken {chicken.name} (ID {chicken.id}) in {coop.location} is clucking, eating, and wandering.")
        print("Simulation finished successfully.")

    def edit_chicken(self):
        chicken_id = int(input("Enter chicken ID to edit: "))
        name = input("New name (leave blank to keep current): ")
        color = input("New color (leave blank to keep current): ")
        age_input = input("New age (leave blank to keep current): ")
        is_molting_input = input("Is molting? (true/false, leave blank to keep current): ")

        kwargs = {}
        if name: kwargs["name"] = name
        if color: kwargs["color"] = color
        if age_input: kwargs["age"] = int(age_input)
        if is_molting_input: kwargs["is_molting"] = is_molting_input == "true"

        self.farm.edit_chicken(chicken_id, **kwargs)

    def delete_chicken(self):
        chicken_id = int(input("Enter chicken ID to delete: "))
        self.farm.delete_chicken(chicken_id)

    def run(self):
        running = True
        while running:
            self.print_menu()
            choice = input("Enter your choice (1-7): ")
            if choice == "1":
                self.list_coops_and_chickens()
            elif choice == "2":
                self.add_chicken()
            elif choice == "3":
                self.add_coop()
            elif choice == "4":
                self.run_simulation()
            elif choice == "5":
                self.edit_chicken()
            elif choice == "6":
                self.delete_chicken()
            elif choice == "7":
                print("Saving data and exiting. Goodbye!")
                running = False
            else:
                print("Invalid choice. Please try again.")
