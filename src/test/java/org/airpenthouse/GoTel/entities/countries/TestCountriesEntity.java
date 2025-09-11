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
import java.util.List;
import java.util.Set;

import static org.airpenthouse.GoTel.util.PropertiesUtilManager.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TestCountriesEntity {

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
    public static void clean(){

    }

    @Test
    public void TestPrivateMethod_GetCountryByName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        setProperties("countryName", "South Africa");
        var list = reflectPrivateMethod("getCountryByName");
        assertTrue(list.get(0).getCountryName().equals(getPropertiesValue("countryName")));
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

    @Test
    public void TestPrivateMethod_GetCountryByNameWhenServerIsDown() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        setProperties("countryName", "");
        assertThrows(NullPointerException.class,()-> reflectPrivateMethod("getCountryByName"));
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

    private List<CountriesEntity> reflectPrivateMethod(String methodName) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method reflectedMethodName = CountriesEntity.class.getDeclaredMethod(methodName);
        reflectedMethodName.setAccessible(true);

        var set = (Set<CountriesEntity>) reflectedMethodName.invoke(countriesEntity);

        return set.stream().toList();
    }

}
