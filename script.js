const addPartButton = document.getElementById("add-part");

addPartButton.addEventListener("click", function(){
    const partNameInput = document.getElementById("part-name");
        if(partNameInput.value.trim() === ""){
        alert("Enter a part name.");
        return;
         }
    const quantityInput = document.getElementById("Quantity");
    const quantity = quantityInput.valueAsNumber;
         if(!Number.isInteger(quantity) || quantity < 0){
            alert("Enter a whole number of 0 or more.");
            return;
         }
         
    const partsList = document.getElementById("parts-list");
    const listItem = document.createElement("li")

    if(quantity === 0){
        listItem.textContent = partNameInput.value + " - Out of stock";
        }else{
         listItem.textContent = partNameInput.value + " - " + quantityInput.value + " in stock";
        }
    partsList.appendChild(listItem);
    
    partNameInput.value = "";
    quantityInput.value = "";
    partNameInput.focus();
});