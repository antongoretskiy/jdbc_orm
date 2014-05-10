

package core;

import core.dbTblModelProcess.DbTypeConverter;
import java.math.BigDecimal;

/**
 * Реализация конвертера некоторых типов для MySQL
 * @author Горецкий Антон
 */
public class MySqlDbTypeConverter implements DbTypeConverter {
    final static String TEXT = "TEXT";
    final static String CHAR = "VARCHAR(1)";
    final static String REAL = "REAL";
    final static String DOUBLE = "DOUBLE";
    final static String DECIMAL = "DECIMAL";
    final static String DATE = "DATE";
    final static String INTEGER = "INT";
    
    @Override
    public String ConvertJavaToSQL(Class<?> javaClass) {
        if(javaClass.equals(String.class))
         {
             return TEXT;
         }
         else if(javaClass.equals(char.class))
         {
             return CHAR;
         }
         else if(javaClass.equals(BigDecimal.class))
         {
             return DECIMAL;
         }
         else if(javaClass.equals(Double.class) || javaClass.equals(double.class))
         {
             return DOUBLE;
         }
         else if(javaClass.equals(Integer.class) || javaClass.equals(int.class))
         {
             return INTEGER;
         }
         else
         {
             return null;
         }
    }
    
}
