import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String name = "root";
        String password = "Root";
        String URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
        try (Connection connection = DriverManager.getConnection(URL, name, password)) {
            System.out.println("Введите логин");
            Scanner scanner = new Scanner(System.in);
            String log = scanner.nextLine();
            System.out.println("Введите пароль");
            String pass = scanner.nextLine();
            Statement statement = connection.createStatement();
            //statement.execute("INSERT INTO users VALUES ('" + log + "','" + pass + "' )");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE login = '" + log + "'");
            boolean inDataBase = resultSet.next();
            if (inDataBase == true) {
                resultSet = statement.executeQuery("SELECT password FROM users WHERE login = '" + log + "'");
                resultSet.next();
                String str = resultSet.getString("password");
                if (str.equals(pass)){
                    System.out.println("Вы зачекинились");
                statement.execute("UPDATE usercheck Set checkin = checkin + 1 where login = '" + log + "'");
            }
                else System.out.println("Пароль не верный");
            } else {
                statement.execute("INSERT INTO users VALUES ('" + log + "','" + pass + "')");
                statement.execute("INSERT INTO usercheck VALUES ('" + log + "'," +1+ ")");
                System.out.println("Вы зарегистрированы");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
