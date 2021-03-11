package by.soc.mysoc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";
    private static final String SELECT_CITIES_AND_COUNTRIES ="SELECT CITIES.ID, CITIES.NAME, COUNTRIES.NAME, COUNTRIES.KOD FROM CITIES INNER JOIN COUNTRIES ON CITIES.COUNTRYID=COUNTRIES.ID";
    private static final String SELECT_ABONENTS_AND_CITIES = "SELECT ABONENTS.ID,ABONENTS.FIRST_NAME, ABONENTS.LAST_NAME, CITIES.NAME FROM ABONENTS INNER JOIN CITIES ON ABONENTS.CITY_ID=CITIES.ID";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory =new ConnectionFactory();
//        connectionFactory.connectCitiesWithCountries();
//        connectionFactory.connectAbonentsWithCities();
    }

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
