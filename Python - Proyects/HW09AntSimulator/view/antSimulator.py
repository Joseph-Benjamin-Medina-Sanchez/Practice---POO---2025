import random
import time
import sys
import os

# Add model directory to path
sys.path.append(os.path.join(os.path.dirname(__file__), '..'))

from model.area import Area

class AntSimulator:
    def __init__(self, width=12, height=12, tick_duration=0.02):
        self.area = Area(width, height)
        self.tick_duration = tick_duration
        self.setup_simulation()
    
    def setup_simulation(self):
        
        center_x = self.area.width // 2
        center_y = self.area.height // 2
        self.colony = self.area.add_colony((center_x, center_y))
        
        
        food_positions = [
            (center_x + 1, center_y),      # Al lado del nido
            (center_x - 1, center_y),      # Al lado del nido  
            (center_x, center_y + 1),      # Al lado del nido
            (center_x, center_y - 1),      # Al lado del nido
            (center_x + 2, center_y),      # Cerca
            (center_x - 2, center_y),      # Cerca
            (center_x, center_y + 2),      # Cerca  
            (center_x, center_y - 2),      # Cerca
            (center_x + 1, center_y + 1),  # Diagonal
            (center_x - 1, center_y - 1),  # Diagonal
        ]
        
        for x, y in food_positions:
            if 0 <= x < self.area.width and 0 <= y < self.area.height:
                self.area.add_food_pile(x, y, random.randint(40, 60))  # Mucha comida
    
        
        self.area.add_ant_eater((center_x + 3, center_y))
        
        print("=== SIMULATION SETUP COMPLETE ===")
        print(f"Colony at ({center_x}, {center_y})")
        print(f"Food piles: {len(food_positions)}")
        print(f"Area size: {self.area.width}x{self.area.height}")
        print("=" * 40)
    
    def run_simulation(self, duration_ticks=300):
        print("=== ANT SIMULATION STARTED ===")
        print(f"Duration: {duration_ticks} ticks")
        print(f"Tick speed: {self.tick_duration}s")
        print("=" * 40)
        
        for tick in range(duration_ticks):
            print(f"\n--- Tick {tick + 1} ---")
            
            
            self.area.update()
            
            
            self.display_state(tick + 1)
            
            
            time.sleep(self.tick_duration)
        
        print("\n" + "=" * 40)
        print("=== SIMULATION COMPLETED ===")
    
    def display_state(self, current_tick):
        total_ants = 0
        total_food_in_nest = 0
        ants_carrying_food = 0
        ants_in_nest = 0
        
        for colony in self.area.colonies:
            total_ants += len(colony.ants)
            total_food_in_nest += colony.nest.food_amount
            
           
            nest_pos = colony.nest.position
            for ant in colony.ants:
                if ant.carrying_food:
                    ants_carrying_food += 1
                if ant.position == nest_pos:
                    ants_in_nest += 1
            
            print(f" COLONY: {len(colony.ants)} ants |  Food: {colony.nest.food_amount}")
            print(f"    Carrying food: {ants_carrying_food} |  In nest: {ants_in_nest}")
            
            
            position_count = {}
            for ant in colony.ants:
                pos = ant.position
                position_count[pos] = position_count.get(pos, 0) + 1
            
            
            sorted_positions = sorted(position_count.items(), key=lambda x: x[1], reverse=True)[:5]
            for pos, count in sorted_positions:
                if pos == colony.nest.position:
                    print(f"    Nest: {count} ants")
                else:
                    print(f"    {pos}: {count} ants")
        
        
        for ant_eater in self.area.ant_eaters:
            state_emoji = {
                "hungry": "Yellow", 
                "eating": "Red", 
                "sleeping": "Blue"
            }
            emoji = state_emoji.get(ant_eater.state, "")
            print(f"{emoji} ANTEATER: {ant_eater.state.upper()} at {ant_eater.position}")
            print(f"    Eaten: {ant_eater.ants_consumed}/50")
        
        
        active_food_piles = 0
        total_food_remaining = 0
        for x in range(self.area.width):
            for y in range(self.area.height):
                cell = self.area.get_cell(x, y)
                if cell.has_food():
                    active_food_piles += 1
                    total_food_remaining += cell.get_food_amount()
        
        print(f" FOOD: {active_food_piles} piles | {total_food_remaining} units remaining")
        
        
        if current_tick % 10 == 0:
            print("\n" + " SUMMARY:")
            print(f"   Total ants: {total_ants}")
            print(f"   Total food in nest: {total_food_in_nest}")
            print(f"   Ants carrying food: {ants_carrying_food}")
            print(f"   Active food piles: {active_food_piles}")

if __name__ == "__main__":
    simulator = AntSimulator(
        width=12, 
        height=12, 
        tick_duration=0.01   
    )
    simulator.run_simulation(duration_ticks=200)