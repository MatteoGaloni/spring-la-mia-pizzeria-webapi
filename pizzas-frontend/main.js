const apiUrl = "http://localhost:8080/api/pizzas"
const root = document.getElementById("root");
const searchBtn = document.getElementById("searchBtn");
const searchInput = document.getElementById("searchInput");
const deletePizzaBtn = document.getElementById("deletePizza");


// // **********************************************************************

//Funzione per ottenere la lista delle pizza dal Database
const getPizza = async (value = null) => {
    try {
        let apiUrlParam = apiUrl
        if (value) {
            apiUrlParam += "?search=" + value;
            console.log(apiUrlParam)
        }
        const response = await axios.get(apiUrlParam);
        createPizzaContainer(response.data);
    } catch (error) {
        console.log(error);
    }
}
getPizza();

//Funzione per creare il container con le cards di ogni pizza
const createPizzaContainer = (data) => {
    let content;
    console.log(data.content);
    if (data.content.length > 0) {
        content = '<div class="row gy-2">';
        data.content.forEach((pizza) => {
            content += '<div class="col-md-6 col-lg-3">';
            content += '<div class="card" style="width: 18rem;">';
            content += `<img src="${pizza.img}" class="card-img-top" alt="${pizza.name}">`;
            content += '<div class="card-body">';
            content += `<h5 class="card-title">${pizza.name}</h5>`;
            content += `<p class="card-text">${pizza.description}</p>`;
            content += `<p class="card-text">${pizza.price}€</p>`;
            content += `<button data-id="${pizza.id}" class="btn btn-danger deletePizza">Cancella pizza</button>`
            content += '</div></div></div>';
        });
        content += '</div>';
    }

    root.innerHTML = content;

}

//Funzione per ricerca con filtro

searchBtn.addEventListener("click", (event) => {
    event.preventDefault();
    const value = searchInput.value.trim();
    getPizza(value);
})

//aggiungo evento sul deleteButton con idPizza
document.addEventListener("click", (event) => {
    if (event.target.classList.contains("deletePizza")) {
        const pizzaId = event.target.dataset.id;
        deletePizza(pizzaId);
    }
});

//chiamo database per cancellare Pizza
const deletePizza = async (pizzaId) => {
    try {
        const response = await axios.delete(apiUrl + "/" + pizzaId);
        getPizza()
        console.log(response)
    } catch (error) {
        console.log(error)
    }
}


