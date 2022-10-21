$(async function() {
    await NavPanel();
});
async function NavPanel() {
    fetch("http://localhost:8080/api/user")
        .then(res => res.json())
        .then(data => {
            let roles = data.roles.map(role => " " + role.name.substring(5));
            $('#header_text').append(data.email + " with roles: " + roles);

            let nav_admin;
            let nav_user;

            if (document.URL.includes("/admin")) {
                nav_admin = `<a class="nav-link active" href="/admin" aria-controls="v-pills-home" aria-selected="false">Admin</a>`;
            } else {
                nav_admin = `<a class="nav-link" href="/admin" aria-controls="v-pills-home" aria-selected="false">Admin</a>`;
            }

            if (document.URL.includes("/user")) {
                nav_user = `<a class="nav-link active" href="/user" aria-controls="v-pills-profile" aria-selected="true">User</a>`;
            } else {
                nav_user = `<a class="nav-link" href="/user" aria-controls="v-pills-profile" aria-selected="true">User</a>`;
            }

            if (data.roles[0].name == "ROLE_ADMIN") {
                $('#v-pills-tab').append(nav_admin);
            }
            $('#v-pills-tab').append(nav_user);
        });
}