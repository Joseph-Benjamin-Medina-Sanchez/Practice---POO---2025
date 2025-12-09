from dataclasses import asdict
from .mongo_connection import get_database
from .contact import Contact

db = get_database()
collection = db["contacts"]

def save_contact(contact: Contact):
    document = asdict(contact)
    collection.insert_one(document)
