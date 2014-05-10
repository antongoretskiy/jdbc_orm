
package core.sqlQueries;

import java.util.Map;

/**
 * Формирует UPDATE SQL запрос
 * @author Горецкий Антон
 */
public class updateSqlQuery implements sqlQuery  {

    private String tableName;
    public final Map<String,String> primaryKeys;
    public final Map<String,String> keyValues;

    /**
     * 
     * @param tableName имя таблицы
     * @param primaryKeys таблица названий и значений первичных ключей
     * @param keyValues таблица названий и значений полей
     */
    public updateSqlQuery(String tableName, Map<String,String> primaryKeys, Map<String,String> keyValues)
    {
        this.tableName = tableName;
        this.primaryKeys = primaryKeys;
        this.keyValues = keyValues;
    }
    
    @Override
    public String getSQL() {
        String query = "UPDATE " + tableName + "\n";
        query += "SET ";
        for(Map.Entry<String, String> entry : keyValues.entrySet())
        {
           query += entry.getKey() + "=" ;
           if(entry.getValue() == null)
               query += entry.getValue();
           else
           {
             query +=  "\"" + entry.getValue() + "\" ";
           }
           query += ", ";
        }
        query = query.substring(0,query.length() - 2);
        query += "\n";
        query += "WHERE ";
        for(Map.Entry<String, String> entry : primaryKeys.entrySet())
        {
           query += entry.getKey() + "=\"" + entry.getValue() +"\" ";
           query += "AND ";
        }
        query = query.substring(0,query.length() - 4);
        query += ";";
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
