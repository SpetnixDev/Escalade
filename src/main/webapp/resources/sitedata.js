function collectData() {
    const site = {
        siteName: document.getElementById('siteName').value,
        description: document.getElementById('description').value,
        location: document.getElementById('location').value,
        rating: document.getElementById('rating').value,
                
        sectors: []
    };

    document.querySelectorAll('.sector').forEach(sector => {
        const sectorData = {
            id: sector.getAttribute('data-id'),
            sectorName: sector.querySelector('input[name="sectorName"]').value,
            sectorDesc: sector.querySelector('input[name="sectorDesc"]').value,
            
            routes: []
        };

        sector.querySelectorAll('.route').forEach(route => {
            const routeData = {
                id: route.getAttribute('data-id'),
                routeName: route.querySelector('input[name="routeName"]').value,
                routeDesc: route.querySelector('input[name="routeDesc"]').value,
                
                pitches: []
            };

            route.querySelectorAll('.pitch').forEach(pitch => {
                const pitchData = {
                    id: pitch.getAttribute('data-id'),
                    pitchName: pitch.querySelector('input[name="pitchName"]').value,
                    pitchDesc: pitch.querySelector('input[name="pitchDesc"]').value,
                    pitchLength: pitch.querySelector('input[name="pitchLength"]').value,
                    pitchDifficulty: pitch.querySelector('select[name="pitchDifficulty"]').value
                };
                
                routeData.pitches.push(pitchData);
            });

            sectorData.routes.push(routeData);
        });

        site.sectors.push(sectorData);
    });

    return site;
}

document.getElementById('climbingSiteForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const siteData = collectData();

    fetch('registersite', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(siteData)
    }).then(res => res.text())
    .then(text => window.location.href = text)
    .catch(error => console.error('Error:', error));
});
