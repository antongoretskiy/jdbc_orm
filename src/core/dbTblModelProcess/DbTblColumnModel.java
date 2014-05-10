

package core.dbTblModelProcess;


/**
 * Класc, описывающий модель столбца в БД
 * @author Горецкий Антон
 */
public class DbTblColumnModel {

    private String name;
    private String type;
    
    public  boolean canBenNull;

    /**
     * Флаг автоматического инкрементирования
     */
    public boolean autoIncrement = false;
    
    /**
     * Конструктор класса
     * @param name название столбца
     * @param canBeNull Флаг возможности NULL
     * @param type тип данных БД
     */
    public DbTblColumnModel(String name, boolean canBeNull, String type)
    {
        this.name = name;
        this.canBenNull = canBeNull;
        this.type = type;
    }
    
    /**
     * Получение названия столбца
     * @return название столбца
     */
    public String getName()
    {
        return name;
    }
    /**
     * Установка названия столбца
     * @param columnName название столбца
     */
    public void setName(String columnName)
    {
        if(columnName == null || columnName.trim().isEmpty())
            return;
        name = columnName;
    }
    /**
     * Получение типа данных столбца
     * @return БД тип данных столбца
     */
    public String getType()
    {
        return type;
    }
    /**
     * Установка типа данных столбца
     * @param type БД тип даных столбца
     */
    public void setType(String type)
    {
        if(type == null || type.trim().isEmpty())
            return;
        this.type = type;
    }
}
