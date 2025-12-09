from dataclasses import dataclass
from typing import List

@dataclass
class Contact:
    first_name: str
    last_name: str
    age: int
    type_of_contact: str
    sex: str
    hobbies: List[str]
    comments: str
