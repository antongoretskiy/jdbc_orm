

package core.sqlQueries;

import java.util.Map;
import java.util.Map.Entry;

/**
 * Формирует SQL запрос вставки в таблицу значений
 * @author Горецкий Антон
 */
public class insertSqlQuery implements sqlQuery {
    private String tableName;
    public final Map<String,String> keyValues;
    
    public insertSqlQuery(String tableName, Map<String,String> keyValues){
        this.tableName = tableName;
        this.keyValues = keyValues;
    }
    
    @Override
    public String getSQL() {
       String query = "INSERT INTO " + tableName;
       String columns = "(";
       String values = "(";
       for(Entry<String, String> entry : keyValues.entrySet())
       {
           if(entry.getValue() != null)
           {
                columns += entry.getKey() +", ";
                values += "\"" + entry.getValue() +"\", ";
           }
       }
       columns = columns.substring(0,columns.length() - 2);
       values = values.substring(0,values.length() - 2);
       columns += ")";
       values += ")";
       query += columns;
       query += " VALUES " + values;
       return query;
    }
    
    public String getTableName()
    {
        return tableName;
    }
    
    public void setTableName(String name)
    {
        if(name == null || name.trim().isEmpty())
            return;
        tableName = name;
    }
    
}
