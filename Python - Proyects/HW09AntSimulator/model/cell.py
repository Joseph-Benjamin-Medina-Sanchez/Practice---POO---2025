from model.pheromone import Pheromone
from model.food_pile import FoodPile

class Cell:
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.pheromone = None
        self.food_pile = None
        self.ants = []
        self.ant_eaters = []
    
    def drop_pheromone(self, level=100):
        if self.pheromone:
            self.pheromone.level = min(100, self.pheromone.level + level)
        else:
            self.pheromone = Pheromone(level)
    
    def get_pheromone_level(self):
        return self.pheromone.get_level() if self.pheromone else 0
    
    def add_food_pile(self, amount):
        self.food_pile = FoodPile(amount)
    
    def get_food_amount(self):
        return self.food_pile.get_amount() if self.food_pile else 0
    
    def add_ant(self, ant):
        self.ants.append(ant)
    
    def remove_ant(self, ant):
        if ant in self.ants:
            self.ants.remove(ant)
    
    def add_ant_eater(self, ant_eater):
        self.ant_eaters.append(ant_eater)
    
    def remove_ant_eater(self, ant_eater):
        if ant_eater in self.ant_eaters:
            self.ant_eaters.remove(ant_eater)
    
    def has_food(self):
        return self.food_pile is not None and self.food_pile.exists()