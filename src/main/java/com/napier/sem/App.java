package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App
{
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        //Extract country information
         //ArrayList<Country> country = a.getCountry();

        //print country information
        //a.printCountries(country);

        // City information
        ArrayList<City> city = a.getCity();

        // print city information
        a.printCities(city);

        // Disconnect from database
        a.disconnect();
    }
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public ArrayList<Country> getCountry()
    {
        try
        {
            {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, country.Capital "
                                + "FROM country WHERE country.Region = 'Eastern Asia' ORDER BY country.population DESC";

                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);
                // Extract employee information
                ArrayList<Country> country = new ArrayList<Country>();
                while (rset.next())
                {
                    Country cnt = new Country();
                    cnt.code = rset.getString("country.code");
                    cnt.name = rset.getString("country.name");
                    cnt.continent = rset.getString("country.continent");
                    cnt.region = rset.getString("country.region");
                    cnt.population = rset.getInt("country.population");
                    cnt.capital = rset.getString("country.capital");
                    country.add(cnt);
                }
                return country;

            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     * Prints a list of countries.
     * @param country The list of countries to print.
     */
    public void printCountries(ArrayList<Country> country)
    {
        // Print header
        System.out.println(String.format("%-40s %-40s %-40s %-40s %-40s %-40s", "Code", "Name", "Continent", "Region", "Population", "Capital"));
        // Loop over all countries in the list
        for (Country cnt : country)
        {
            String cnt_string =
                    String.format("%-40s %-40s %-40s %-40s %-40s %-40s",
                            cnt.code, cnt.name, cnt.continent, cnt.region, cnt.population, cnt.capital);
            System.out.println(cnt_string);
        }
    }

    public ArrayList<City> getCity()
    {
        try
        {
            {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT city.Name, city.CountryCode, city.District, city.Population "
                                + "FROM city ORDER BY city.Population DESC;";

                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);
                // Extract employee information
                ArrayList<City> city = new ArrayList<City>();
                while (rset.next())
                {
                    City cnt = new City();
                    cnt.Name = rset.getString("city.Name");
                    cnt.CountryCode = rset.getString("city.CountryCode");
                    cnt.District = rset.getString("city.District");
                    cnt.Population = rset.getInt("city.Population");
                    city.add(cnt);
                }
                return city;

            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }
    /**
     * Prints a list of cities.
     */
    public void printCities(ArrayList<City> city)
    {
        // Print header
        System.out.println(String.format("%-40s %-40s %-40s %-40s ", "Name", "CountryCode", "District", "Population"));
        // Loop over all cities in the list
        for (City cnt : city)
        {
            String cnt_string =
                    String.format("%-40s %-40s %-40s %-40s",
                            cnt.Name, cnt.CountryCode, cnt.District, cnt.Population);
            System.out.println(cnt_string);
        }
    }
}