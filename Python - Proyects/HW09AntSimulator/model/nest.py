from model.ant import Ant

class Nest:
    def __init__(self, position, colony):
        self.position = position
        self.colony = colony
        self.food_amount = 0
    
    def place_food(self, amount):
        self.food_amount += amount
        print(f"Food added to nest. Total: {self.food_amount}")
    
    def create_ant(self):
        if self.food_amount >= 5:
            self.food_amount -= 5
            new_ant = Ant(self.position, self.colony)
            print(f"New ant created! Food left: {self.food_amount}")
            return new_ant
        return None
    
    def get_food_amount(self):
        return self.food_amount