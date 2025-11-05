import random

class Ant:
    _id_counter = 1
    
    def __init__(self, position, colony):
        self.id = Ant._id_counter
        Ant._id_counter += 1
        self.position = position
        self.colony = colony
        self.weight = 1
        self.carrying_food = False
        self.food_amount = 0
        self.direction = random.choice([(0,1), (1,0), (0,-1), (-1,0)])
        self.energy = 100
        self.ticks_since_last_meal = 0
        self.ticks_alive = 0
        print(f"Ant {self.id} created at {position} with weight {self.weight}")
    
    def act(self, area):
        self.ticks_since_last_meal += 1
        self.ticks_alive += 1
        
        # Decrease weight every 50 ticks
        if self.ticks_since_last_meal >= 50 and self.ticks_since_last_meal % 50 == 0:
            self.weight = max(1, self.weight - 1)
            print(f"Ant {self.id} lost weight. Current: {self.weight}")
        
        current_cell = area.get_cell(self.position[0], self.position[1])
        nest_cell = area.get_cell(self.colony.nest.position[0], self.colony.nest.position[1])
        
        # Check if in nest
        if current_cell == nest_cell:
            self._act_in_nest(area, current_cell)
        else:
            self._act_outside_nest(area, current_cell, nest_cell)
    
    def _act_in_nest(self, area, current_cell):
        # Eat food if available and weight < 5
        if self.colony.nest.food_amount > 0 and self.weight < 5:
            self.colony.nest.food_amount -= 1
            self.weight += 1
            self.energy = min(100, self.energy + 10)
            self.ticks_since_last_meal = 0
            print(f"Ant {self.id} ate food. Weight: {self.weight}")
        
        # Drop carried food
        if self.carrying_food:
            self.colony.nest.place_food(self.food_amount)
            self.carrying_food = False
            self.food_amount = 0
            print(f"Ant {self.id} dropped {self.food_amount} food at nest")
        
        # LEAVE NEST - Siempre intentar salir si tiene peso suficiente
        if self.weight >= 2:  # Reducido para que salgan más fácil
            neighbors = area.get_neighbor_cells(self.position[0], self.position[1])
            if neighbors:
                # 90% de probabilidad de salir
                if random.random() < 0.9:
                    # Prefer direction with pheromone
                    pheromone_neighbors = [cell for cell in neighbors if cell.get_pheromone_level() > 0]
                    
                    if pheromone_neighbors:
                        next_cell = random.choice(pheromone_neighbors)
                    else:
                        next_cell = random.choice(neighbors)
                    
                    self._move_to_cell(area, current_cell, next_cell)
                    print(f"Ant {self.id} left nest to {self.position}")
    
    def _act_outside_nest(self, area, current_cell, nest_cell):
        # Pick up food if available and not carrying
        if current_cell.has_food() and not self.carrying_food and current_cell.get_food_amount() > 0:
            max_can_carry = 5 - self.food_amount
            amount_to_pickup = min(current_cell.get_food_amount(), max_can_carry)
            if amount_to_pickup > 0:
                self.carrying_food = True
                self.food_amount += amount_to_pickup
                current_cell.food_pile.amount -= amount_to_pickup
                print(f"Ant {self.id} picked up {amount_to_pickup} food. Total carrying: {self.food_amount}")
                
                # Drop strong pheromone when picking up food
                current_cell.drop_pheromone(100)
        
        if not self.carrying_food:
            self._look_for_food(area, current_cell)
        else:
            self._return_to_nest(area, current_cell, nest_cell)
    
    def _look_for_food(self, area, current_cell):
        neighbors = area.get_neighbor_cells(self.position[0], self.position[1])
        if not neighbors:
            return
        
        # Si hay comida en la celda actual, quedarse aquí
        if current_cell.has_food() and current_cell.get_food_amount() > 0:
            # Quedarse en la celda con comida
            print(f"Ant {self.id} found food at {self.position}")
            return
        
        # Buscar comida
        food_neighbors = [cell for cell in neighbors if cell.has_food() and cell.get_food_amount() > 0]
        if food_neighbors:
            # Moverse hacia comida
            next_cell = random.choice(food_neighbors)
        elif current_cell.get_pheromone_level() > 0:
            # Seguir feromonas
            min_pheromone_level = min(cell.get_pheromone_level() for cell in neighbors)
            min_cells = [cell for cell in neighbors if cell.get_pheromone_level() == min_pheromone_level]
            next_cell = random.choice(min_cells)
        else:
            # Movimiento aleatorio
            next_cell = self._get_preferred_cell(area, neighbors)
        
        self._move_to_cell(area, current_cell, next_cell)
        
        # Drop weak pheromone when searching for food
        if random.random() < 0.3:  # Solo a veces para no saturar
            current_cell.drop_pheromone(20)
    
    def _return_to_nest(self, area, current_cell, nest_cell):
        # Drop strong pheromone when carrying food back to nest
        current_cell.drop_pheromone(100)
        
        neighbors = area.get_neighbor_cells(self.position[0], self.position[1])
        if not neighbors:
            return
        
        # Si está al lado del nido, entrar
        if nest_cell in neighbors:
            self._move_to_cell(area, current_cell, nest_cell)
            return
        
        # Moverse hacia el nido
        nest_direction = self._get_direction_towards(nest_cell)
        preferred_x = self.position[0] + nest_direction[0]
        preferred_y = self.position[1] + nest_direction[1]
        preferred_cell = area.get_cell(preferred_x, preferred_y)
        
        if preferred_cell and preferred_cell in neighbors:
            next_cell = preferred_cell
        else:
            # Moverse aleatoriamente pero preferir dirección general al nido
            towards_nest_neighbors = []
            for cell in neighbors:
                dx = nest_cell.x - cell.x
                dy = nest_cell.y - cell.y
                if abs(dx) + abs(dy) < abs(nest_cell.x - self.position[0]) + abs(nest_cell.y - self.position[1]):
                    towards_nest_neighbors.append(cell)
            
            if towards_nest_neighbors:
                next_cell = random.choice(towards_nest_neighbors)
            else:
                next_cell = random.choice(neighbors)
        
        self._move_to_cell(area, current_cell, next_cell)
    
    def _get_direction_towards(self, target_cell):
        dx = target_cell.x - self.position[0]
        dy = target_cell.y - self.position[1]
        
        if abs(dx) > abs(dy):
            return (1 if dx > 0 else -1, 0)
        else:
            return (0, 1 if dy > 0 else -1)
    
    def _get_preferred_cell(self, area, neighbors):
        # Try to continue in current direction
        preferred_x = self.position[0] + self.direction[0]
        preferred_y = self.position[1] + self.direction[1]
        
        preferred_cell = area.get_cell(preferred_x, preferred_y)
        if preferred_cell and preferred_cell in neighbors:
            return preferred_cell
        else:
            # Change direction if can't move that way
            self.direction = random.choice([(0,1), (1,0), (0,-1), (-1,0)])
            return random.choice(neighbors)
    
    def _move_to_cell(self, area, current_cell, next_cell):
        if current_cell != next_cell:
            current_cell.remove_ant(self)
            old_pos = self.position
            self.position = (next_cell.x, next_cell.y)
            next_cell.add_ant(self)
            self.energy = max(0, self.energy - 1)
            
            # Update direction based on movement
            dx = next_cell.x - old_pos[0]
            dy = next_cell.y - old_pos[1]
            if dx != 0 or dy != 0:
                self.direction = (dx, dy)
            
            print(f"Ant {self.id} moved from {old_pos} to {self.position}")