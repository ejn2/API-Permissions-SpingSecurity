# Api-permissions

Permission-api, está sendo desenvolvida para o entendimento de como podem ser atribuídas permissões aos usuários. (Spring Security).

:warning: Essa aplicação ainda está em desenvolvimento, algumas funcionalidades ainda podem sofrer alterações.

Abaixo você pode ver a tabela com as rotas das funções já disponíveis.

### URL padrão

Use a url http://localhost:8080/api/v1/ para acessar os recursos.

:warning: Foram feitos testes unitários e testes manuais para cada função implementada.

<table border="1">
    <th>Endpoint</th><th>Método</th><th>Ação</th>
    <tr>
        <td>/user</td><td>GET</td><td>Mostra uma lista com todos os usuários.</td>
</td>
    </tr>
        <tr>
        <td>/user</td><td>POST</td><td>Registra um novo usuário.
</td>
    </tr>
        <tr>
        <td>/user/{id}</td><td>DELETE</td><td>Exclui um usuário.
</td>
</td>
    </tr>
        <tr>
        <td>/admin</td><td>POST</td><td>Cria uma nova role.
</td>
</td>
    </tr>
    </td>
    </tr>
        <tr>
        <td>/admin/{id}</td><td>PATCH</td><td>Atribui uma nova role ao usuário.
</td>
</td>
    </tr>
    </td>
    </tr>
        <tr>
        <td>/admin/{id}</td><td>DELETE</td><td> Exclui uma role.

</td>
    </tr>
</table>

 :warning: Todos os endpoints com final “admin” será necessário possuir a role ADMIN para ser acessada.
 
 
 #### Entrada.
 
 :warning: Todos os dados de entrada devem ser passados no formato JSON.
 
 POST /user
 
 ~~~JSON
    {
        "name":"teste",
        "username": "user-teste-1",
        "password": "exemplo@senha"
    }
 ~~~
 
 POST /admin
 
 ~~~JSON
 
 {
    "name":"SIMPLE_USER"
 }
 ~~~
 
Por enquanto é isso. Quando a aplicação for terminada provavelmente terá muitas outras funcionalidades.

 

