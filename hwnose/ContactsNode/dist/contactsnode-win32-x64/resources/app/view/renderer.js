const { saveContact } = require("../model/contactRepository");

window.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("contactForm");
  const statusEl = document.getElementById("status");

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

    const data = {
      firstName,
      lastName,
      age,
      typeOfContact,
      sex,
      hobbies,
      comments,
    };

    try {
      await saveContact(data);
      statusEl.textContent = "Contact saved in MongoDB (JavaScript Desktop)";
      form.reset();
    } catch (err) {
      statusEl.textContent = "Error: " + err.message;
    }
  });
});
