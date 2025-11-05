from model.Egg import Egg
from model.Poop import Poop

class Chicken:
    def __init__(self, id: int, name: str, color: str, age: int, is_molting: bool):
        self.id = id
        self.name = name
        self.color = color
        self.age = age
        self.is_molting = is_molting

    def cluck(self):
        print(f"chicken {self.name} is clucking, cluck cluck cluck")

    def eat(self):
        print(f"chicken {self.name} is eating grains")

    def wander(self):
        print(f"chicken {self.name} is wandering")

    def drink(self):
        print(f"chicken {self.name} is drinking water")

    def poop(self, amount: int):
        poop = Poop(amount)
        print(f"chicken {self.name} is pooping a {poop}")
        return poop

    def lay_an_egg(self, size: str):
        egg = Egg(size)
        print(f"chicken {self.name} is laying a {egg.get_size()} size egg")
        return egg

    def do_stuff(self):
        self.cluck()
        self.eat()
        self.cluck()
        self.poop(2)
        self.poop(3)
        self.eat()
        self.wander()
        self.drink()
        self.lay_an_egg('M')
        self.lay_an_egg('L')

    def __str__(self):
        return (f"\nChicken{{\n"
                f"id -> \t\t{self.id},\n"
                f"name -> \t{self.name},\n"
                f"color -> \t{self.color},\n"
                f"age -> \t\t{self.age},\n"
                f"isMolting -> \t{self.is_molting}\n}}")
