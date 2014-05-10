

package core.dbTblModelProcess;;

import core.annotations.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Горецкий Антон
 */
public class DbTblModelClassAnnotationProcessor<T> {
    private final Class<?> dbTblModelClass;
    private final DbTypeConverter dbTypeConverter;
    private DbTblModel model;
    private boolean isInspected;
    
    /**
     *
     * @param dbTblModelClass обьект, модель которого будет анализироваться
     * @param dbTypeConverter конвертер Java типов в типы данных БД
     * @throws core.dbTblModelProcess.IncorrectModelException
     */
    public DbTblModelClassAnnotationProcessor(Class<T> dbTblModelClass, DbTypeConverter dbTypeConverter) throws IncorrectModelException{
        this.dbTblModelClass = dbTblModelClass;
        this.dbTypeConverter = dbTypeConverter;
        checkDbTblModel();
        createStartModel();
    }
    
    
    private void checkDbTblModel() throws IncorrectModelException
    {
        try {
           dbTblModelClass.getConstructor();
        } catch (NoSuchMethodException | SecurityException ex) {
           throw new IncorrectModelException("Класс модели должен иметь открытый конструктор по умолчанию");
        }
        if(!dbTblModelClass.isAnnotationPresent(Table.class))
            throw new IncorrectModelException("Table аннотация отсутствует");
        
        Field[] DbTblModelClassFields = dbTblModelClass.getDeclaredFields();
        int pkAutoIncCount = 0;
        for(Field field:DbTblModelClassFields)
        {
            if(field.isAnnotationPresent(PrimaryKey.class))
            {
                if(!field.isAnnotationPresent(Column.class) &&
                        !isLoweCaseAndHaveUnderscore(field.getName()))
                {
                    throw new IncorrectModelException("Первичный ключ задан над неопознанными полем");
                }
                PrimaryKey pk = field.getAnnotation(PrimaryKey.class);
                if(pk.AutoIncrement())
                {
                   if( field.getType() != Integer.class)
                       throw new IncorrectModelException("Автоинкрементируемым первичным ключом может быть только поле типа Integer");
                   
                   pkAutoIncCount++;
                   if(pkAutoIncCount > 1)
                       throw new IncorrectModelException("В одной модели может быть только 1 первичный автоинкрементируемый ключ");
                }
            }
            if(field.isAnnotationPresent(Column.class) ||
                    isLoweCaseAndHaveUnderscore(field.getName()))
            {
                String type = dbTypeConverter.ConvertJavaToSQL(field.getType());
                if(type == null)
                    throw new IncorrectModelException(
                            "Данный тип не поддерживается установленным конвертером: "
                                    + field.getType().toString());
            }
        }
    }
    
    private void createStartModel()
    {
        Table tblAnnotation = dbTblModelClass.getAnnotation(Table.class);
        String tableName = tblAnnotation.name();
        model = new DbTblModel(tableName);
    }
    
    /**
     * Получает название таблицы модели класса
     * @return название таблицы
     */
    public String getTableName()
    {
        return model.getTableName();
    }
    
    /**
     * Получает модель таблицы по модели класса
     * @return модель таблица
     */
    public DbTblModel getTableModel()
    {
       if(!isInspected)
           inspectdbTblModelClass();
       return model;
    }
    
    /**
     * Получает таблицу названий первичных ключей и их значений по модели обьекта
     * @param object обьект модель которого будет использована
     * @return таблица названий первичных ключей и их значений
     * @throws IllegalAccessException
     */
    public Map<String,String> getPrimaryKeysFromObject(T object) throws IllegalAccessException
    {
        Field[] DbTblModelClassFields = dbTblModelClass.getDeclaredFields();
        Map<String,String> map = new HashMap<>();
        for(Field field:DbTblModelClassFields)
        {
            if(field.isAnnotationPresent(PrimaryKey.class))
            {
                String colName = field.getName();
                String value = null;
                if(field.isAnnotationPresent(Column.class))
                {
                    Column colAnn = field.getAnnotation(Column.class);
                    colName = colAnn.name();
                }
                else if(!isLoweCaseAndHaveUnderscore(colName))
                {
                    continue;
                }
                Object val = field.get(object);
                if(val != null)
                    value = val.toString();
                map.put(colName, value);
            }
        }
        return map;
    }
    
    /**
     * Получает названия полей модели обьекта и их значения
     * @param object обьект, модель которого будет использована
     * @return таблица названий полей и их значений
     * @throws IllegalAccessException
     */
    public Map<String,String> getColumnValuesFromObject(T object) throws IllegalAccessException
    {
        Field[] DbTblModelClassFields = dbTblModelClass.getDeclaredFields();
        Map<String,String> map = new HashMap<>();
        String ColumnName;
        String ColumnValue;
        for(Field field:DbTblModelClassFields)
        {
            ColumnName = field.getName();
            if(field.isAnnotationPresent(Column.class))
            {
                Column columnAnnotation = field.getAnnotation(Column.class);
                ColumnName = columnAnnotation.name();
            }
            else if(!isLoweCaseAndHaveUnderscore(ColumnName))
            {
                continue;
            }
            ColumnValue = null;
            Object val = field.get(object);
            if(val != null)
                ColumnValue = field.get(object).toString();
            map.put(ColumnName, ColumnValue);
        }
        return map;
    }
    
    /**
     * Заполняет поля модели обьекта заданными значениями
     * @param map таблица названий полей и соответствующих им значений
     * @param object обьект, поля которого будут заполнены
     * @throws IllegalAccessException
     */
    public void setValuesToObject(Map<String,Object> map, T object) throws IllegalAccessException
    {
        Field[] DbTblModelClassFields = dbTblModelClass.getDeclaredFields();
        String ColumnName;
        for(Field field:DbTblModelClassFields)
        {
            ColumnName = field.getName();
            if(field.isAnnotationPresent(Column.class))
            {
                Column columnAnnotation = field.getAnnotation(Column.class);
                ColumnName = columnAnnotation.name();
            }
            else if(!isLoweCaseAndHaveUnderscore(ColumnName))
            {
                continue;
            }
            Object value = map.get(ColumnName);
            if(value != null)
            {
               field.set(object,value);
            }
               
        }
    }
    
    public void setAutoIncrementedValueToObject(T object, Integer value) throws IllegalAccessException
    {
        Field[] DbTblModelClassFields = dbTblModelClass.getDeclaredFields();
        for(Field field:DbTblModelClassFields)
        {
            if(field.isAnnotationPresent(PrimaryKey.class))
            {
                PrimaryKey pk = field.getAnnotation(PrimaryKey.class);
                if(pk.AutoIncrement())
                {
                    field.set(object, value);
                    return;
                }
            }
            inspectFieldAndAddToModel(field,model);
        }
    }
    
    private DbTblModel inspectdbTblModelClass()
    {
        Field[] DbTblModelClassFields = dbTblModelClass.getDeclaredFields();
        for(Field field:DbTblModelClassFields)
        {
            inspectFieldAndAddToModel(field,model);
        }
        isInspected = true;
        return model;
    }
    
    private void inspectFieldAndAddToModel(Field field, DbTblModel model)
    {
        String columnName;
        boolean columnCanBeNull = true; 
        if(field.isAnnotationPresent(Column.class))
        {
            Column columnAnnotation = field.getAnnotation(Column.class);
            columnName = columnAnnotation.name();
            columnCanBeNull = columnAnnotation.CanBeNull();
        }
        else if(isLoweCaseAndHaveUnderscore(field.getName()))
        {
            columnName = field.getName();
        }
        else
        {
            return;
        }
        String columnType = dbTypeConverter.ConvertJavaToSQL(field.getType());
        if(columnType == null)
            return;
        DbTblColumnModel columnModel = 
                new DbTblColumnModel(columnName,columnCanBeNull,columnType);
        if(field.isAnnotationPresent(PrimaryKey.class))
        {
           columnModel.canBenNull = false;
           PrimaryKey pkAnn = field.getAnnotation(PrimaryKey.class);
           columnModel.autoIncrement = pkAnn.AutoIncrement();
           model.primaryKeys.add(columnModel);
        }
        model.columns.add(columnModel);
    }
    
    private boolean isLoweCaseAndHaveUnderscore(String str)
    {
        boolean haveUnderscore = false;
        for (char ch: str.toCharArray()) {
            
            if(ch == '_') {
                haveUnderscore = true;
            }
            else
            {
                if(!Character.isLowerCase(ch))
                    return false;
            }
        }
        return haveUnderscore;
    }
}
