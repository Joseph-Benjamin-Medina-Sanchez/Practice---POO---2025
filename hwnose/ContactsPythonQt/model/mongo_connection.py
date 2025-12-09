import os
from pymongo import MongoClient

ENV_URI_NAME = "MONGODB_URI"
DATABASE_NAME = "ContactsDB"

def get_database():
    connection_string = os.getenv(ENV_URI_NAME)
    if not connection_string:
        raise RuntimeError(f"La variable de entorno {ENV_URI_NAME} no está definida")

    client = MongoClient(connection_string)
    return client[DATABASE_NAME]
