function addIngredient() {
    const ingredientsDiv = document.getElementById('ingredients');
    const ingredientIndex = ingredientsDiv.childElementCount - 1;
    const newIngredientDiv = document.createElement('div');
    newIngredientDiv.className = 'input-group mb-2';
    newIngredientDiv.id = 'ingredient-' + ingredientIndex;
    newIngredientDiv.innerHTML = `
        <input type="text" name="ingredients[${ingredientIndex}].name" class="form-control" placeholder="Nome ingrediente">
        <input type="text" name="ingredients[${ingredientIndex}].quantity" class="form-control" placeholder="Quantità ingrediente">
        <button type="button" class="btn btn-danger" onclick="removeIngredient(${ingredientIndex})">Rimuovi</button>
    `;
    ingredientsDiv.appendChild(newIngredientDiv);
}

function removeIngredient(index) {
    const ingredientDiv = document.getElementById('ingredient-' + index);
    ingredientDiv.remove();
}
