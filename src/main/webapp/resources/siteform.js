let sectorCount = 1;
let routeCount = 1;
let pitchCount = 1;

document.addEventListener("DOMContentLoaded", function() {
    window.addSector = function() {
		sectorCount++;
		
		const sectorTemplate = document.createElement('div');
		sectorTemplate.className = "box collapsible";
		sectorTemplate.innerHTML = `
            <div class="sector" data-id="${sectorCount}">
                <button class="button remove-button red-button" data-for="sector" onclick="removeSector(this)" disabled}>X</button>
                <input class="research-input half-length" type="text" placeholder="Nom du secteur" name="sectorName" required>
                <button class="button" onclick="toggleContent(this, event)">+</button>
            	<input class="research-input block" type="text" placeholder="Description" id="sectorDesc" name="sectorDesc" required>
			</div>
            <div class="collapsible-content vertical-centered">
                <h3>Voies</h3>
                
				<div class="routes">
	                <div class="box collapsible extended route-box">
	                    <div class="route" data-id="${++routeCount}" data-sector-id="${sectorCount}">
	                        <button class="button remove-button red-button" data-for="route-${sectorCount}" onclick="removeRoute(this)" disabled>X</button>
	                        <input class="research-input half-length" type="text" placeholder="Nom de la voie" name="routeName" required>
	                        <button class="button" onclick="toggleContent(this, event)">+</button>
	                    	<input class="research-input block" type="text" placeholder="Description" id="routeDesc" name="routeDesc" required>
						</div>
	                
		                <div class="collapsible-content vertical-centered">
							<h3>Longueurs</h3>
							
							<div class="pitches">
		                		<div class="box collapsible extended pitch-box">
									<div class="pitch" data-id="${++pitchCount}" data-route-id="${routeCount}">
										<button class="button remove-button red-button" data-for="pitch-${routeCount}" onclick="removePitch(this)" disabled>X</button>
								
										<input class="research-input quarter-length" type="text" placeholder="Nom de la longueur" id="pitchName" name="pitchName" required>
										<input class="research-input quarter-length" type="text" placeholder="Longueur en mètres" id="pitchLength" name="pitchLength" required>
										<input class="research-input quarter-length" type="text" placeholder="Cotation" id="pitchDifficulty" name="pitchDifficulty" required>
										<input class="research-input block" type="text" placeholder="Description" id="pitchDesc" name="pitchDesc" required>
									</div>
								</div>
							</div>
								
							<button type="button" class="button" onclick="addPitch(this)" data-route-id="${routeCount}">Ajouter une longueur</button>
						</div>
	                </div>
				</div>
	            
	            <button type="button" class="button" onclick="addRoute(this)" data-sector-id="${sectorCount}">Ajouter une voie</button>
            </div>
        `;
		
		document.getElementById('sectors').appendChild(sectorTemplate);
		
		document.querySelectorAll('.remove-button').forEach(button => {
			if (button.dataset.for === "sector") {
				button.disabled = false;
			}
		});
    };

    window.removeSector = function(button) {
		var sectors = document.querySelectorAll('.sector').length;
		
        if (sectors > 1) {
            button.closest('.box.collapsible').remove();
            
            if (sectors == 2) {
				document.querySelectorAll('.remove-button').forEach(button => {
			if (button.dataset.for === "sector") {
				button.disabled = true;
			}
		});
			}
        } else {
			document.querySelectorAll('.remove-button').forEach(button => {
			if (button.dataset.for === "sector") {
				button.disabled = true;
			}
		});
        }
    };

    window.addRoute = function(button) {
		routeCount++;
		
		const sectorId = button.dataset.sectorId;
		
		const routeTemplate = document.createElement('div');
		routeTemplate.className = "box collapsible extended route-box";
		routeTemplate.innerHTML = `
            <div class="route" data-id="${routeCount}" data-sector-id="${sectorId}">
                <button class="button remove-button red-button" data-for="route-${sectorId}" onclick="removeRoute(this)">X</button>
                <input class="research-input half-length" type="text" placeholder="Nom de la voie" name="routeName" required>
                <button class="button" onclick="toggleContent(this, event)">+</button>
            	<input class="research-input block" type="text" placeholder="Description" id="routeDesc" name="routeDesc" required>
			</div>
            <div class="collapsible-content vertical-centered">
				<h3>Longueurs</h3>
				
				<div class="pitches">
	        		<div class="box collapsible extended pitch-box">
						<div class="pitch" data-id="${++pitchCount}" data-route-id="${routeCount}">
							<button class="button remove-button red-button" data-for="pitch-${routeCount}" onclick="removePitch(this)" disabled>X</button>
					
							<input class="research-input quarter-length" type="text" placeholder="Nom de la longueur" id="pitchName" name="pitchName" required>
							<input class="research-input quarter-length" type="text" placeholder="Longueur en mètres" id="pitchLength" name="pitchLength" required>
							<input class="research-input quarter-length" type="text" placeholder="Cotation" id="pitchDifficulty" name="pitchDifficulty" required>
							<input class="research-input block" type="text" placeholder="Description" id="pitchDesc" name="pitchDesc" required>
						</div>
					</div>
				</div>
					
				<button type="button" class="button" onclick="addPitch(this)" data-route-id="${routeCount}">Ajouter une longueur</button>
			</div>
        `;
         
		button.previousElementSibling.appendChild(routeTemplate);
				 
		document.querySelectorAll('.remove-button').forEach(button => {
	        if (button.dataset.for === "route-" + sectorId) {
	            button.disabled = false;
			}
		});
	};
    
    window.removeRoute = function(button) {
		const sector = button.dataset.for;
		const sectorId = sector.split("-")[1];
		
		console.log("Sector Id: " + sectorId);
		
		var routes = document.querySelectorAll(`.route[data-sector-id='${sectorId}']`).length;
		
        if (routes > 1) {
            button.closest('.box.collapsible').remove();
            
            if (routes == 2) {
				document.querySelectorAll('.remove-button').forEach(button => {
	                if (button.dataset.for === sector) {
	                    button.disabled = true;
	                }
	            });
			}
        } else {
			document.querySelectorAll('.remove-button').forEach(button => {
                if (button.dataset.for === sector) {
                    button.disabled = true;
                }
            });
        }
    };

	window.addPitch = function(button) {
		pitchCount++;
		
		const routeId = button.dataset.routeId;
		
		const pitchTemplate = document.createElement('div');
		pitchTemplate.className = "box collapsible extended pitch-box";
		pitchTemplate.innerHTML = `
            <div class="pitch" data-id="${pitchCount}" data-route-id="${routeId}">
				<button class="button remove-button red-button" data-for="pitch-${routeId}" onclick="removePitch(this)" disabled>X</button>
		
				<input class="research-input quarter-length" type="text" placeholder="Nom de la longueur" id="pitchName" name="pitchName" required>
				<input class="research-input quarter-length" type="text" placeholder="Longueur en mètres" id="pitchLength" name="pitchLength" required>
				<input class="research-input quarter-length" type="text" placeholder="Cotation" id="pitchDifficulty" name="pitchDifficulty" required>
				<input class="research-input block" type="text" placeholder="Description" id="pitchDesc" name="pitchDesc" required>
			</div>
        `;
         
		button.previousElementSibling.appendChild(pitchTemplate);
				 
		document.querySelectorAll('.remove-button').forEach(button => {
	        if (button.dataset.for === "pitch-" + routeId) {
	            button.disabled = false;
			}
		});
	};
    
    window.removePitch = function(button) {
		const route = button.dataset.for;
		const routeId = route.split("-")[1];
		
		console.log("Route Id: " + routeId);
		
		var pitches = document.querySelectorAll(`.pitch[data-route-id='${routeId}']`).length;
		
        if (pitches > 1) {
            button.closest('.box.collapsible').remove();
            
            if (pitches == 2) {
				document.querySelectorAll('.remove-button').forEach(button => {
	                if (button.dataset.for === route) {
	                    button.disabled = true;
	                }
	            });
			}
        } else {
			document.querySelectorAll('.remove-button').forEach(button => {
                if (button.dataset.for === route) {
                    button.disabled = true;
                }
            });
        }
    };
});
