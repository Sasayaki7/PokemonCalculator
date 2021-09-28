/**
 * 
 */

function checkIfPokemonExists(s){
	return true
}

function checkIfAbilityExists(s){
	return true
}

function onSubmit(e){
	e.preventDefault();
	let canSubmit = true;
	let form = document.forms["submission-form"];
	if ((form["generation"].value != "8" || form["generation"].value != "7" || form["generation"].value != "6" || form["generation"].value != "5")){
		form["generation"].value = "8";
	}
	if (!checkIfPokemonExists(form["calcpokemon"].value)){
		canSubmit = false;
	}
	
	if (!checkIfAbilityExists(form["ability"].value)){
		canSubmit = false;
	}
	
	
}
 
 