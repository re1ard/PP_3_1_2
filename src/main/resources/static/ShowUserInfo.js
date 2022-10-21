$(async function() {
    await ShowUser();
});
async function ShowUser() {
    fetch("http://localhost:8080/api/user")
        .then(res => res.json())
        .then(data => {
            let user = `$(
            <tr>
                <td>${data.id}</td>
                <td>${data.first_name}</td>
                <td>${data.last_name}</td>
                <td>${data.age}</td>
                <td>${data.email}</td>
                <td>${data.roles.map(role => " " + role.name.substring(5))}</td>)`;
            $('#UserInfo').append(user);
        });
}