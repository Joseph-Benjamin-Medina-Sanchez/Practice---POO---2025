import random

class AntEater:
    _id_counter = 1
    
    def __init__(self, position, area):
        self.id = AntEater._id_counter
        AntEater._id_counter += 1
        self.position = position
        self.area = area
        self.state = "hungry"  # hungry, eating, sleeping
        self.ants_consumed = 0
        self.eating_ticks = 0
        self.sleeping_ticks = 0
        print(f"Ant eater {self.id} created at {position}")
    
    def act(self):
        if self.state == "hungry":
            self._wander()
        elif self.state == "eating":
            self._eat()
        elif self.state == "sleeping":
            self._sleep()
    
    def _wander(self):
        current_cell = self.area.get_cell(self.position[0], self.position[1])
        
        # Check if there are ants to eat
        if current_cell.ants:
            self.state = "eating"
            self.eating_ticks = 0
            print(f"Ant eater {self.id} found ants and started eating")
        else:
            # Move randomly
            neighbors = self.area.get_neighbor_cells(self.position[0], self.position[1])
            if neighbors and random.random() < 0.5:  # 50% chance to move
                next_cell = random.choice(neighbors)
                current_cell.remove_ant_eater(self)
                self.position = (next_cell.x, next_cell.y)
                next_cell.add_ant_eater(self)
    
    def _eat(self):
        self.eating_ticks += 1
        current_cell = self.area.get_cell(self.position[0], self.position[1])
        
        if self.eating_ticks >= 10:
            if current_cell.ants:
                # Consume an ant
                ant = current_cell.ants[0]
                current_cell.remove_ant(ant)
                if ant in ant.colony.ants:
                    ant.colony.ants.remove(ant)
                
                self.ants_consumed += 1
                print(f"Ant eater {self.id} consumed ant {ant.id}. Total: {self.ants_consumed}")
                
                if self.ants_consumed >= 50:
                    self.state = "sleeping"
                    self.sleeping_ticks = 0
                    print(f"DEBUG: Ant eater {self.id} ate 50 ants and is going to sleep")
                elif current_cell.ants:
                    self.eating_ticks = 0  # Continue eating
                else:
                    self.state = "hungry"
                    print(f"Ant eater {self.id} finished eating, going back to hungry")
            else:
                self.state = "hungry"
                print(f"Ant eater {self.id} no ants left, going back to hungry")
    
    def _sleep(self):
        self.sleeping_ticks += 1
        if self.sleeping_ticks >= 60:  # Reduced for testing
            self.state = "hungry"
            self.ants_consumed = 0
            print(f" Ant eater {self.id} woke up and is hungry again")