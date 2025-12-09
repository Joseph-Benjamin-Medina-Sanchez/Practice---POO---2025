const { saveContact } = require("../model/contactRepository");

window.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("contactForm");
  const statusEl = document.getElementById("status");

  const validateName = (name) => /^[a-zA-Z\s]+$/.test(name);
  const validateAge = (age) => /^\d+$/.test(age);
  const validateNonNegativeAge = (age) => age >= 0;
  const validateEmptyField = (value, fieldName) => {
    if (!value.trim()) {
      throw new Error(`${fieldName} is required.`);
    }
  };
  const validateSex = (sexInput) => {
    if (!sexInput) {
      throw new Error("Sex must be selected.");
    }
  };

  form.addEventListener("submit", async (event) => {
    event.preventDefault();
    statusEl.textContent = "Saving...";

    const firstName = document.getElementById("firstName").value.trim();
    const lastName = document.getElementById("lastName").value.trim();
    const age = document.getElementById("age").value.trim();
    const typeOfContact = document.getElementById("typeOfContact").value;

    const sexInput = document.querySelector('input[name="sex"]:checked');
    const sex = sexInput ? sexInput.value : "Unknown";

    const hobbiesSelect = document.getElementById("hobbies");
    const hobbies = Array.from(hobbiesSelect.selectedOptions).map(
      (opt) => opt.value
    );

    const comments = document.getElementById("comments").value.trim();

    try {
      if (!validateName(firstName)) {
        throw new Error("First Name can only contain letters and spaces.");
      }
      if (!validateName(lastName)) {
        throw new Error("Last Name can only contain letters and spaces.");
      }

      if (!validateAge(age)) {
        throw new Error("Age must be a valid number.");
      }

      if (!validateNonNegativeAge(age)) {
        throw new Error("Age cannot be negative.");
      }

      validateEmptyField(firstName, "First Name");
      validateEmptyField(lastName, "Last Name");
      validateEmptyField(age, "Age");
      validateEmptyField(typeOfContact, "Type of Contact");

      validateSex(sexInput);

      const data = {
        firstName,
        lastName,
        age,
        typeOfContact,
        sex,
        hobbies,
        comments,
      };

      await saveContact(data);
      statusEl.textContent = "Contact saved in MongoDB (JavaScript Desktop)";
      form.reset();
    } catch (err) {
      statusEl.textContent = "Error: " + err.message;
    }
  });
});
