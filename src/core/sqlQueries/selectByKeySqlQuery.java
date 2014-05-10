
package core.sqlQueries;

import java.util.Map;

/**
 * Формирует SELECT SQL запрос по ключам
 * @author Горецкий Антон
 */
public class selectByKeySqlQuery implements sqlQuery {
    
    private String tableName;
    public final Map<String,String> keys;

    /**
     *
     * @param tableName имя таблицы
     * @param keys таблица названий и значений ключевых полей
     */
    public selectByKeySqlQuery(String tableName,Map<String,String> keys )
    {
        this.tableName=tableName;
        this.keys = keys;
    }

    @Override
    public String getSQL() {
        String query = "SELECT * FROM " + tableName;
        query += "\n";
        query +="WHERE ";
        for(Map.Entry<String, String> entry : keys.entrySet())
        {
           query += entry.getKey() + "=\"" + entry.getValue() +"\" ";
           query += "AND ";
        }
        query = query.substring(0,query.length() - 4);
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
