package forceman.dao.jdbc.impl;

import forceman.dao.DAOException;
import forceman.dao.DAOExceptionSource;
import forceman.dao.jdbc.AbstractJDBCGroupDAO;
import forceman.entity.Group;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 09.01.2017.
 * Реализация класса операций над объектами класса {@link Group} для использования с СУБД MySQL
 * в связи с использованием комбинации LIMIT ... OFFSET для выборки групп пользователей
 * и реализации хранимых процедур эмуляции функции nextval()
 */
public class MysqlJdbcGroupDAO extends AbstractJDBCGroupDAO {
    /**
     * Скрипт получения списка групп пользователей
     */
    public static final String SQL_LIST_GROUP = "SELECT id, name FROM " + GROUP_TABLE + " LIMIT ? OFFSET ?";

    /**
     * Скрипт инициализации таблицы последовательностей для таблицы групп пользователей
     */
    public static final String SQL_SEQUENCE_INIT_GROUPS = "INSERT INTO SEQUENCE_DATA (SEQUENCE_NAME) VALUES ('groups')";
    /**
     * Скрипт получения следующего значения последовательности для таблицы групп пользователей
     */
    public static final String SQL_ID_NEXTVAL = "SELECT nextval('groups')";

    /**
     * Скрипт получения текущего значения последовательности для таблицы групп пользователей
     */
    public static final String SQL_ID_CURRVAL = "SELECT currval('groups')";

    /**
     * Создание нового экземпляра класса {@link Group}
     *
     * @param group Объект класса @{link Group}
     * @return сохраненный в БД объект класса {@link Group} с присвоенным ему уникальным идентификатором
     * @throws DAOException - в случае возникновения ошибок при выполнении SQL запросов или при передаче в качестве значения - null
     *        в поле {@link Group#name}
     */
    @Override
    public Group create(Group group) throws DAOException {
        if( group == null || group.getName() == null )
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_GROUP_CREATE.toString(), new NullPointerException());
        PreparedStatement prepStmt = null;

        try {
            prepStmt = conn.prepareStatement(SQL_ID_CURRVAL);
            ResultSet rs = prepStmt.executeQuery();
            Integer id = null;
            if(rs.next())
                id = rs.getObject(1) != null ? rs.getInt(1) : null;
            if( id == null ) // Отсутствует запись в таблице последовательности для таблицы групп пользователей
            {
                // Добавим данную запись
                prepStmt = conn.prepareStatement( SQL_SEQUENCE_INIT_GROUPS );
                prepStmt.execute();
                prepStmt.close();
            }
            // Получаем новый идентификатор группы пользователец
            prepStmt = conn.prepareStatement(SQL_ID_NEXTVAL);
            rs = prepStmt.executeQuery();
            if( rs.next() )
                id = rs.getObject(1) != null ? rs.getInt(1) : null;
            if( id == null ) // Отсутствует запись в таблице последовательности для таблицы групп пользователей
            {
                throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_GROUP_CREATE.toString());
            }
            group.setId( id );
            prepStmt.close();

            prepStmt = conn.prepareStatement(SQL_CREATE_GROUP);
            prepStmt.setInt(1, id);
            prepStmt.setString(2, group.getName());
            prepStmt.executeUpdate();
            return group;
        }catch(SQLException sqlExc){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_GROUP_CREATE.toString(), sqlExc);
        }finally {
            if( prepStmt != null )
                try {
                    prepStmt.close();
                }catch (SQLException closeExc){
                    throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_CLOSE_STATEMENT.toString(), closeExc);
                }
        }
    }

    /**
     * Получения списка объектов класса {@link Group}, соотвествующим имеющимся в БД записям.
     *
     * @param limit  Максимальное количество выводимых записей. Если = -1, то без ограничения
     * @param offset Начальный индекс записи начала вывода
     * @return Список групп полльзователей
     * @throws DAOException - в случае возникновения ошибок при выполнении SQL запросов или при передаче в качестве значения - null
     *        в параметры limit и offset
     */
    @Override
    public List<Group> getList(Integer limit, Integer offset) throws DAOException {
        if(limit == null || offset == null)
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_GROUP_LIST.toString(), new NullPointerException());
        PreparedStatement prepStmt = null;
        List<Group> groups = new ArrayList<>();
        try {
            prepStmt = conn.prepareStatement(SQL_LIST_GROUP);
            prepStmt.setInt(1, offset);
            prepStmt.setInt(2, limit);
            ResultSet rs = prepStmt.executeQuery();
            while(rs.next()){
                Group group = new Group( rs.getString("name") );
                group.setId( rs.getInt("id") );
                groups.add( group );
            }

        }catch(SQLException sqlExc){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_GROUP_LIST.toString(), sqlExc);
        }finally{
            try {
                prepStmt.close();
            }catch(SQLException closeExc){
                throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_CLOSE_STATEMENT.toString(), closeExc);
            }
        }
        return groups;
    }

    public MysqlJdbcGroupDAO(Connection conn){
        super(conn);
    }
}
