from model.Chicken import Chicken
from model.ChickenCoop import ChickenCoop

print(" Chicken Coop Farm Simulator \n")

owner = "Joseph Medina"

chicken_coops = [
    ChickenCoop(1, "Coop #1 "),
    ChickenCoop(2, "Coop #2 ")
]

chickens = [
    Chicken(1, "Lucy", "White", 2, False),
    Chicken(2, "Maruja", "Brown", 1, True),
    Chicken(3, "Clara", "Golden", 3, False),
    Chicken(4, "Tina", "Black", 1, False),
    Chicken(5, "Nina", "Gray", 2, True),
    Chicken(6, "Sofi", "White", 4, False),
    Chicken(7, "Luna", "Golden", 3, True),
    Chicken(8, "Mimi", "Black", 1, False),
    Chicken(9, "Rita", "Brown", 2, False),
    Chicken(10, "Coco", "White", 1, True)
]

# Asignar 5 gallinas al coop1 y 5 al coop2
for chicken in chickens[:5]:
    chicken_coops[0].add_chicken(chicken)
for chicken in chickens[5:]:
    chicken_coops[1].add_chicken(chicken)

# Mostrar los gallineros con sus gallinas
for coop in chicken_coops:
    print(f"\n {coop.name} contains:")
    for chicken in coop.get_chickens():
        print(f" - {chicken.name}")
    print("------------------------------------")

print("\n All chickens in the farm are now doing their routines:\n")

for chicken in chickens:
    print(f"The chicken is --> {chicken}")
    print(f"His owner and friend is --> {owner}")
    chicken.do_stuff()
    print("------------------------------\n")

print(" Simulation finished successfully.")
