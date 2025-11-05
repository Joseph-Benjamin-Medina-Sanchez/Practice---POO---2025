from model.nest import Nest
from model.ant import Ant

class Colony:
    def __init__(self, position, area):
        self.nest = Nest(position, self)
        self.ants = []
        self.area = area
        
        # ADD INITIAL FOOD TO NEST - Muy importante
        self.nest.food_amount = 20  # Suficiente comida inicial
        
        # Create initial ants
        for _ in range(5):  # Empezar con 5 hormigas
            initial_ant = Ant(position, self)
            self.ants.append(initial_ant)
            area.get_cell(position[0], position[1]).add_ant(initial_ant)
        
        print(f"Colony created at {position} with {self.nest.food_amount} initial food and {len(self.ants)} ants")
    
    def update(self):
        # Try to create new ant if enough food (check every 10 ticks para no saturar)
        if len(self.ants) < 20:  # Límite máximo de hormigas
            new_ant = self.nest.create_ant()
            if new_ant:
                self.ants.append(new_ant)
                nest_cell = self.area.get_cell(self.nest.position[0], self.nest.position[1])
                nest_cell.add_ant(new_ant)
                print(f"NEW ANT CREATED! Total ants: {len(self.ants)}, Food in nest: {self.nest.food_amount}")
        
        # Update all ants
        for ant in self.ants[:]:
            ant.act(self.area)
            
            # Remover hormigas que murieron (por ejemplo, si energía llega a 0)
            if ant.energy <= 0:
                current_cell = self.area.get_cell(ant.position[0], ant.position[1])
                current_cell.remove_ant(ant)
                self.ants.remove(ant)
                print(f"Ant {ant.id} died from exhaustion")