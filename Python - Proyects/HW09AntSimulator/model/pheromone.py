class Pheromone:
    def __init__(self, initial_level=100):
        self.level = initial_level
    
    def decrease_level(self):
        self.level -= 1
        return self.level > 0
    
    def get_level(self):
        return self.level