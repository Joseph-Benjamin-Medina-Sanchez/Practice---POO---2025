class ChickenCoop:
    def __init__(self, id: int, name: str):
        self.id = id
        self.name = name
        self.chickens = []  

    def add_chicken(self, chicken):
        self.chickens.append(chicken)

    def get_chickens(self):
        return self.chickens

    def __str__(self):
        chickens_info = "\n".join([str(ch) for ch in self.chickens])
        return f"\nChickenCoop{{ id -> {self.id}, name -> {self.name}, chickens -> [{chickens_info}] }}"
