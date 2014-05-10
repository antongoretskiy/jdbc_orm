
package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Горецкий Антон
 */
public class JdbcConnectionFactory {

    public String connectionURL;
    public String dbUser;
    public String dbPassword;
    private final String instDriverClassName;
    public static String driverClassName;
    private static JdbcConnectionFactory connectionFactory = null;
    
    private JdbcConnectionFactory() throws ClassNotFoundException
    {
        instDriverClassName = driverClassName;
        Class.forName(instDriverClassName);
    }
    
    public static synchronized JdbcConnectionFactory getInstance() throws ClassNotFoundException
    {
        if(connectionFactory == null)
            connectionFactory = new JdbcConnectionFactory();
        else if(!connectionFactory.instDriverClassName.equals(driverClassName))
        {
            connectionFactory = new JdbcConnectionFactory();
        }
        return connectionFactory;
    }
    
    public Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(connectionURL, dbUser,dbPassword);
    }
}
