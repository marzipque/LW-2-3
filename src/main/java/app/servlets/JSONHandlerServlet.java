package app.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.*;
import app.models.Model;
import app.entities.User;

@WebServlet("/JSONHandlerServlet")
public class JSONHandlerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Получение списка пользователей
        var userList = Model.getInstance().getUserList();

        // Серелизация в JSON
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(userList);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(data);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Получение JSON
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String data = reader.lines().collect(Collectors.joining()); // Сам JSON
        reader.close();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(data);

        // Получаем значения полей из объекта JsonNode и создаём POJO объект
        User user = new User();
        user.setName(jsonNode.get("name").asText());
        user.setLastName(jsonNode.get("lastName").asText());
        user.setAge(jsonNode.get("age").asInt());
        user.setEmail(jsonNode.get("email").asText());
        user.setPhoneNum(jsonNode.get("phone").asText());

        Model model = Model.getInstance();
        model.add(user);

        Model.writeToCSV((user));

        response.getWriter().write("Новый пользователь добавлен!");
    }
}