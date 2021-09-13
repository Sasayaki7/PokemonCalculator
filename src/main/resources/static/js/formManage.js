/**
 * 
 */
 

const formRow = document.getElementById('condition-1').cloneNode(true)
const conditions = document.querySelector('.conditions')


function changeStringNumber(str, index){
	if (str != null){
		return str.replace(/\d+/, index);
	}
	else{
		return ""
	}	
}

function changeName(element, index){
	if(element.nodeName == 'LABEL'){
		element.setAttribute("for",changeStringNumber(element.getAttribute("for"),  index));
	}
	else if (element.nodeName == 'INPUT' || element.nodeName == 'SELECT'){
		element.setAttribute("name",changeStringNumber(element.getAttribute("name"), index));
		if (element.type =='radio'){
			element.setAttribute("id", changeStringNumber(element.getAttribute("id"), index))
		}
	}
	else if (element.nodeName == 'DIV'){
		element.setAttribute("id",changeStringNumber(element.getAttribute("id"), index));
	}

}


function changeAllNodes(element, index){
	if (element.hasChildNodes()){
		element.childNodes.forEach(e =>{
			changeAllNodes(e, index)
		})
	}
	changeName(element, index)
	return element
}

function cleanseText(text){
	return text.replace(" ", "-").toLowerCase();
}


function removeDash(s){
	let arrS = s.split("-")
	arrS = arrS.map(w => w.charAt(0).toUpperCase() + w.slice(1))
	return arrS.join(" ")
}

function getImageForPokemon(e){
	let cleansedText = cleanseText(e.value)
	fetch(`https://pokeapi.co/api/v2/pokemon/${cleansedText}`)
		.then(resp => resp.json())
		.then(resp => {document.getElementById("pokemon-pic").src=`${resp.sprites.front_default}`
			let html = "";
			for(let abil in resp.abilities){
				html += `<option ${abil==0?'selected' : ''} value=${resp.abilities[abil].ability.name}>${removeDash(resp.abilities[abil].ability.name)}</option>`
			}
			e.parentNode.parentNode.querySelector(".your-ability").innerHTML=html
		})
		.catch()
}



function createListOfAutoComplete(list, div){
	let html = "";
	for (let item of list){
		let newToggle = document.createElement("div");
		newToggle.setAttribute("class", "autocomplete-item")
		newToggle.innerHTML = item;
		newToggle.addEventListener("click", function(e){
			let elem = div.parentNode.querySelector("input")
			elem.value = removeDash(item)
		})
		div.appendChild(newToggle);
	}
}


function getMovesStartingWith(s){
	fetch(`https://localhost:8080/api/moves?startWith=${s}`)
		.then(resp => resp.json())
		.then(resp => {})
}




function changeOppPokemon(e){
	let cleansedText = cleanseText(e.value)
	fetch(`https://pokeapi.co/api/v2/pokemon/${cleansedText}`)
		.then(resp => resp.json())
		.then(resp => {
			e.parentNode.parentNode.querySelector(".opp-pokemon").src=`${resp.sprites.front_default}`
			let html = "";
			for(let abil in resp.abilities){
				html += `<option ${abil==0?'selected' : ''} value=${resp.abilities[abil].ability.name}>${removeDash(resp.abilities[abil].ability.name)}</option>`
			}
			e.parentNode.parentNode.parentNode.querySelector(".opp-ability").innerHTML=html
		})
		.catch()
}

function changeItemForPokemon(e){
	let cleansedText = cleanseText(e.value)
	fetch(`https://pokeapi.co/api/v2/item/${cleansedText}`)
		.then(resp => resp.json())
		.then(resp => {document.getElementById("item-pic").src=`${resp.sprites.default}`})
		.catch()
}

function changeItemForOppPokemon(e){
	let cleansedText = cleanseText(e.value)
	fetch(`https://pokeapi.co/api/v2/item/${cleansedText}`)
		.then(resp => resp.json())
		.then(resp => {e.parentNode.querySelector(".opp-item").src=`${resp.sprites.default}`})
		.catch()
}

function addRow(){
	let cloneRow = formRow.cloneNode(true);
	cloneRow = changeAllNodes(cloneRow, conditions.children.length)
	conditions.appendChild(cloneRow);
	conditions.appendChild(document.getElementById('add-button'));
}

function changeCondition(e){
	let condition = e.parentNode.parentNode;
	let attackShow = condition.querySelector('.offev')
	let speedShow = condition.querySelector('.speedev')
	let defShow = condition.querySelector('.defev')
	speedShow.classList.add('invisible')
	defShow.classList.add('invisible')
	attackShow.classList.add('invisible')
	if (e.value=='Outspeed' || e.value=='Underspeed'){
		speedShow.classList.remove('invisible')
	}
	else if (e.value=='Survive'){
		attackShow.classList.remove('invisible')
	}
	else{
		defShow.classList.remove('invisible')
	}
}

function deleteRow(e){
	let condition = e.parentNode
	if (conditions.childNodes.length<=2){
		return;
	}
	let startIndex = parseInt(condition.id.match(/\d+/)[0])+1;
	condition.remove();
	for (let index = startIndex; index < conditions.childNodes.length; index++){
		let nextElem = document.querySelector(`#condition-${index}`)
		if (nextElem){
			changeAllNodes(nextElem, index-1)
		}
		else{
			return
		}
	}
}

function expandRow(e){
	return;
}

console.log('loaded')