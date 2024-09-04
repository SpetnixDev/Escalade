let sectorCount = 1;
let routeCount = 1;
let pitchCount = 1;

document.addEventListener("DOMContentLoaded", function() {
    window.addSector = function() {
		sectorCount++;
		
		const sectorTemplate = document.createElement('div');
		sectorTemplate.className = "bg-light rounded row p-2 m-0 collapsible sector";
		sectorTemplate.dataset.id = sectorCount;
		sectorTemplate.innerHTML = `
			<div class="d-flex flex-row gap-2 p-0 mb-2">
				<button type="button" class="button px-3 remove-button red-button" data-for="sector" onclick="removeSector(this)" disabled>X</button>
			
				<input class="form-control flex-fill" type="text" placeholder="Nom du secteur" id="sectorName" name="sectorName" required>
				
				<button class="button px-3" onclick="toggleContent(this, event)">+</button>
			</div>
			
			<input class="form-control" type="text" placeholder="Description" id="sectorDesc" name="sectorDesc" required>
		
			<div class="collapsible-content p-0 mt-2">
				<h3 class="text-center my-2">Voies</h3>
				
				<hr />
				
				<div class="d-flex flex-column gap-2" id="routes-${sectorCount}">
					<div class="bg-lightgray rounded row m-0 p-2 collapsible route" data-id="${++routeCount}" data-sector-id="${sectorCount}">
						<div class="d-flex flex-row gap-2 p-0 mb-2">
							<button type="button" class="button px-3 remove-button red-button" data-for="route-${sectorCount}" onclick="removeRoute(this)" disabled>X</button>
					
							<input class="form-control flex-fill" type="text" placeholder="Nom de la voie" id="routeName" name="routeName" required>
							
							<button class="button px-3" onclick="toggleContent(this, event)">+</button>
						</div>
						
						<input class="form-control" type="text" placeholder="Description" id="routeDesc" name="routeDesc" required>
						
						<div class="collapsible-content p-0 mt-2">
							<h4 class="text-center my-2">Longueurs</h4>
							
							<hr />
							
							<div class="d-flex flex-column gap-2" id="pitches-${routeCount}">
								<div class="bg-secondary rounded row m-0 p-2 collapsible pitch" data-id="${++pitchCount}" data-route-id="${routeCount}">
									<div class="d-flex flex-column gap-2 p-0">
										<div class="d-flex flex-row gap-2 p-0">
											<button type="button" class="button px-3 remove-button red-button" data-for="pitch-${routeCount}" onclick="removePitch(this)" disabled>X</button>
									
											<input class="form-control flex-fill" type="text" placeholder="Nom de la longueur" id="pitchName" name="pitchName" required>
										</div>
										
										<div class="d-flex flex-column flex-md-row gap-2">
											<div class="flex-fill">
												<input class="form-control" type="number" min="1" placeholder="Longueur en mètres" id="pitchLength" name="pitchLength" required>
											</div>
											
											<div class="flex-fill">
												<select class="form-select" id="pitchDifficulty" name="pitchDifficulty" required>
													<option disabled selected value="">Cotation</option>
										            <option>3</option>
										            <option>4</option>
										            <option>5a</option>
										            <option>5b</option>
										            <option>5c</option>
										            <option>6a</option>
										            <option>6a+</option>
										            <option>6b</option>
										            <option>6b+</option>
										            <option>6c</option>
										            <option>6c+</option>
										            <option>7a</option>
										          	<option>7a+</option>
										          	<option>7b</option>
										          	<option>7b+</option>
										          	<option>7c</option>
										          	<option>7c+</option>
										          	<option>8a</option>
										          	<option>8a+</option>
										          	<option>8b</option>
										          	<option>8b+</option>
										          	<option>8c</option>
										          	<option>8c+</option>
										          	<option>9a</option>
												</select>
											</div>
										</div>
											
										<input class="form-control" type="text" placeholder="Description" id="pitchDesc" name="pitchDesc" required>
									</div>
								</div>
							</div>
							
							<hr />
							
							<div class="d-flex justify-content-center align-items-center">
								<button type="button" class="button px-3" onclick="addPitch(this)" data-route-id="${routeCount}">Ajouter une longueur</button>
							</div>
						</div>
					</div>
				</div>
				
				<hr />
				
				<div class="d-flex justify-content-center align-items-center">
					<button type="button" class="button px-3" onclick="addRoute(this)" data-sector-id="${sectorCount}">Ajouter une voie</button>
				</div>
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
            button.closest('.collapsible.sector').remove();
            
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
		routeTemplate.className = "bg-lightgray rounded row m-0 p-2 collapsible route";
		routeTemplate.dataset.id = routeCount;
		routeTemplate.dataset.sectorId = sectorId;
		routeTemplate.innerHTML = `
			<div class="d-flex flex-row gap-2 p-0 mb-2">
				<button type="button" class="button px-3 remove-button red-button" data-for="route-${sectorId}" onclick="removeRoute(this)" disabled>X</button>
		
				<input class="form-control flex-fill" type="text" placeholder="Nom de la voie" id="routeName" name="routeName" required>
				
				<button class="button px-3" onclick="toggleContent(this, event)">+</button>
			</div>
			
			<input class="form-control" type="text" placeholder="Description" id="routeDesc" name="routeDesc" required>
			
			<div class="collapsible-content p-0 mt-2">
				<h4 class="text-center my-2">Longueurs</h4>
				
				<hr />
				
				<div class="d-flex flex-column gap-2" id="pitches-${routeCount}">
					<div class="bg-secondary rounded row m-0 p-2 collapsible pitch" data-id="${++pitchCount}" data-route-id="${routeCount}">
						<div class="d-flex flex-column gap-2 p-0">
							<div class="d-flex flex-row gap-2 p-0">
								<button type="button" class="button px-3 remove-button red-button" data-for="pitch-${routeCount}" onclick="removePitch(this)" disabled>X</button>
						
								<input class="form-control flex-fill" type="text" placeholder="Nom de la longueur" id="pitchName" name="pitchName" required>
							</div>
							
							<div class="d-flex flex-column flex-md-row gap-2">
								<div class="flex-fill">
									<input class="form-control" type="number" min="1" placeholder="Longueur en mètres" id="pitchLength" name="pitchLength" required>
								</div>
								
								<div class="flex-fill">
									<select class="form-select" id="pitchDifficulty" name="pitchDifficulty" required>
										<option disabled selected value="">Cotation</option>
							            <option>3</option>
							            <option>4</option>
							            <option>5a</option>
							            <option>5b</option>
							            <option>5c</option>
							            <option>6a</option>
							            <option>6a+</option>
							            <option>6b</option>
							            <option>6b+</option>
							            <option>6c</option>
							            <option>6c+</option>
							            <option>7a</option>
							          	<option>7a+</option>
							          	<option>7b</option>
							          	<option>7b+</option>
							          	<option>7c</option>
							          	<option>7c+</option>
							          	<option>8a</option>
							          	<option>8a+</option>
							          	<option>8b</option>
							          	<option>8b+</option>
							          	<option>8c</option>
							          	<option>8c+</option>
							          	<option>9a</option>
									</select>
								</div>
							</div>
								
							<input class="form-control" type="text" placeholder="Description" id="pitchDesc" name="pitchDesc" required>
						</div>
					</div>
				</div>
				
				<hr />
				
				<div class="d-flex justify-content-center align-items-center">
					<button type="button" class="button px-3" onclick="addPitch(this)" data-route-id="${routeCount}">Ajouter une longueur</button>
				</div>
			</div>
        `;
         
        document.getElementById(`routes-${sectorId}`).appendChild(routeTemplate);
				 
		document.querySelectorAll('.remove-button').forEach(button => {
	        if (button.dataset.for === "route-" + sectorId) {
	            button.disabled = false;
			}
		});
	};
    
    window.removeRoute = function(button) {
		const sector = button.dataset.for;
		const sectorId = sector.split("-")[1];
		
		var routes = document.querySelectorAll(`.route[data-sector-id='${sectorId}']`).length;
		
        if (routes > 1) {
            button.closest('.collapsible.route').remove();
            
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
		pitchTemplate.className = "bg-secondary rounded row m-0 p-2 collapsible pitch";
		pitchTemplate.dataset.id = pitchCount;
		pitchTemplate.dataset.routeId = routeId;
		pitchTemplate.innerHTML = `
			<div class="d-flex flex-column gap-2 p-0">
				<div class="d-flex flex-row gap-2 p-0">
					<button type="button" class="button px-3 remove-button red-button" data-for="pitch-${routeId}" onclick="removePitch(this)" disabled>X</button>
			
					<input class="form-control flex-fill" type="text" placeholder="Nom de la longueur" id="pitchName" name="pitchName" required>
				</div>
				
				<div class="d-flex flex-column flex-md-row gap-2">
					<div class="flex-fill">
						<input class="form-control" type="number" min="1" placeholder="Longueur en mètres" id="pitchLength" name="pitchLength" required>
					</div>
					
					<div class="flex-fill">
						<select class="form-select" id="pitchDifficulty" name="pitchDifficulty" required>
							<option disabled selected value="">Cotation</option>
				            <option>3</option>
				            <option>4</option>
				            <option>5a</option>
				            <option>5b</option>
				            <option>5c</option>
				            <option>6a</option>
				            <option>6a+</option>
				            <option>6b</option>
				            <option>6b+</option>
				            <option>6c</option>
				            <option>6c+</option>
				            <option>7a</option>
				          	<option>7a+</option>
				          	<option>7b</option>
				          	<option>7b+</option>
				          	<option>7c</option>
				          	<option>7c+</option>
				          	<option>8a</option>
				          	<option>8a+</option>
				          	<option>8b</option>
				          	<option>8b+</option>
				          	<option>8c</option>
				          	<option>8c+</option>
				          	<option>9a</option>
						</select>
					</div>
				</div>
					
				<input class="form-control" type="text" placeholder="Description" id="pitchDesc" name="pitchDesc" required>
			</div>
        `;
         
		document.getElementById(`pitches-${routeId}`).appendChild(pitchTemplate);
				 
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
            button.closest('.collapsible.pitch').remove();
            
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
