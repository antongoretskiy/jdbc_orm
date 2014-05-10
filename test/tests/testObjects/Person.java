/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests.testObjects;
import core.annotations.Column;
import core.annotations.PrimaryKey;
import core.annotations.Table;
import java.math.BigDecimal;
/**
 * Тестируемый обьект
 * @author Горецкий Антон
 */
@Table(name="PersonTable")
public class Person {
    
    @PrimaryKey(AutoIncrement=true)
    public Integer person_id;
    
    @PrimaryKey(AutoIncrement=false)
    public Integer test_id;
    
    @Column(name="first_name")
    public String FirstName;
    
    public String last_name;
    
    @Column(name="age")
    public Integer Age;
    
    public BigDecimal big_dec;
    
    public String ThisFieldWillNotDefinedByProcessor;

    
    public Person() 
    {
        
    }
}
