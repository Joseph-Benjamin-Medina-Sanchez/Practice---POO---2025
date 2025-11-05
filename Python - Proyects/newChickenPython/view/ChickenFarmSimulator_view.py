import sys
import os
from datetime import date, datetime

sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
from model.Chicken import Chicken

def calcular_edad(fecha_nacimiento):
    today = date.today()
    return today.year - fecha_nacimiento.year - ((today.month, today.day) < (fecha_nacimiento.month, fecha_nacimiento.day))

def crear_nueva_gallina(proximo_id):
    print("\n Vamos a crear una nueva gallina")
    name = input("Enter the chicken's name: ")
    color = input("Enter the chicken's color: ")
    is_molting_input = input("Is the chicken molting? (yes/no): ").strip().lower()
    is_molting = is_molting_input == "yes"

    birth_date_str = input("Enter the chicken's birth date (YYYY-MM-DD): ")
    birth_date = datetime.strptime(birth_date_str, "%Y-%m-%d").date()

    age = calcular_edad(birth_date)
    new_chicken = Chicken(proximo_id, name, color, age, is_molting)
    return new_chicken

def main():
    print(" Chicken Farm Simulator\n")

    owner = "Joseph Medina"

    chicken1 = Chicken(1, "Lucy", "White and Brown", 2, False)
    chicken2 = Chicken(2, "Maruja", "White", 1, True)

    gallinas = [chicken1, chicken2]

    for c in gallinas:
        print(f"The chicken is --> {c}")
        print(f"His owner and friend is --> {owner}\n")
        c.do_stuff()
        print("\n---------------------\n")

    while True:
        add_new = input("Do you want to add a new chicken? (yes/no): ").strip().lower()
        if add_new != "yes":
            break

        proximo_id = len(gallinas) + 1
        nueva_gallina = crear_nueva_gallina(proximo_id)
        gallinas.append(nueva_gallina)

        print(f"\nThe new chicken is --> {nueva_gallina}")
        print(f"His owner and friend is --> {owner}\n")
        nueva_gallina.do_stuff()
        print("\n---------------------\n")

    print("\n--- End of Chicken Farm Simulation ---")

if __name__ == "__main__":
    main()
