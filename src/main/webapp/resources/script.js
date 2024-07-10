function toggleContent(button, event) {
	event.preventDefault();
	
    var collapsibleContent = button.parentElement.nextElementSibling;

    if (collapsibleContent.classList.contains("active")) {
        collapsibleContent.classList.remove("active");
        button.textContent = "+";
    } else {
        collapsibleContent.classList.add("active");
        button.textContent = "-";
    }
}

function getRegionsNames() {
    var xhr = new XMLHttpRequest();
    
    xhr.open('GET', 'https://geo.api.gouv.fr/regions', true);
    
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var regions = JSON.parse(xhr.responseText);
            
            var select = document.getElementById('location');

            regions.forEach(function (region) {
                var option = document.createElement('option');
                
                option.textContent = region.nom;
                select.appendChild(option);
            });
        }
    };
    
    xhr.send();
}

window.onload = function() {
    var location = document.getElementById('location');
    
    if (location) {
        getRegionsNames();
    }
    
    var errorDiv = document.getElementById("errorDiv");
    
    if (errorDiv) {
        setTimeout(function() {
            errorDiv.classList.add("hidden");
        }, 5000);
    }
};

document.querySelectorAll(".availability-button").forEach(button => {
    const isAvailable = button.getAttribute("data-available") === "true";
    const reservationStatus = button.getAttribute("data-reservation-status");
    
    console.log(reservationStatus);

    if (isAvailable) {
        button.classList.add("red-button");
        button.classList.remove("green-button");
        button.classList.remove("orange-button");
    } else {
        if (reservationStatus === "pending" || reservationStatus === "ongoing") {
            button.classList.add("orange-button");
            button.classList.remove("green-button");
            button.classList.remove("red-button");
        } else {
            button.classList.add("green-button");
            button.classList.remove("red-button");
            button.classList.remove("orange-button");
        }
    }

    button.addEventListener("click", e => {
        const topoId = e.target.dataset.topoId;
        const resId = e.target.dataset.reservationId;

		if (reservationStatus === "") {
	        fetch("/Escalade/updatetopo", {
	            method: 'POST',
	            headers: {
	                'Content-Type': 'application/json'
	            },
	            body: JSON.stringify({ topoId: topoId, topoAvailable: isAvailable})
	        })
	        .then(result => result.json())
	        .then(res => {
	            location.reload();
	        });
		} else {
			if (reservationStatus === 'pending') {
                var modal = document.createElement('div');
                modal.classList.add('modal');
                modal.innerHTML = `
                    <div class="modal-content">
                        <p>Accepter ou refuser la demande de réservation?</p>
                        <div class="modal-buttons">
                            <button id="accept-button" class="button green-button half-sized">Accepter</button>
                            <button id="decline-button" class="button red-button half-sized">Refuser</button>
                        </div>
                    </div>
                `;
                document.body.appendChild(modal);

                document.getElementById('accept-button').addEventListener('click', function() {
                    callReservationUpdate(resId, "ongoing")
                });

                document.getElementById('decline-button').addEventListener('click', function() {
                    callReservationUpdate(resId, "refused");
                });
            } else {
				callReservationUpdate(resId, "completed");
			}
		}
    });
});

function callReservationUpdate(resId, status) {
	fetch("/Escalade/updatereservation", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ resId: resId, reservationStatus: status})
    })
    .then(result => result.json())
    .then(res => {
        location.reload();
    });
}

document.querySelectorAll(".reservation-button").forEach(button => {
	button.addEventListener("click", e => {
		const topoId = e.target.dataset.topoId;
		
		fetch("/Escalade/reservetopo", {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ topoId: topoId })
		})
		.then(response => response.text())
		.catch(error => console.error('Erreur lors de la requête:', error));
	});
});

document.querySelectorAll(".remove-topo-button").forEach(button => {
	button.addEventListener("click", e => {
		const topoId = e.target.dataset.topoId;
		
		fetch("/Escalade/removetopo", {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ topoId: topoId })
		})
		.then(response => response.text())
		.catch(error => console.error('Erreur lors de la requête:', error));
	});
});