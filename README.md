jdbc_orm
========


####Данный проект демонстрирует пример реализации DAO, который с помощью Reflection делает отображение класса определенного формата в таблицу в реляционной базе данных.

!!!Тесты выполнялись на MySQL, для корректной работы тестов необходимо добавить в проект библиотеку драйвера JDBC MySQL.

###Функционал:

* создание таблицы по модели класса
*	вставка объекта в таблицу (если таблицы не существует, то она будет создана), если объект содержит поле автоинкрементируемого первичного ключа, то после вставки этому полю будет присвоено сгенерированное СУБД значение.
*	обновление полей объекта в таблице по первичному (возможно составному) ключу объекта
*	получение объекта из таблицы по первичному ключу
*	удаление обьекта из таблицы по первичному ключу
*	получение всех объектов из таблицы

###Класс должен удовлетворять следующим критериям:
* Класс должен быть помечен аннотацией @Table и иметь открытый конструктор по умолчанию
*	Поля класса, которые  отражаются в таблицу должны быть помечены аннотацией @Column, либо содержать в своем названии только маленькие буквы и символ “_”.
*	Первичным автоинкрементируемым ключом может быть только поле типа Integer. Первичные ключи помечаются аннотацией @PrimaryKey(boolean autoIncrement)
*	Поддерживаемые типы данных:
Т.К. в разных СУБД типы данных могут называться по-разному поддержка типов данных зависит от реализации интерфейса DbTypeConverter. Рекомендуется использоваться null-значимые типы.  В проекте есть пример реализации конвертера некоторых типов для MySQL.
