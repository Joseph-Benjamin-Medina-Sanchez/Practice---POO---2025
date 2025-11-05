from model.food import Food

class FoodPile:
    def __init__(self, amount):
        self.amount = amount
    
    def request_food(self, x):
        if self.amount >= x:
            self.amount -= x
            return Food(x)
        else:
            remaining = self.amount
            self.amount = 0
            return Food(remaining)
    
    def get_amount(self):
        return self.amount
    
    def exists(self):
        return self.amount > 0