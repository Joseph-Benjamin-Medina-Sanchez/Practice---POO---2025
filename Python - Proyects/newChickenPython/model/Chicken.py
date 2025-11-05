from datetime import date

class Chicken:
    def __init__(self, id, name, color, age, is_molting):
        self.id = id
        self.name = name
        self.color = color
        self.age = age
        self.is_molting = is_molting

    def __cluck(self):
        print(f"chicken {self.name} is clucking, cluck cluck cluck")

    def __eat(self):
        print(f"chicken {self.name} is eating, grains")

    def do_stuff(self):
        self.__cluck()
        self.__eat()
        self.__cluck()
        self.poop(2)
        self.poop(3)
        self.__eat()
        self.wander()
        self.drink()
        self.lay_an_egg('M')
        self.lay_an_egg('L')

    def poop(self, amount):
        from .Poop import Poop  # importación relativa
        poop = Poop(amount)
        print(f"chicken {self.name} is pooping a {poop}")
        return poop

    def lay_an_egg(self, size):
        from .Egg import Egg
        egg = Egg(size)
        print(f"chicken {self.name} is laying a {egg.size} size egg")
        return egg

    def wander(self):
        print(f"chicken {self.name} is wandering")

    def drink(self):
        print(f"chicken {self.name} is drinking")

    def __str__(self):
        return (
            f"\nChicken{{\n"
            f"id -> \t\t{self.id},\n"
            f"name -> \t{self.name},\n"
            f"color -> \t{self.color},\n"
            f"age -> \t\t{self.age},\n"
            f"isMolting -> \t{self.is_molting}\n}}"
        )
