

package core.sqlQueries;

import core.dbTblModelProcess.DbTblColumnModel;
import core.dbTblModelProcess.DbTblModel;

/**
 * Формирует CREATE TABLE SQL запрос по модели таблицы
 * @author Горецкий Антон
 */
public class createTableSqlQuery implements sqlQuery {
    private DbTblModel tblModel;
    
    public createTableSqlQuery(DbTblModel tblModel)
    {
        this.tblModel = tblModel;
    }

    @Override
    public String getSQL() {
        String query = "CREATE TABLE " + tblModel.getTableName() + "\n(";
        
        for(DbTblColumnModel column :tblModel.columns)
        {
            query += "\n";
            query += column.getName() + " " + column.getType();
            if(!column.canBenNull)
                query+= " NOT NULL";
            if(column.autoIncrement)
                query+= " AUTO_INCREMENT";
            query += ",";
        }
        query = query.substring(0,query.length() - 1);
        if(tblModel.primaryKeys.size() > 0)
        {
            
            query += ",\n";
            query += "PRIMARY KEY(";
            for(DbTblColumnModel column :tblModel.primaryKeys)
            {
                query += column.getName() + ", ";
            }
            query = query.substring(0,query.length() - 2);
            query += ")";
        }
        query += "\n)";
        return query;
    }
    
    public DbTblModel getModel()
    {
        return tblModel;
    }
    
    public void setModel(DbTblModel model)
    {
        tblModel = model;
    }
    
   
}
