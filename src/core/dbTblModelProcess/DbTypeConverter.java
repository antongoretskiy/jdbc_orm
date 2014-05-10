

package core.dbTblModelProcess;

/**
 *
 * @author Горецкий Антон
 */
public interface DbTypeConverter {

    /**
     * Конвертация типа обьекта Java в тип данных БД
     * @param javaClass тип обьекта, который будет конвертироваться
     * @return соответствующий тип данных БД, либо null, если тип обьекта не поддерживается
     */
    String ConvertJavaToSQL(Class<?> javaClass); 
}
