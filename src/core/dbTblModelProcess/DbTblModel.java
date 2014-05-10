

package core.dbTblModelProcess;

import java.util.ArrayList;

/**
 *
 * @author Горецкий Антон
 */
public class DbTblModel {

    private String tableName;
    
    /**
     * Первичные ключи таблицы
     */
    public final ArrayList<DbTblColumnModel> primaryKeys = new ArrayList<>();

    /**
     * Столбцы таблицы
     */
    public final ArrayList<DbTblColumnModel> columns = new ArrayList<>();
    
    /**
     * Конструктор класса
     * @param tableName название таблицы
     */
    public DbTblModel(String tableName)
    {
       this.tableName = tableName;
    }
    
    /**
     * Получение названия таблицы
     * @return название таблицы
     */
    public String getTableName()
    {
        return tableName;
    }
    /**
     * Установка названия таблицы
     * @param name название таблицы
     */
    public void setTableName(String name)
    {
        if(name == null || name.trim().isEmpty())
            return;
        tableName = name;
    }
    
}
