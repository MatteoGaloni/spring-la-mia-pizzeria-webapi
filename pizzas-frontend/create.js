const apiUrl = "http://localhost:8080/api/pizzas";
const createPizzaForm = document.getElementById("createPizzaForm");


//aggiungo evento sul submit del form
createPizzaForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const newPizza = {
        name: document.getElementById("name").value,
        description: document.getElementById("description").value,
        img: document.getElementById("img").value,
        price: document.getElementById("price").value
    };
    createNewPizza(newPizza);

});


//funzione per creare una nuova pizza
const createNewPizza = async (newPizza) => {
    try {
        const response = await axios.post(apiUrl, newPizza);
        window.location.href = "index.html";
    } catch (error) {
        console.log(error);
    }
};
