class Chicken:
    def __init__(self, id, name, color, age, is_molting):
        self.id = id
        self.name = name
        self.color = color
        self.age = age
        self.is_molting = is_molting

    def to_dict(self):
        return {
            "id": self.id,
            "name": self.name,
            "color": self.color,
            "age": self.age,
            "isMolting": self.is_molting
        }

    @staticmethod
    def from_dict(data):
        return Chicken(
            data["id"],
            data["name"],
            data["color"],
            data["age"],
            data["isMolting"]
        )

    def __str__(self):
        return f"{self.name} (ID: {self.id}, Color: {self.color}, Age: {self.age}, Molting: {self.is_molting})"
