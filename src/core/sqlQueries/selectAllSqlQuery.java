
package core.sqlQueries;

/**
 * Формирует SELECT ALL SQL запрос
 * @author Горецкий Антон
 */
public class selectAllSqlQuery implements sqlQuery {
    private String tableName;

    /**
     * Конструктор класса
     * @param tableName имя таблицы
     */
    public selectAllSqlQuery(String tableName)
    {
        this.tableName = tableName;
    }

    @Override
    public String getSQL() {
        String query = "SELECT * FROM " + tableName;
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
