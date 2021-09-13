<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pokémon EV/IV calculator</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/calculator.css"> <!-- change to match your file/naming structure -->
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<h1>Pokémon Calculator</h1>
	<form action="/" method="POST">
		<img id="pokemon-pic" src="" alt="Pokémon"/>
		<img id="item-pic" src="" alt="Pokémon"/>
		
		<div class="input-row">
			<label for="calcpokemon">Pokémon:</label>
	   		<input type="text" name="calcpokemon" onChange="getImageForPokemon(this)"/>
	   	</div>
   		<div class="input-row">
   			<label for="item">Item:</label>
   			<input type="text" name="item" onChange="changeItemForPokemon(this)"/>
   			<div class="item-autocomplete"></div>
   		</div>
   		<div class="input-row">
   			<label for="ability">Ability: </label>
   			<select name="ability" class="your-ability">
	   		</select>
   		</div>
   		<div class="input-row">
   			<label for="level">Level:</label>
   			<input class="short-number" name="level" type="number" min="1" max="100" value="100"/>
   			
   		</div>
   		<div class="input-row">
   		   	<label for="nature-pokemon">Nature:</label>
	   		<select name="nature-pokemon">
	   			<option value="undecided" selected>Not important</option>
	   			<c:forEach var="nature" items="${natures }">
	   				<option value="${nature.identifier }"><c:out value="${nature.identifier}"/></option>
	   			</c:forEach>
	   		</select>
   		</div>
   		<div class="conditions">
   			<div id="condition-1" class="cells">
   				<div class="cell">
	   				<select name="condition-1" onChange= "changeCondition(this)">
	   					<c:forEach var="condition" items="${conditions}">
	   						<option value="${condition}"><c:out value="${condition }"/></option>
	   					</c:forEach>
	   				</select>
	   			</div>
	   			<div class="cell">
	   				<img class="opp-pokemon" src="" alt="">
	   				<div class="input-row">
		   				<label for="pokemon-1">Opp. Pokémon:</label>
		   				<input type="text" name="pokemon-1" onChange="changeOppPokemon(this)">
	   				</div>
	   			</div>
	   			<div class="cell">
	   				<div class="input-row">
	   					<label for="ability-1">Opp. Ability:</label>
	   					<select name="ability-1" class="opp-ability">
	   					</select>
	   				</div>
	   				<div class="input-row">
	   					<img class="opp-item" src="">
	   					<label for="item-1">Opp. Item:</label>
	   					<input type="text" name="item-1" onChange="changeItemForOppPokemon(this)">
	   				</div>
	   				<div class="input-row">
	   					<label for="level-1">Opp. Level:</label>
	   					<input class="short-number" type="number" name="level-1" min="1" max="100" value="100"/>
	   				</div>
	   				<div class="input-row">
	   					<label for="nature-1">Nature:</label>
		   				<select name="nature-1">
	   						<c:forEach var="nature" items="${natures }" varStatus="loop">
	   							<option value="${nature.identifier }" <c:if test="${loop.first}">selected</c:if>><c:out value="${nature.identifier}"/></option>
	   						</c:forEach>
	   					</select>
   					</div>
   					<div class="input-row">
   						<label for="hp-percentage-1">HP:</label>
   						<input type="text" name="hp-percentage-1" value="100"/>
   					</div>
   				</div>
   				<div class="cell">		
	   				<div class="defev">
	   					<div class="display-ev-iv">
	   						<h6>HP</h6>
		   					<label for="hp-1">EV:</label>
		   					<input class="short-number" type="number" name="hp-1" min="0" max="252" value="0"/>
		   					<label for="hp-iv-1">IV:</label>
		   					<input class="short-number" type="number" name="hp-iv-1" min="0" max="31" value="31"/>
	   					</div>
	
	   					<div class="display-ev-iv">
	   						<h6>Def/Spd</h6>
	   						<label for="def-1">EV:</label>
	   						<input class="short-number" type="number" name="def-1" min="0" max="252" value="0"/>
	   						<label for="def-iv-1">IV:</label>
	   						<input class="short-number" type="number" name="def-iv-1" min="0" max="31" value="31"/>
	   					</div>
	   				</div>
	   				<div class="invisible offev">
	   					<div class="display-ev-iv">
	   						<h6>Atk/SpA</h6>
	   						<label for="atk-1">EV:</label>
	   						<input class="short-number" type="number" name="atk-1" min="0" max="252" value="0"/>
	   						<label for="atk-iv-1">IV:</label>
	   						<input class="short-number" type="number" name="atk-iv-1" min="0" max="31" value="31"/>
	   					</div>
	   				</div>
	   				<div class="speedev invisible">
	   					<div class="display-ev-iv">
	   						<h6>Speed</h6>
	   						<label for="speed-1">EV:</label>
	   						<input class="short-number" type="number" name="speed-1" min="0" max="252" value="0"/>
	   						<label for="speed-iv-1">IV:</label>
	   						<input class="short-number" type="number" name="speed-iv-1" min="0" max="31" value="31"/>
	   					</div>
	   				</div>
	   			</div>
	   			<div class="cell">	
	   				<div class="boost-selector">
		   				<label for="boost-you-1">Your boosts:</label>
		   				<select name="boost-you-1">
				   			<c:forEach var="boost" items="${boosts}">
				   				<option value="${boost}"<c:if test="${boost eq '0'}"> selected</c:if>><c:out value="${boost}"/></option>
				   			</c:forEach>
				   		</select>
					</div>
				   	<div class="boost-selector">
		   				<label for="boost-opp-1">Opp boosts:</label>
		   				<select name="boost-opp-1">
				   			<c:forEach var="boost" items="${boosts}">
				   				<option value="${boost}"<c:if test="${boost eq '0'}"> selected</c:if>><c:out value="${boost}"/></option>
					   		</c:forEach>
					   	</select>
					</div>
				</div>
   				<img id="expand-button" class='add-button' src="/assets/plus.png" alt="plus button" onClick="expandRow(this)"/>
   				<div class="cell">
   					<div>
   						<h4>Field Effects</h4>
   						<label for="terrain-1">Terrain:</label>
   						<select name="terrain-1">
   							<c:forEach var="terrain" items="${terrains}">
   								<option value="${terrain}"><c:out value="${terrain}"/></option>
   							</c:forEach>
   						</select>
   						<label for="weather-1">Weather:</label>
   						
   						<select name="weather-1">
   							<c:forEach var="weather" items="${weathers}">
   								<option value="${weather}"><c:out value="${weather}"/></option>
   							</c:forEach>
   						</select>
						<p>Screens:</p>
    					<input type="radio" id="screen-yes-1" name="screen-1" value="true"/>
   							<label for="screen-yes-1">Yes</label>
   						<input type="radio" id="screen-no-1" name="screen-1" value="false" checked/>
   							<label for="screen-no-1">No</label>
   							
   					</div>
   				</div>
   				<img class="delete-button" src="/assets/garbage.png" alt="delete button" onClick="deleteRow(this)"/>
   			</div>
   			<img id="add-button" class='add-button' src="/assets/plus.png" alt="plus button" onClick="addRow()"/>
   			
   		</div>
   		<button class="btn btn-primary">Calculate</button>
   	</form>
   	<c:forEach var="stat" items="${calcedstat}">
   		<p><c:out value="${stat[1]}: ${stat[0]}"/></p>
   	</c:forEach>
   	
   	<div class="suggestion-box">
   	<p>Suggested Nature: <c:out value="${suggestedNature.getIdentifier()}"/></p>
   	<c:forEach var="suggestion" items="${suggestionEV}">
   		<c:out value="${suggestion }"/>
   	</c:forEach>
   	</div>
</body>
</html>

