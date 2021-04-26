package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

/**
 * The App class is the main entry point into our code.
 * This is where any queries or data manipulation occurs.
 *
 *
 * @author  Kristiyan Tanev, Lee Shaw, Amy Harvey, Lewis Watson
 * @version 1.0
 * @since   2021-02-03
 */
public class App
{
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        if (args.length < 1)
        {
            a.connect("localhost:33060");
        }
        else
        {
            a.connect(args[0]);
        }

        // Extract country information
        // ArrayList<Country> country = a.getCountry();

        //print country information
        //a.printCountries(country);

        // City information
        ArrayList<City> city = a.getCity();

        // print city information
        a.printCities(city);

        // City information
        //ArrayList<CapitalCity> city = a.getCapitalCity();

        // print city information
        //a.printCapitalCities(city);

        // Language information
        //ArrayList<Language> language = a.getLanguage();

        //print language information
        //a.printLanguage(language);

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * This is the method used to attempt to connect to the program Database.
     * Upon failing to connect the program will try again (ten times).
     * @param location Database IP/Address
     */
    public void connect(String location)
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
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
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
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

    /**
     * This is the method used to return an array list of Countries.
     * Dependant on the SQL used in the method.
     *
     * @return ArrayList<Country> List of Countries
     */
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
                                + "FROM country ORDER BY country.population DESC";

                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);
                // Extract employee information
                ArrayList<Country> country = new ArrayList<Country>();
                while (rset.next())
                {
                    Country cnt = new Country();
                    cnt.setCode(rset.getString("country.code"));
                    cnt.setName(rset.getString("country.name"));
                    cnt.setContinent(rset.getString("country.continent"));
                    cnt.setRegion(rset.getString("country.region"));
                    cnt.setPopulation(rset.getInt("country.population"));
                    cnt.setCapital(rset.getString("country.capital"));
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
     * This is the method used to return an array list of Cities.
     * Dependant on the SQL used in the method.
     *
     * @return ArrayList<Country> List of Cities
     */
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
                                + "FROM city, country \n" +
                                "WHERE country.Code = city.CountryCode\n" +
                                "AND country.Name = 'Australia'\n" +
                                "ORDER BY city.Population DESC LIMIT 0,5;";


                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);
                // Extract employee information
                ArrayList<City> city = new ArrayList<City>();
                while (rset.next())
                {
                    City cnt = new City();
                    cnt.setName(rset.getString("city.Name"));
                    cnt.setCountryCode(rset.getString("city.CountryCode"));
                    cnt.setDistrict(rset.getString("city.District"));
                    cnt.setPopulation(rset.getInt("city.Population"));
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
     * This is the method used to return an array list of Capital Cities.
     * Dependant on the SQL used in the method.
     *
     * @return ArrayList<Country> List of Capital Cities.
     */
    public ArrayList<CapitalCity> getCapitalCity()
    {
        try
        {
            {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT Name, CountryCode, District, Population\n" +
                                "FROM city, country \n" +
                                "WHERE country.Code = city.CountryCode\n" +
                                "AND country.Name = 'Australia'\n" +
                                "ORDER BY city.Population DESC LIMIT 0,5;";

                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);
                // Extract city information
                ArrayList<CapitalCity> city = new ArrayList<CapitalCity>();
                while (rset.next())
                {
                    CapitalCity ccy = new CapitalCity();
                    ccy.setName(rset.getString("city.Name"));
                    ccy.setCountryCode(rset.getString("city.CountryCode"));
                    ccy.setDistrict(rset.getString("city.District"));
                    ccy.setPopulation(rset.getInt("city.Population"));
                    city.add(ccy);
                }
                return city;

            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city details");
            return null;
        }
    }

    /**
     * Prints a list of countries.
     * @param country Array List of Countries to be printed to system out.
     */
    public void printCountries(ArrayList<Country> country)
    {
        // Check countries is not null
        if (country == null)
        {
            System.out.println("No countries");
            return;
        }

        // Print header
        System.out.println(String.format("%-40s %-40s %-40s %-40s %-40s %-40s", "Code", "Name", "Continent", "Region", "Population", "Capital"));
        // Loop over all countries in the list
        for (Country cnt : country)
        {
            if (cnt == null)
                continue;
            String cnt_string =
                    String.format("%-40s %-40s %-40s %-40s %-40s %-40s",
                            cnt.getCode(), cnt.getName(), cnt.getContinent(), cnt.getRegion(), cnt.getPopulation(), cnt.getCapital());
            System.out.println(cnt_string);
        }
    }


    /**
     * Prints a list of cities.
     * @param city Array List of Cities to be printed to system out.
     */
    public void printCities(ArrayList<City> city)
    {
        // Check city is not null
        if (city == null)
        {
            System.out.println("No cities");
            return;
        }

        // Print header
        System.out.println(String.format("%-40s %-40s %-40s %-40s ", "Name", "CountryCode", "District", "Population"));
        // Loop over all cities in the list
        for (City cnt : city)
        {
            if (cnt == null)
                continue;

            String cnt_string =
                    String.format("%-40s %-40s %-40s %-40s",
                            cnt.getName(), cnt.getCountryCode(), cnt.getDistrict(), cnt.getPopulation());
            System.out.println(cnt_string);
        }
    }

    /**
     * Prints a list of Capital Cities.
     * @param city Array List of Capital Cities to be printed to system out.
     */
    public void printCapitalCities(ArrayList<CapitalCity> city)
    {
        // Check capital city is not null
        if (city == null)
        {
            System.out.println("No capital cities");
            return;
        }
        // Print header
        System.out.println(String.format("%-40s %-40s %-40s %-40s ", "Name", "CountryCode", "District", "Population"));
        // Loop over all capital cities in the list
        for (CapitalCity ccy : city)
        {
            if (ccy == null)
                continue;
            String ccy_string =
                    String.format("%-40s %-40s %-40s %-40s",
                            ccy.getName(), ccy.getCountryCode(), ccy.getDistrict(), ccy.getPopulation());
            System.out.println(ccy_string);
        }
    }

    public ArrayList<Language> getLanguage()
    {
        try
        {
            {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT countrylanguage.Language, SUM(country.Population * countrylanguage.Percentage / 100) AS speakers, " +
                                "SUM(country.Population * countrylanguage.Percentage / 100) / " +
                                "(SELECT sum(Population)FROM country) * 100 AS percentage_speakers " +
                                " FROM country " +
                                " JOIN countrylanguage ON countrylanguage.CountryCode = country.Code " +
                                " WHERE countrylanguage.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic')" +
                                " GROUP BY countrylanguage.Language" +
                                " ORDER BY speakers DESC;";

                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);
                // Extract employee information
                ArrayList<Language> language = new ArrayList<Language>();
                while (rset.next())
                {
                    Language cnt = new Language();
                    cnt.setLanguage(rset.getString("countryLanguage.Language"));
                    cnt.setPopulation(rset.getLong("speakers"));
                    cnt.setPercentage(rset.getFloat("percentage_speakers"));
                    language.add(cnt);
                }
                return language;

            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get language details");
            return null;
        }
    }

    public void printLanguage(ArrayList<Language> language)
    {
        // Check language is not null
        if (language == null)
        {
            System.out.println("No languages");
            return;
        }

        // Print header
        System.out.println(String.format("%-40s %-40s %-40s", "language", "Population", "Percentage"));
        // Loop over all languages in the list
        for (Language cnt : language)
        {
            if (cnt == null)
                continue;

            String cnt_string =
                    String.format("%-40s %-40s %-40s",
                            cnt.getLanguage(), cnt.getPopulation(), cnt.getPercentage());
            System.out.println(cnt_string);
        }
    }

}