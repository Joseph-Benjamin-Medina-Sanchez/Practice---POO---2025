class Egg:
    def __init__(self, size):
        self.size = size

    def __str__(self):
        return f"Egg{{size={self.size}}}"
