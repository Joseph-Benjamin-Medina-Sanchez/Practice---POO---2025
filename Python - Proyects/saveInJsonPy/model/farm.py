import json
import os
from model.coop import Coop
from model.chicken import Chicken

class Farm:
    def __init__(self, file_path):
        self.file_path = file_path
        self.coops = []
        self.coops = self.load_data()

    def load_data(self):
        if os.path.exists(self.file_path):
            with open(self.file_path, "r", encoding="utf-8") as file:
                data = json.load(file)
                print("Loaded existing farm data.")
                return [Coop.from_dict(c) for c in data]
        else:
            print("No data found. Creating new farm file...")
            os.makedirs(os.path.dirname(self.file_path), exist_ok=True)
            # Aquí se pasa directamente una lista vacía sin tocar self.coops
            with open(self.file_path, "w", encoding="utf-8") as file:
                json.dump([], file, indent=2, ensure_ascii=False)
            return []

    def save_data(self, coops=None):

        data_to_save = coops if coops is not None else self.coops
        data = [coop.to_dict() for coop in data_to_save]
        os.makedirs(os.path.dirname(self.file_path), exist_ok=True)
        with open(self.file_path, "w", encoding="utf-8") as file:
            json.dump(data, file, indent=2, ensure_ascii=False)

    def list_coops(self):
        return self.coops

    def get_coop_by_id(self, coop_id):
        for coop in self.coops:
            if coop.id == coop_id:
                return coop
        return None

    def add_coop(self, coop):
        if any(c.id == coop.id for c in self.coops):
            print("Error: Coop with this ID already exists.")
        else:
            self.coops.append(coop)
            self.save_data()
            print(f"Successfully added coop at {coop.location}!")

    def add_chicken(self, coop_id, chicken):
        coop = self.get_coop_by_id(coop_id)
        if coop:
            if any(c.id == chicken.id for c in coop.chickens):
                print("Error: A chicken with this ID already exists in this coop.")
                return
            coop.add_chicken(chicken)
            self.save_data()
            print(f"Successfully added {chicken.name} to coop {coop.location}!")
        else:
            print("Error: Coop not found.")

    def edit_chicken(self, chicken_id, **kwargs):
        for coop in self.coops:
            chicken = coop.get_chicken_by_id(chicken_id)
            if chicken:
                for key, value in kwargs.items():
                    setattr(chicken, key, value)
                self.save_data()
                print(f"Chicken {chicken.name} updated successfully.")
                return
        print("Chicken not found.")

    def delete_chicken(self, chicken_id):
        for coop in self.coops:
            if any(ch.id == chicken_id for ch in coop.chickens):
                coop.remove_chicken(chicken_id)
                self.save_data()
                print("Chicken deleted successfully.")
                return
        print("Chicken not found.")
