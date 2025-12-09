const { getDatabase } = require("./mongoConnection");
const Contact = require("./contact");

async function saveContact(data) {
  const contact = new Contact(
    data.firstName,
    data.lastName,
    parseInt(data.age, 10) || 0,
    data.typeOfContact,
    data.sex,
    Array.isArray(data.hobbies) ? data.hobbies : [],
    data.comments
  );

  const db = await getDatabase();
  const collection = db.collection("contacts");
  await collection.insertOne(contact);
}

module.exports = { saveContact };
