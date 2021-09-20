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
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/css/calculator.css"> <!-- change to match your file/naming structure -->
    
</head>
<body>
	<h1>Pokémon EV Calculator</h1>
	<form action="/" method="POST"  autocomplete="off">
		<div class="input-row">
			<div class="btn-group" role="group" aria-label="Basic radio toggle button group">
				<input type="radio" class="btn-check" name="generation" id="gen8" value="8" checked>
				<label class="btn btn-outline-secondary" for="gen8">SS</label>
				<input type="radio" class="btn-check" name="generation" id="gen7" value="7">
				<label class="btn btn-outline-secondary" for="gen7">SM</label>
				<input type="radio" class="btn-check" name="generation" id="gen6" value="6">
				<label class="btn btn-outline-secondary" for="gen6">XY</label>
				<input type="radio" class="btn-check" name="generation" id="gen5" value="5">
				<label class="btn btn-outline-secondary" for="gen5">BW</label>
<!--				<input type="radio" class="btn-check" name="generation" id="gen4" value="4">
 				<label class="btn btn-outline-secondary" for="gen4">DPP</label>
				<input type="radio" class="btn-check" name="generation" id="gen3" value="3">
				<label class="btn btn-outline-secondary" for="gen3">ADV</label>  -->
			</div>
		</div>
		<div class="form-div">
			<div class="left-pokemon">
				<img id="pokemon-pic" src="" alt=""/>
				<img id="item-pic" src="" alt=""/>
				<div class="type-pokemon"></div>
				<div class="input-row">
					<label for="calcpokemon">Pokémon:</label>
					<div class="form-input-with-autocomplete">
			   			<input type="text" class="medium-text" name="calcpokemon" oninput="autoComplete(this)"/>
			   		   	<div class="item-autocomplete"></div>
			   		</div>
			   	</div>
		   		<div class="input-row">
		   			<label for="item">Item:</label>
		   			<div class="form-input-with-autocomplete">
			   			<input type="text" class="medium-text" name="item" oninput="autoComplete(this)"/>
			   			<div class="item-autocomplete"></div>
		   			</div>
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
		   		<div class="input-row">
			   		<div class="btn-group" role="group" aria-label="Basic radio toggle button group">
					  <input type="radio" class="btn-check" name="single-double" id="single-1" value="single" checked>
					  <label class="btn btn-outline-secondary" for="single-1">Singles</label>
					  <input type="radio" class="btn-check" name="single-double" id="double-1" value="double">
					  <label class="btn btn-outline-secondary" for="double-1">Doubles</label>
					</div>
		   		</div>
		   		<div class="input-row">
		   			<label for="status">Status:</label>
		   			<select name="status">
		   				<c:forEach var="stat" items="${status}" varStatus="loop">
		   					<option value="${stat}" <c:if test="${loop.first}">selected</c:if>><c:out value="${stat}"/></option>
		   				</c:forEach>
		   			</select>
		   		</div>
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
		   				<div class="type-pokemon"></div>
		   				<div class="input-row">
			   				<label for="pokemon-1">Opp. Pokémon:</label>
			   				<div class="form-input-with-autocomplete">
			   					<input type="text" class="medium-text" name="pokemon-1" oninput="autoComplete(this)">
		   					   	<div class="item-autocomplete"></div>
	   						</div>
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
		   				   	<div class="form-input-with-autocomplete">	
		   						<input type="text" class="medium-text" name="item-1" oninput="autoComplete(this)">
		   					   	<div class="item-autocomplete"></div>
	   						</div>	   				
	   					</div>
		   				<div class="input-row">
		   					<label for="level-1">Opp. Level:</label>
		   					<input class="short-number" type="number" name="level-1" min="1" max="100" value="100"/>
		   				</div>
		   				<div class="input-row">
		   					<label for="nature-1">Nature:</label>
			   				<select name="nature-1">
		   						<c:forEach var="nature" items="${natures}" varStatus="loop">
		   							<option value="${nature.identifier}" <c:if test="${loop.first}">selected</c:if>><c:out value="${nature.identifier}"/></option>
		   						</c:forEach>
		   					</select>
	   					</div>
	   					<div class="input-row">
	   						<label for="hp-percentage-1">HP:</label>
	   						<input class="short-number" type="text" name="hp-percentage-1" value="100"/>
	   					</div>
	   				</div>
	   				<div class="cell">
	   					<label for="move-1">Move:</label>
		   			   	<div class="form-input-with-autocomplete">	
		   					<input type="text" class="medium-text" name="move-1" oninput="autoComplete(this)">
		   				   	<div class="item-autocomplete"></div>
	   					</div>
	   					<div class="btn-group btn-group-sm" role="group" aria-label="Basic checkbox toggle button group">
		   					<input type="checkbox" class="btn-check" id="crit-1" name="crit-1" autocomplete="off">
							<label class="btn btn-outline-secondary" for="crit-1">Crit</label>
						</div>
						<label for="status">Status:</label>
		   				<select name="status-1">
		   				<c:forEach var="stat" items="${status}" varStatus="loop">
		   					<option value="${stat}" <c:if test="${loop.first}">selected</c:if>><c:out value="${stat}"/></option>
		   				</c:forEach>
		   			</select>
	   				</div>
	   				<div class="cell">		
		   				<div class="defev invisible">
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
		   				<div class="speedev">
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
	   						<div class="btn-group btn-group-sm" role="group" aria-label="Basic checkbox toggle button group">
							  <input type="checkbox" class="btn-check" id="screen-1" name="screen-1" autocomplete="off">
							  <label class="btn btn-outline-secondary" for="screen-1">Screens</label>

							  <input type="checkbox" class="btn-check" id="your-tailwind-1" name="your-tailwind-1" autocomplete="off">
							  <label class="btn btn-outline-secondary" for="your-tailwind-1">Tailwind (You)</label>
							  
							  <input type="checkbox" class="btn-check" id="foe-tailwind-1" name="foe-tailwind-1" autocomplete="off">
							  <label class="btn btn-outline-secondary" for="foe-tailwind-1">Tailwind (Foe)</label>				
							</div>
							<div class="btn-group btn-group-sm" role="group" aria-label="Basic checkbox toggle button group">
							  <input type="checkbox" class="btn-check" id="hh-1" autocomplete="off">
							  <label class="btn btn-outline-secondary" for="hh-1">Helping Hand</label>
							
							  <input type="checkbox" class="btn-check" id="flower-gift-1" autocomplete="off">
							  <label class="btn btn-outline-secondary" for="flower-gift-1">Flower Gift</label>
							  
							  <input type="checkbox" class="btn-check" id="power-spot-1" autocomplete="off">
							  <label class="btn btn-outline-secondary" for="power-spot-1">Power Spot</label>
							  
							  <input type="checkbox" class="btn-check" id="battery-1" autocomplete="off">
							  <label class="btn btn-outline-secondary" for="battery-1">Battery</label>
							</div>

	   							
	   					</div>
	   				</div>
	   				<img class="delete-button" src="/assets/garbage.png" alt="delete button" onClick="deleteRow(this)"/>
	   			</div>
	   			<img id="add-button" class='add-button' src="/assets/plus.png" alt="plus button" onClick="addRow()"/>
	   		</div>
	   	</div>
	   	<div class="centerizer">
	   		<button class="btn btn-primary submit-button">Calculate</button>
   		</div>
   	</form>
   	<script src="js/formManage.js"></script>
</body>
</html>

