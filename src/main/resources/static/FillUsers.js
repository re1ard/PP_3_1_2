$(async function() {
    await FillUsers();
});
async function FillUsers() {
   const table = $('#AllUsers');
   table.empty();
   fetch("http://localhost:8080/api/users")
       .then(res => res.json())
       .then(data => {
           data.forEach(user => {
               let current_tr = `$(
               <tr>
                    <td>${user.id}</td>
                    <td>${user.first_name}</td>
                    <td>${user.last_name}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.roles.map(role => " " + role.name.substring(5))}</td>
                    <td>
                        <button type="button" class="btn btn-primary" data-toggle="modal" id="buttonEdit"
                        data-action="edit" data-id="${user.id}" data-target="#edit">Edit</button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger" data-toggle="modal" id="buttonDelete"
                        data-action="delete" data-id="${user.id}" data-target="#delete">Delete</button>
                    </td>
               </tr>)`;
               table.append(current_tr);
           })
       });
}