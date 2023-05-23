let usersTable = $('.table');

function AddUser() {
     // Получение таблицы и формы для добавления пользователя
     let rawName = $('#inputName').val();
     let rawLastName = $('#inputLastName').val();
     let rawAge = $('#inputAge').val();
     let rawEmail = $('#inputEmail').val();
     let rawPhone = $('#inputPhone').val();

     // Создаем новый объект с данными нового пользователя
     let User = {
         name: rawName,
         lastName: rawLastName,
         age : rawAge,
         email: rawEmail,
         phone : rawPhone
     }

     // Отправляем данные нового пользователя на сервер в формате JSON с помощью AJAX-запроса
     $.ajax({
         type: 'POST',
         url: 'JSONHandlerServlet',
         data: JSON.stringify(User),
         dataType: 'json',
         contentType: 'application/json',
         success: function(data) {
             // Если AJAX-запрос выполнен успешно, создаем новую строку таблицы с данными нового пользователя
         },
         error: function(jqXHR, textStatus, errorThrown) {
             console.log(errorThrown);
         }
     });

    // Добавляем новую строку в таблицу
    usersTable.append(newRow);
    // Очищаем поля формы
    $('#inputName').val('');
    $('#inputLastName').val('');
    $('#inputAge').val('');
    $('#inputEmail').val('');
    $('#inputPhone').val('');
}

function RefreshUsers() {
    $.ajax({
        url: 'JSONHandlerServlet',
        type: "GET",
        dataType: "json",
        success: function(data) {
            $.each(data, function(i, user) {
                let row = $("<tr>");
                row.append($("<td>").text(user.name));
                row.append($("<td>").text(user.lastName));
                row.append($("<td>").text(user.age));
                row.append($("<td>").text(user.email));
                row.append($("<td>").text(user.phone));
                usersTable.append(row);
            });
            },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}