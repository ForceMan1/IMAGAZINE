package forceman.dao.jdbc;

import forceman.dao.DAOException;
import forceman.dao.DAOExceptionSource;
import forceman.dao.IPermissionDAO;
import forceman.entity.Permission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Igor on 21.12.2016.
 */
abstract public class AbstractJDBCPermissionDAO implements IPermissionDAO<Integer>{
    /**
     * Наименование таблицы прав доступа в БД
     */
    public static final String PERMISSION_TABLE = "permissions";
    /**
     * Скрипт создания нового права доступа
     */
    public static final String SQL_CREATE_PERMISSION = "INSERT INTO " + PERMISSION_TABLE + " (id, name, code) " +
            "VALUES (?, ?, ?)";

    /**
     * Скрипт удаления права доступа
     */
     public static final String SQL_DELETE_PERMISSION = "DELETE FROM " + PERMISSION_TABLE + " WHERE id=?";

     /**
     * Скрипт поиска права доступа его идентификатору
     */
    public static final String SQL_FIND_PERMISSION_BY_ID = "SELECT id, name, code FROM " + PERMISSION_TABLE +
            " where id = ?";

    /**
     * Скрипт обновления данных права доступа
     */
    public static final String SQL_UPDATE_PERMISSION = "UPDATE " + PERMISSION_TABLE + " SET name = ?, " +
            "code = ? WHERE id = ?";

    /**
     * Скрипт получения количества прав доступа
     */
    public static final String SQL_COUNT_PERMISSION = "SELECT COUNT(id) FROM " + PERMISSION_TABLE;

    /**
     * Скрипт поиска права доступа по его коду
     */
    public static final String SQL_FIND_PERMISSION_BY_CODE = "SELECT id, name, code FROM " + PERMISSION_TABLE +
            " WHERE code = ?";

    /**
     * Скрипт поиска права доступа по его наименованию
     */
    public static final String SQL_FIND_PERMISSION_BY_NAME = "SELECT id, name, code FROM " +
            PERMISSION_TABLE + " WHERE name = ?";


    protected Connection conn;

    /**
     * Функция получения коннектора БД
     * @return Коннектор БД
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * Функция установки коннектора БД
     * @param conn Коннектор БД
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public AbstractJDBCPermissionDAO(Connection conn){
        this.conn = conn;
    }

    /**
     * Сохранение права доступа в БД
     *
     * @param entity Объект права доступа {@link Permission}
     * @return созданный экземпляр права доступа с присвоенным ему уникальным идентификатором
     */
    @Override
    abstract public Permission create(Permission entity) throws DAOException;

    /**
     * Удаление права доступа из БД по его идентификатору {@link Permission#id}
     *
     * @param permission Объект класса права доступа {@link Permission}, однозначно характеризующего
     *                  удалямое право доступа по его идентификатору {@link Permission#id}
     * @return 1 - в случае успешного удаления права доступа
     */
    @Override
    public int delete(Permission permission) throws DAOException {
        if( permission == null || permission.getId() == null )
            return 0;
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(SQL_DELETE_PERMISSION);
            prepStmt.setInt( 1, permission.getId() );
            return prepStmt.executeUpdate();
        }catch(SQLException sqlExc){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_PERMISSION_DELETE.toString(), sqlExc);
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
     * Поиск права доступа по его идентификатору
     *
     * @param id Идентификатор права доступа
     * @return Найденный объект права доступа {@link Permission} или null
     */
    @Override
    public Permission findById(Integer id) throws DAOException {
        if( id == null )
            return null;
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(SQL_FIND_PERMISSION_BY_ID);
            prepStmt.setInt( 1, id);
            ResultSet rs = prepStmt.executeQuery();
            if( rs.next() ) {
                Permission permission = new Permission(rs.getString("name"), rs.getString("code"));
                permission.setId(id);
                return permission;
            }
            return null;
        }catch(SQLException sqlExc){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_PERMISSION_FIND_BY_ID.toString(), sqlExc);
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
     * Обновление права доступа
     *
     * @param permission Экземпляр класса {@link Permission}, однозначно характеризующий оюновляемое право
     *                   доступа по идентифкатору {@link Permission#id}
     * @return 1 - в случае успешного обновления права доступа
     */
    @Override
    public int update(Permission permission) throws DAOException {
        if(permission == null || permission.getId() == null)
            return 0;
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(SQL_UPDATE_PERMISSION);
            prepStmt.setString( 1, permission.getName() );
            prepStmt.setString( 2, permission.getCode() );
            prepStmt.setInt( 3, permission.getId() );
            return prepStmt.executeUpdate();
        }catch( SQLException sqlExc ) {
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_PERMISSION_UPDATE.toString(), sqlExc);
        }finally {
            if( prepStmt != null )
                try {
                    prepStmt.close();
                }catch(SQLException closeExc) {
                    throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_CLOSE_STATEMENT.toString(), closeExc);
                }
        }
    }

    /**
     * Получение количества прав доступа
     * @return Количество прав доступа
     */
    @Override
    public int getCount() throws DAOException {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(SQL_COUNT_PERMISSION);
            ResultSet rs = prepStmt.executeQuery();
            if( rs.next() ) {
                return rs.getObject(1) != null ? rs.getInt(1) : 0;
            }
            return 0;
        }catch(SQLException sqlException){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_PERMISSION_COUNT.toString(), sqlException);
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
     * Получения списка экземпляров класса T
     *
     * @param limit  Максимальное количество выводимых записей. Если = -1, то без ограничения
     * @param offset Начальный индекс записи начала вывода
     */
    @Override
    abstract public List<Permission> getList(Integer limit, Integer offset) throws DAOException;
}
