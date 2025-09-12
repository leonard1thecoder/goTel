package org.airpenthouse.GoTel.entities.countries;

import org.airpenthouse.GoTel.entities.country.CountriesEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import static org.airpenthouse.GoTel.util.PropertiesUtilManager.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class TestCountriesEntity {


    @Mock
    private ResultSet set;
    @Mock
    private PreparedStatement ps;
    @InjectMocks
    private CountriesEntity countriesEntity;

    @BeforeAll
    public static void init() {

    }

    @AfterEach
    public void initAfterEach() {
        setProperties("countryName", "");
    }

    @AfterAll
    public static void clean() {

    }

    @Test
    public void TestPrivateMethod_GetCountryByName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {
        setProperties("countryName", "South Africa");
        var list = reflectPrivateMethod("getCountryByName");

        assertEquals(getPropertiesValue("countryName"), list.get(0).getCountryName());
    }

    /*
    Here testing when country is not  provide
     */
    @Test
    public void TestPrivateMethod_GetCountryByNameEmptyCountryName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        setProperties("countryName", "");
        var list = reflectPrivateMethod("getCountryByName");
        assertTrue(list.isEmpty());
    }

    /*
        Test when server is down throw NullPointer
     */

  //  @Test
    public void TestPrivateMethod_GetCountryByNameWhenServerIsDown() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        setProperties("countryName", "");
        assertThrows(NullPointerException.class, () -> reflectPrivateMethod("getCountryByName"));
    }

    /*
   Here testing when country is not  correct
    */
    @Test
    public void TestPrivateMethod_GetCountryByNameEmptyIsInvalid() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        setProperties("countryName", "South African Flag");
        var list = reflectPrivateMethod("getCountryByName");
        assertTrue(list.isEmpty());
    }

    /*
        Here testing we getting all countries
        Countries Size : 239
     */

    @Test
    public void TestPrivateMethod_GetAllCountries() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var list = reflectPrivateMethod("getAllCountries");
        assertEquals(239, list.size());
    }

   // @Test
    public void TestPrivateMethod_GetAllCountriesThrowsNullPointerException() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        assertThrows(NullPointerException.class, () -> reflectPrivateMethod("getAllCountries"));
    }

    @Test
    public void TestPrivateMethod_addDataToSet() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method reflectedMethodName = CountriesEntity.class.getDeclaredMethod("addDataFromDbToSet",ResultSet.class);
        reflectedMethodName.setAccessible(true);
        var sets = (Set<CountriesEntity>)reflectedMethodName.invoke(countriesEntity,set);

        assertTrue(sets.isEmpty());
    }



    private List<CountriesEntity> reflectPrivateMethod(String methodName) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method reflectedMethodName = CountriesEntity.class.getDeclaredMethod(methodName);
        reflectedMethodName.setAccessible(true);

        var set = (Set<CountriesEntity>) reflectedMethodName.invoke(countriesEntity);

        return set.stream().toList();
    }

    @Test
    public void TestPrivateMethod_GetCountryByRegionEmptyCountryName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        setProperties("regionName", "");
        var list = reflectPrivateMethod("getCountryByRegion");
        assertTrue(list.isEmpty());
    }

    @Test
    public void TestPrivateMethod_GetCountryByRegion() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {
        setProperties("regionName", "Caribbean");
        var list = reflectPrivateMethod("getCountryByRegion");

        assertEquals(getPropertiesValue("regionName"), list.get(0).getCountryRegion());
    }

    @Test
    public void TestPrivateMethod_GetCountryByContinentEmptyCountryName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        setProperties("continentName", "");
        var list = reflectPrivateMethod("getCountryByContinent");
        assertTrue(list.isEmpty());
    }

    @Test
    public void TestPrivateMethod_GetCountryByContinent() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {
        setProperties("continentName", "Africa");
        var list = reflectPrivateMethod("getCountryByContinent");

        assertEquals(getPropertiesValue("continentName"), list.get(0).getContinent());
    }

}
