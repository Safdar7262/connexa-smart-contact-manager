console.log("Script Loaded");

let currentTheme = getTheme();
applyTheme(currentTheme);

function applyTheme(theme) {

    const html = document.documentElement;

    if (theme === "dark") {
        html.classList.add("dark");
    } else {
        html.classList.remove("dark");
    }

    // Navbar theme button
    const changeThemeButton = document.querySelector("#theme_change_button");

    if (changeThemeButton) {
        const span = changeThemeButton.querySelector("span");
        if (span) {
            span.textContent = theme === "light" ? "Dark" : "Light";
        }
    }

    // Mobile sidebar theme button
    const changeThemeButtonMobile = document.querySelector("#theme_change_button_mobile");

    if (changeThemeButtonMobile) {
        const span = changeThemeButtonMobile.querySelector("span");
        if (span) {
            span.textContent = theme === "light" ? "Dark" : "Light";
        }
    }
}

function toggleTheme() {

    let theme = getTheme();

    if (theme === "dark") {
        theme = "light";
    } else {
        theme = "dark";
    }

    setTheme(theme);
    applyTheme(theme);
}

// Navbar button
const changeThemeButton = document.querySelector("#theme_change_button");
if (changeThemeButton) {
    changeThemeButton.addEventListener("click", toggleTheme);
}

// Mobile sidebar button
const changeThemeButtonMobile = document.querySelector("#theme_change_button_mobile");
if (changeThemeButtonMobile) {
    changeThemeButtonMobile.addEventListener("click", toggleTheme);
}

function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}

// Delete popup

function confirmDelete(element) {

    let id = element.getAttribute("data-id");

    let result = confirm("Are you sure you want to delete this contact?");

    if (result) {
        window.location.href = "/user/contacts/delete/" + id;
    }
}

// function previewFile(event) {
//     const image = document.getElementById('previewImage');
//     image.src = URL.createObjectURL(event.target.files[0]);
// }

function previewImage(event) {
    const file = event.target.files[0];
    const preview = document.getElementById("preview");

    if (file) {
        preview.src = URL.createObjectURL(file);
        preview.classList.remove("hidden");
    }
}