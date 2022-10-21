$(async function() {
    await ShowUser();
});
async function ShowUser() {
    fetch("http://localhost:8080/api/user")
        .then(res => res.json())
        .then(data => {
            let roles = data.roles.map(role => " " + role.name.substring(5));
            $('#header_text').append(data.email + " with roles: " + roles);

            let user = `$(
            <tr>
                <td>${data.id}</td>
                <td>${data.first_name}</td>
                <td>${data.last_name}</td>
                <td>${data.age}</td>
                <td>${data.email}</td>
                <td>${roles}</td>)`;
            $('#UserInfo').append(user);
            //<a th:case="'yes'" class="nav-link" href="/admin" aria-controls="v-pills-home" aria-selected="true">Admin</a>
            //<a class="nav-link active" href="/user" aria-controls="v-pills-profile" aria-selected="false">User</a>

            let nav_admin = `<a class="nav-link" href="/admin" aria-controls="v-pills-home" aria-selected="false">Admin</a>`;
            let nav_user = `<a class="nav-link active" href="#AboutCurrentUser" aria-controls="v-pills-profile" aria-selected="true">User</a>`;

            if (data.roles[0].name == "ROLE_ADMIN") {
                $('#v-pills-tab').append(nav_admin);
            }
            $('#v-pills-tab').append(nav_user);
        });
}