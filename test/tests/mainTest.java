

package tests;

import core.JdbcConnectionFactory;
import core.MySqlDbTypeConverter;
import core.ReflectionJdbcDaoImpl;
import core.dbTblModelProcess.DbTypeConverter;
import core.dbTblModelProcess.IncorrectModelException;
import java.sql.SQLException;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.testObjects.Person;

/**
 *
 * @author Горецкий Антон
 */
public class mainTest {
    JdbcConnectionFactory factory;
    DbTypeConverter converter;
    
    public mainTest(){}
    
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        JdbcConnectionFactory.driverClassName = "com.mysql.jdbc.Driver";
        factory = JdbcConnectionFactory.getInstance();
        factory.dbUser = "root";
        factory.dbPassword = "";
        factory.connectionURL = "jdbc:mysql://localhost:3306/daotest";
        converter = new MySqlDbTypeConverter();
    }
    @Test
    public void TestALL() throws SQLException
    {
        ReflectionJdbcDaoImpl<Person> daoInstance = null;
        
        try {
            daoInstance  = new ReflectionJdbcDaoImpl<>(Person.class,factory.getConnection(),converter);
        } catch (IncorrectModelException ex) {
            System.out.println("Incorreсt model format: " + ex.getMessage());
        }
        Assert.assertNotNull(daoInstance);
        
        Person person = new Person();
        person.FirstName = "Anton";
        person.last_name = "Goretskiy";
        person.Age = 19;
        person.test_id = 7;
        
        System.out.println("Insert test: table will be created if it isn't exist");
        Assert.assertNull(person.person_id);
        System.out.println("before insert person_id is Null");
        daoInstance.insert(person);
        Assert.assertNotNull(person.person_id);
        System.out.println("after insert person_id is " + person.person_id);
        System.out.println("end of insert test");
        
        System.out.println("SelectByKey test:");
        person.FirstName = null;
        person.Age = null;
        person.last_name = null;
        System.out.println("We set null to fields, that isn't primary key");
        person = daoInstance.selectByKey(person);
        Assert.assertNotNull(person.FirstName);
        Assert.assertNotNull(person.Age);
        Assert.assertNotNull(person.last_name);
        System.out.println("After we check these fields");
        System.out.println("end of selectByKey test");
        
        System.out.println("Update test:");
        System.out.println("before Update Age is " + person.Age);
        System.out.println("Next:Increment Age, Update person, set NULL to Age and invoke SelectByKey");
        Integer incrementAge = ++person.Age;
        daoInstance.update(person);
        person.Age = null;
        daoInstance.selectByKey(person);
        Assert.assertNotNull(person.Age);
        Assert.assertEquals(incrementAge, person.Age);
        System.out.println("After this Age is " + person.Age);
        System.out.println("end of Update test");
        
        System.out.println("DeleteByKey test:");
        daoInstance.deleteByKey(person);
        person = daoInstance.selectByKey(person);
        Assert.assertNull(person);
        System.out.println("end of DeleteByKey test");
        
        System.out.println("SelectAll test:");
        for(int i =1;i<10;i++)
        {
            person = new Person();
            person.FirstName = "FirstName" + i;
            person.last_name = "LastName" + i;
            person.Age = 1;
            person.test_id = i;
            daoInstance.insert(person);
        }
        List<Person> personList = daoInstance.selectAll();
        Assert.assertNotNull(personList);
        
        for (Person pers : personList) {
            System.out.println(pers.FirstName);
        }
        System.out.println("end of SelectAll test");
        
        
    }
    
    
}
