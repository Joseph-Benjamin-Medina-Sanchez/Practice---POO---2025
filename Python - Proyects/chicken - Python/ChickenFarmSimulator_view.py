from Chicken_model import Chicken

def main():
    print("My chicken is")
    
    owner = "Joseph Medina"
    id = 12
    name = "Lucy"
    color = "Brown and White"
    age = 3
    is_molting = False
    
    chicken = Chicken(id, name, color, age, is_molting)
    print(f"The chicken owner is {owner}")
    print("The chicken is -->", chicken)
    print("Chicken id is -->", chicken.get_id())
    print("Chicken name is -->", chicken.get_name())
    print("Chicken color is -->", chicken.get_color())
    print("Chicken age is -->", chicken.get_age())
    print("Chicken is molting -->", chicken.get_is_molting())

if __name__ == "__main__":
    main()
