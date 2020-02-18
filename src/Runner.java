import Utils.ScannerWrapper;

import java.sql.*;

public class Runner {
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/cinema";
    static final String USER = "postgres";
    static final String PASS = "2154";

    public static void main(String[] args) {
        ScannerWrapper scanner = new ScannerWrapper();
        System.out.println("Testing connection to PostgreSQL JDBC");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
        System.out.println("Enter parameter for search : 1- first name or last name ,2 - release date interval , 3 - all parameters ");
        int i = scanner.readInt();
        switch (i) {
            case 1:
                System.out.println("Enter first name or last name");
                String nameForFirstQuery = scanner.readLine();
                ResultSet resultSetName = queryForName(nameForFirstQuery);
                printResult(resultSetName);
                break;
            case 2:
                System.out.println("Enter first date in format yyyy-MM-dd");
                String firstDate = scanner.readLine();
                System.out.println("Enter second date in format yyyy-MM-dd ");
                String secondDate = scanner.readLine();
                ResultSet resultSetDate = queryForDate(firstDate, secondDate);
                printResult(resultSetDate);
                break;
            case 3:
                System.out.println("Enter first name or last name");
                String nameForQuery = scanner.readLine();
                System.out.println("Enter first date in format yyyy-MM-dd");
                String firstDateForQuery = scanner.readLine();
                System.out.println("Enter second date in format yyyy-MM-dd ");
                String secondDateForQuery = scanner.readLine();
                ResultSet resultSetAll = queryForAll(nameForQuery, firstDateForQuery, secondDateForQuery);
                printResult(resultSetAll);
        }
    }

    public static ResultSet queryForName(String name) {
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "select \"Director\".first_name,\"Director\".last_name,\"Director\".birth_date," +
                    "\"Film\".name,\"Film\".release_date,\"Film\".genre from \"Director\" inner join \"Film\" " +
                    "on \"Director\".id =\"Film\".director_id where (\"Director\".first_name=? or \"Director\".last_name=?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, name);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet queryForDate(String firstDate, String secondDate) {
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "select \"Director\".first_name,\"Director\".last_name,\"Director\".birth_date," +
                    "\"Film\".name,\"Film\".release_date,\"Film\".genre from \"Director\" inner join \"Film\" " +
                    "on \"Director\".id =\"Film\".director_id where (\"Film\".release_date between ? and ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(firstDate));
            preparedStatement.setDate(2, Date.valueOf(secondDate));
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet queryForAll(String name, String firstDate, String secondDate) {
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "select \"Director\".first_name,\"Director\".last_name,\"Director\".birth_date," +
                    "\"Film\".name,\"Film\".release_date,\"Film\".genre from \"Director\" inner join \"Film\" " +
                    "on \"Director\".id =\"Film\".director_id where (\"Director\".first_name=? or \"Director\".last_name=?)" +
                    "and \"Film\".release_date between ? and ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, name);
            preparedStatement.setDate(3, Date.valueOf(firstDate));
            preparedStatement.setDate(4, Date.valueOf(secondDate));
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static void printResult(ResultSet result) {
        try {
            while (result.next()) {
                String directorName = result.getString("first_name") +" "+ result.getString("last_name");
                Date directorBirthday = result.getDate("birth_date");
                String filmName= result.getString("name");
                Date releaseDate= result.getDate("release_date");
                String genre = result.getString("genre");
                System.out.println(directorName+"\n"+" "+ directorBirthday+"\n"+" "+filmName+"\n"+" "+releaseDate+"\n"+" "+genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
