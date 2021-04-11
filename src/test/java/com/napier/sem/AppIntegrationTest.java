package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060");
    }

    @Test
    void testGetCountry()
    {
        ArrayList<Country> cnt = app.getCountry();
        app.printCountries(cnt);
        System.out.println("--------------------------");
    }

    @Test
    void testGetCity()
    {
        ArrayList<City> cnt = app.getCity();
        app.printCities(cnt);
        System.out.println("--------------------------");
    }

    @Test
    void testGetCapitalCity()
    {
        ArrayList<CapitalCity> ccy = app.getCapitalCity();
        app.printCapitalCities(ccy);
        System.out.println("--------------------------");
    }

    @Test
    void testGetLanguage()
    {
        ArrayList<Language> ccy = app.getLanguage();
        app.printLanguage(ccy);
        System.out.println("--------------------------");
    }

}