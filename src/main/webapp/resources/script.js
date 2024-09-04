function toggleContent(button, event) {
	event.preventDefault();
	
    var collapsibleContent = button.parentElement.nextElementSibling;
    
    while (collapsibleContent) {
        if (collapsibleContent.classList.contains("collapsible-content")) {
            break;
        }
        
        collapsibleContent = collapsibleContent.nextElementSibling;
    }

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
			    var overlay = document.createElement('div');
			    overlay.classList.add('overlay');
			    document.body.appendChild(overlay);
				
                var modal = document.createElement('div');
                modal.classList.add('modal-overlay');
                modal.innerHTML = `
                    <div class="modal-content">
                        <p>Accepter ou refuser la demande de réservation?</p>
                        <div class="modal-buttons">
                            <button id="accept-button" class="button green-button px-3">Accepter</button>
                            <button id="decline-button" class="button red-button px-3">Refuser</button>
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
		.then(res => location.reload())
		.catch(error => console.error('Erreur lors de la requête:', error));
	});
});

document.querySelectorAll(".official-button").forEach(button => {
	button.addEventListener("click", e => {
		const pathArray = window.location.pathname.split('/');
        const siteId = pathArray[pathArray.length - 1];
		
		fetch("/Escalade/updateofficialsite", {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ siteId: siteId })
		})
		.then(response => response.text())
		.then(res => location.reload())
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
		.then(res => location.reload())
		.catch(error => console.error('Erreur lors de la requête:', error));
	});
});

function addCommentListeners() {
	document.querySelectorAll(".edit-comment").forEach(button => {
		button.addEventListener("click", e => {
			const commentId = e.target.dataset.commentId;
			const commentContent = e.target.dataset.content;
			
			var overlay = document.createElement('div');
		    overlay.classList.add('overlay');
		    document.body.appendChild(overlay);
			
	        var modal = document.createElement('div');
	        modal.classList.add('modal-overlay');
	        modal.innerHTML = `
	            <div class="modal-content">
	                <div class="modal-buttons">
	                    <textarea class="comment-field col-12 mb-2" name="commentmodificationarea" oninput="autoAdjustHeight(this)" placeholder="Ecrivez un commentaire ici...">${commentContent}</textarea>
	                    <button class="button px-3" id="cancel-editing" name="cancel-editing">Annuler</button>
						<button class="button px-3" id="modify-comment" name="modify-comment">Modifier</button>
	                </div>
	            </div>
	        `;
	        document.body.appendChild(modal);
	        
	        document.getElementById('cancel-editing').addEventListener('click', function() {
				document.body.removeChild(document.querySelector('.overlay'));
				document.body.removeChild(document.querySelector('.modal-overlay'));
	        });
	
	        document.getElementById('modify-comment').addEventListener('click', function() {
				const newCommentContent = document.querySelector("textarea[name='commentmodificationarea']").value;

                if (newCommentContent !== commentContent && newCommentContent.trim() !== "") {
                    fetch("/Escalade/modifycomment", {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({ 
                            commentId: commentId,
                            comment: newCommentContent
                        })
                    })
                    .then(data => {
                        const commentBox = e.target.closest('.comment-box');
                        commentBox.querySelector('.comment-content').textContent = newCommentContent;
                        const editButton = commentBox.querySelector('.edit-comment');
                        editButton.dataset.content = newCommentContent;
                        document.body.removeChild(document.querySelector('.overlay'));
                        document.body.removeChild(document.querySelector('.modal-overlay'));
                    })
                    .catch(error => console.error('Erreur lors de la modification du commentaire:', error));
                }
	        });
		});
	});

	document.querySelectorAll(".delete-comment").forEach(button => {
		button.addEventListener("click", e => {
			const commentId = e.target.dataset.commentId;
			
			fetch("/Escalade/deletecomment", {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({ commentId: commentId })
			})
			.then(response => response.text())
			.then(res => location.reload())
			.catch(error => console.error('Erreur lors de la requête:', error));
		});
	});
}

document.addEventListener("DOMContentLoaded", function() {
    const sendCommentButton = document.querySelector("#send-comment");
    const commentTextarea = document.querySelector(".comment-area textarea[name='commentarea']");
    const commentsContainer = document.querySelector(".comments-container");
    
    addCommentListeners();

	if (sendCommentButton) {
		sendCommentButton.addEventListener("click", function() {
	        const comment = commentTextarea.value;
	        
	        if (comment === "") return;
	
	        const pathArray = window.location.pathname.split('/');
	        const siteId = pathArray[pathArray.length - 1];
	
	        fetch("/Escalade/sendcomment", {
	            method: 'POST',
	            headers: {
	                'Content-Type': 'application/json'
	            },
	            body: JSON.stringify({ 
	                comment: comment,
	                siteId: siteId
	            })
	        })
	        .then(response => response.json())
			.then(data => {
			    const comments = data.comments;
			    const isMember = data.isMember;
			
			    commentTextarea.value = '';
			
			    while (commentsContainer.children.length > 2) {
			        commentsContainer.removeChild(commentsContainer.lastChild);
			    }
			
			    comments.forEach(comment => {
			        const commentBox = document.createElement('div');
			        commentBox.classList.add('bg-light', 'row', 'm-0', 'py-2', 'px-1', 'rounded');
			        commentBox.innerHTML = `
			            <div class="col-12 col-sm-9">
							<p class="m-0 blue">${comment.user.firstName} ${comment.user.lastName} <span class="comment-date">- ${comment.date}</span></p>
			                <p class="comment-content m-0">${comment.content}</p>
						</div>
			            ${isMember ? `
			                <div class="col-12 col-sm-3 pt-2 py-sm-0 d-flex flex-row flex-sm-column justify-content-between justify-content-sm-center align-items-center align-items-sm-end gap-1">
								<button class="button edit-comment px-3 h-auto d-flex align-items-center justify-content-center" id="edit-comment" name="edit-comment" data-comment-id="${comment.id}" data-content="${comment.content}">Modifier</button>
								<button class="button red-button delete-comment px-3 h-auto d-flex align-items-center justify-content-center" id="delete-comment" name="delete-comment" data-comment-id="${comment.id}">Supprimer</button>
							</div>
			            ` : ''}
			        `;
			        commentsContainer.appendChild(commentBox);
			    });
			
			    document.querySelector(".comments-title").innerHTML = `${comments.length} commentaire(s)`;
			    addCommentListeners();
			})
			.catch(error => console.error('Erreur lors de l\'envoi du commentaire:', error));
	    });
	}
});

function autoAdjustHeight(textarea) {
    textarea.style.height = 'auto';
    textarea.style.height = textarea.scrollHeight + 'px';
}