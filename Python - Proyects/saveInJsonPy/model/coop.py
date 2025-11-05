from model.chicken import Chicken

class Coop:
    def __init__(self, id, location):
        self.id = id
        self.location = location
        self.chickens = []

    def add_chicken(self, chicken):
        self.chickens.append(chicken)

    def remove_chicken(self, chicken_id):
        self.chickens = [c for c in self.chickens if c.id != chicken_id]

    def get_chicken_by_id(self, chicken_id):
        for chicken in self.chickens:
            if chicken.id == chicken_id:
                return chicken
        return None

    def to_dict(self):
        return {
            "id": self.id,
            "location": self.location,
            "chickens": [chicken.to_dict() for chicken in self.chickens]
        }

    @staticmethod
    def from_dict(data):
        coop = Coop(data["id"], data["location"])
        coop.chickens = [Chicken.from_dict(ch) for ch in data.get("chickens", [])]
        return coop

    def __str__(self):
        result = f"\n {self.location} (ID: {self.id}) contains:\n"
        if not self.chickens:
            result += " - (Empty)\n"
        else:
            for chicken in self.chickens:
                result += f" - {chicken.name} (ID: {chicken.id})\n"
        result += "------------------------------------"
        return result
