package app.models;

import app.entities.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Model {
    private static Model instance = new Model();

    private List<User> users;

    public static Model getInstance() {
        return instance;
    }

    private Model() {
        users = new ArrayList<>();

        users.add(new User("Aleksey", "Kichemasov", 19, "kich@mail.ru", "111"));
        users.add(new User("Denis", "Kolesnicov", 19, "koles@mail.ru", "222"));
        users.add(new User("Igor", "Asriev", 20, "asri@mail.ru", "333"));
    }

    public void add(User user) {
        users.add(user);
    }

    public List<String> getUserNamelist() {
        return users.stream()
                .map(User::getName)
                .collect(Collectors.toList());
    }

    public List<User> getUserList() {
        return users;
    }

    //European countries use ";" as
    //CSV separator because "," is their digit separator
    private static final String CSV_SEPARATOR = ",";

    public static void writeToCSV(User user) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\Alex\\IdeaProjects\\lb-2-3\\src\\main\\webapp\\data\\data.csv"), "UTF-8"));
            StringBuffer oneLine = new StringBuffer();
            oneLine.append(user.getName());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(user.getLastName());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(user.getAge());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(user.getEmail());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(user.getPhoneNum());
            bw.write(oneLine.toString());
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException exception) {

        }
    }
}