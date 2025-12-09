const { MongoClient } = require("mongodb");

const ENV_URI_NAME = "MONGODB_URI";
const DATABASE_NAME = "ContactsDB";

let client = null;

async function getDatabase() {
  if (!client) {
    const uri = process.env[ENV_URI_NAME];
    if (!uri) {
      throw new Error(`La variable de entorno ${ENV_URI_NAME} no está definida`);
    }
    client = new MongoClient(uri);
    await client.connect();
  }
  return client.db(DATABASE_NAME);
}

async function closeConnection() {
  if (client) {
    await client.close();
    client = null;
  }
}

module.exports = { getDatabase, closeConnection };
