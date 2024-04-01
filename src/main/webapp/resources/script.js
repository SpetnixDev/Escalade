function toggleContent(button) {
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
};

document.querySelectorAll(".availability-button").forEach(button => {
	button.addEventListener("click", e => {
        const topoId = e.target.dataset.topoId;
        const topoAvailable = e.target.dataset.available;
        
        fetch("/Escalade/updatetopo", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ topoId: topoId, topoAvailable: topoAvailable })
        })
        .then(result => result.json())
        .then(res => {
            document.getElementById("available-" + topoId).innerHTML = res.available;
        });
    });
});