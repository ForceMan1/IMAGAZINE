package forceman.dao.jdbc.impl;

import forceman.dao.DAOException;
import forceman.dao.DAOExceptionSource;
import forceman.dao.jdbc.AbstractJDBCPermissionDAO;
import forceman.entity.Permission;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 10.01.2017.
 * Реализация класса операций над объектами класса {@link Permission} для использования с СУБД MySQL
 * в связи с использованием комбинации LIMIT ... OFFSET для выборки групп пользователей
 * и реализации хранимых процедур эмуляции функции nextval()
 */
public class MysqlJdbcPermissionDAO extends AbstractJDBCPermissionDAO {
    /**
     * Скрипт получения списка прав доступа
     */
    public static final String SQL_LIST_PERMISSION = "SELECT id, name, code FROM " + PERMISSION_TABLE + " LIMIT ? OFFSET ?";

    /**
     * Скрипт инициализации таблицы последовательностей для таблицы прав доступа
     */
    public static final String SQL_SEQUENCE_INIT_PERMISSIONS = "INSERT INTO SEQUENCE_DATA (SEQUENCE_NAME) VALUES ('permissions')";
    /**
     * Скрипт получения следующего значения последовательности для таблицы прав доступа
     */
    public static final String SQL_ID_NEXTVAL = "SELECT nextval('permissions')";

    /**
     * Скрипт получения текущего значения последовательности для таблицы прав доступа
     */
    public static final String SQL_ID_CURRVAL = "SELECT currval('permissions')";


    /**
     * Сохранение права доступа в БД
     *
     * @param permission Объект права доступа {@link Permission}
     * @return созданный экземпляр права доступа с присвоенным ему уникальным идентификатором
     */
    @Override
    public Permission create(Permission permission) throws DAOException {
        if(permission == null)
            return null;
        PreparedStatement prepStmt = null;
        try {
            // Проверка инициализации таблицы последовательности для прав доступа
            Integer id = null;
            prepStmt = conn.prepareStatement(SQL_ID_CURRVAL);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next())
                id = rs.getObject(1) != null ? rs.getInt(1) : null;
            if( id == null ) // Произвести инициализацию таблицы последовательности для прав доступа
            {
                // Добавим данную запись
                prepStmt = conn.prepareStatement( SQL_SEQUENCE_INIT_PERMISSIONS );
                prepStmt.execute();
                prepStmt.close();
            }
            // Получаем новый идентификатор группы прав доступа
            prepStmt = conn.prepareStatement(SQL_ID_NEXTVAL);
            rs = prepStmt.executeQuery();
            if( rs.next() )
                id = rs.getObject(1) != null ? rs.getInt(1) : null;
            if( id == null ) // Отсутствует запись в таблице последовательности для таблицы прав доступа
            {
                throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_PERMISSION_CREATE.toString());
            }
            permission.setId( id );
            prepStmt.close();

            prepStmt = conn.prepareStatement(SQL_CREATE_PERMISSION);
            prepStmt.setInt(1, id);
            prepStmt.setString(2, permission.getName());
            prepStmt.setString(3, permission.getCode());
            prepStmt.executeUpdate();
            return permission;
        }catch (SQLException sqlExc){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_PERMISSION_CREATE.toString(), sqlExc);
        }finally {
            if( prepStmt != null )
                try {
                    prepStmt.close();
                }catch(SQLException closeExc){
                    throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_CLOSE_STATEMENT.toString(), closeExc);
                }
        }
    }

    /**
     * Получения списка прав доступа из БД
     *
     * @param limit  Максимальное количество выводимых записей. Если = -1, то без ограничения
     * @param offset Начальный индекс записи начала вывода
     * @return Список прав доступа, null - в случае некорректных значений параметров limit и offset
     */
    @Override
    public List<Permission> getList(Integer limit, Integer offset) throws DAOException {
        if(offset == null || limit == null)
            return null;
        List<Permission> permissions = new ArrayList<>();
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(SQL_LIST_PERMISSION);
            prepStmt.setInt( 1, limit);
            prepStmt.setInt( 2, offset);
            ResultSet rs = prepStmt.executeQuery();
            while( rs.next() ) {
                Permission permission = new Permission(rs.getString("name"), rs.getString("code"));
                permission.setId( rs.getInt("id"));
                permissions.add( permission );
            }
            return permissions;
        }catch (SQLException sqlExc){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_PERMISSION_LIST.toString(), sqlExc);
        }finally {
            if( prepStmt != null )
                try {
                    prepStmt.close();
                }catch(SQLException closeExc){
                    throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_CLOSE_STATEMENT.toString(), closeExc);
                }
        }
    }


    public MysqlJdbcPermissionDAO(Connection conn){
        super(conn);
    }
}
