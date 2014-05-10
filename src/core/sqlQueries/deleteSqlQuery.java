

package core.sqlQueries;

import java.util.Map;
import java.util.Map.Entry;

/**
 * Формирует DELETE SQL запрос по ключевым полям
 * @author Горецкий Антон
 */
public class deleteSqlQuery implements sqlQuery  {

    private String tableName;
    public final Map<String,String> primaryKeys;
    
    public deleteSqlQuery(String tableName, Map<String,String> primaryKeys)
    {
        this.tableName = tableName;
        this.primaryKeys = primaryKeys;
    }
    
    @Override
    public String getSQL() {
        String query = "DELETE FROM " + tableName + "\n";
        query += "WHERE ";
        for(Entry<String, String> entry : primaryKeys.entrySet())
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
