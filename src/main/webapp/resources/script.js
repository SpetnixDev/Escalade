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