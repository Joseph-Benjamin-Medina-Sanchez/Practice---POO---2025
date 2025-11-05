from model.Chicken import Chicken
from model.ChickenCoop import ChickenCoop
import csv

print(" Chicken Coop Farm Simulator \n")

owner = "Joseph Medina"

chicken_coops = [
    ChickenCoop(1, "Coop #1 "),
    ChickenCoop(2, "Coop #2 ")
]

chickens = [
    Chicken(1, "Lucy", "White", 2, False),
    Chicken(2, "Maruja", "Brown", 3, True),
    Chicken(3, "Clara", "Golden", 4, False),
    Chicken(4, "Tina", "Black", 2, False),
    Chicken(5, "Nina", "Gray", 1, True),
    Chicken(6, "Sofi", "White", 3, False),
    Chicken(7, "Luna", "Golden", 2, True),
    Chicken(8, "Mimi", "Black", 1, False),
    Chicken(9, "Rita", "Brown", 4, False),
    Chicken(10, "Coco", "White", 2, True)
]

for chicken in chickens[:5]:
    chicken_coops[0].add_chicken(chicken)
for chicken in chickens[5:]:
    chicken_coops[1].add_chicken(chicken)

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

print(" Simulation finished successfully.\n")

with open("chickens_data.csv", mode="w", newline="", encoding="utf-8") as file:
    writer = csv.writer(file, delimiter=';')
    writer.writerow(["ID", "NAME", "COLOR", "AGE", "IS_MOLTING"])
    for chicken in chickens:
        writer.writerow([chicken.id, chicken.name, chicken.color, chicken.age, chicken.is_molting])

print("Datos guardados correctamente en 'chickens_data.csv'")

with open("chickens_data.txt", mode="w", encoding="utf-8") as file:
    file.write("CHICKEN INFORMATION\n")
    file.write("-------------------\n\n")
    for chicken in chickens:
        file.write(f"ID: {chicken.id}\n")
        file.write(f"NAME: {chicken.name}\n")
        file.write(f"COLOR: {chicken.color}\n")
        file.write(f"AGE: {chicken.age}\n")
        file.write(f"IS MOLTING: {chicken.is_molting}\n")
        file.write("-------------------\n")

print("Datos guardados correctamente en 'chickens_data.txt'")