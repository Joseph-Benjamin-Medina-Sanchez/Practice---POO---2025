from model.cell import Cell

class Area:
    def __init__(self, width, height):
        self.width = width
        self.height = height
        self.cells = [[Cell(x, y) for y in range(height)] for x in range(width)]
        self.colonies = []
        self.ant_eaters = []
    
    def get_cell(self, x, y):
        if 0 <= x < self.width and 0 <= y < self.height:
            return self.cells[x][y]
        return None
    
    def get_neighbor_cells(self, x, y):
        neighbors = []
        for dx, dy in [(0,1), (1,0), (0,-1), (-1,0), (1,1), (1,-1), (-1,1), (-1,-1)]:
            neighbor = self.get_cell(x + dx, y + dy)
            if neighbor:
                neighbors.append(neighbor)
        return neighbors
    
    def add_colony(self, position):
        from model.colony import Colony
        colony = Colony(position, self)
        self.colonies.append(colony)
        return colony
    
    def add_ant_eater(self, position):
        from model.ant_eater import AntEater
        ant_eater = AntEater(position, self)
        self.ant_eaters.append(ant_eater)
        self.get_cell(position[0], position[1]).add_ant_eater(ant_eater)
        return ant_eater
    
    def add_food_pile(self, x, y, amount):
        cell = self.get_cell(x, y)
        if cell:
            cell.add_food_pile(amount)
            print(f"Food pile added at ({x}, {y}) with {amount} food")
    
    def update(self):
        # Update all colonies
        for colony in self.colonies:
            colony.update()
        
        # Update all ant eaters
        for ant_eater in self.ant_eaters:
            ant_eater.act()
        
        # Update pheromones
        for x in range(self.width):
            for y in range(self.height):
                cell = self.cells[x][y]
                if cell.pheromone:
                    if not cell.pheromone.decrease_level():
                        cell.pheromone = None