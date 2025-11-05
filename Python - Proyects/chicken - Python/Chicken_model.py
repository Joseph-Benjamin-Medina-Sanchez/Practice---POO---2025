class Chicken:
    def __init__(self, id: int, name: str, color: str, age: int, is_molting: bool):
        self._id = id
        self._name = name
        self._color = color
        self._age = age
        self._is_molting = is_molting

    def set_id(self, id: int):
        self._id = id

    def get_id(self) -> int:
        return self._id

    def set_name(self, name: str):
        self._name = name

    def get_name(self) -> str:
        return self._name

    def set_color(self, color: str):
        self._color = color

    def get_color(self) -> str:
        return self._color

    def set_age(self, age: int):
        self._age = age

    def get_age(self) -> int:
        return self._age

    def set_is_molting(self, is_molting: bool):
        self._is_molting = is_molting

    def get_is_molting(self) -> bool:
        return self._is_molting

    def __str__(self) -> str:
        return f"Chicken: id={self._id}, name={self._name}, color={self._color}, age={self._age}, is_molting={self._is_molting}"
