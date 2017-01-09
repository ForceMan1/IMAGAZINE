package forceman.dao.jdbc;

import forceman.dao.DAOException;
import forceman.dao.DAOExceptionSource;
import forceman.dao.IGroupDAO;
import forceman.entity.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

/**
 * Created by Igor on 21.12.2016.
 */
abstract public class AbstractJDBCGroupDAO implements IGroupDAO<Integer> {
    /**
     * Наименование таблицы групп пользователей в БД
     */
    public static final String GROUP_TABLE = "groups";
    /**
     * Скрипт создания новой группы пользователей
     */
    public static final String SQL_CREATE_GROUP = "INSERT INTO " + GROUP_TABLE + " (id, name) " +
            "VALUES (?, ?)";

    /**
     * Скрипт удаления группы пользователей
     */
    public static final String SQL_DELETE_GROUP = "DELETE FROM " + GROUP_TABLE + " WHERE id=?";

    /**
     * Скрипт поиска группы пользователей по ее идентификатору
     */
    public static final String SQL_FIND_GROUP_BY_ID = "SELECT id, name FROM " + GROUP_TABLE +
            " where id = ?";

    /**
     * Скрипт обновления данных группы пользователей
     */
    public static final String SQL_UPDATE_GROUP = "UPDATE " + GROUP_TABLE + " SET name = ? WHERE id = ?";

    /**
     * Скрипт получения количества групп пользователей
     */
    public static final String SQL_COUNT_GROUP = "SELECT COUNT(id) FROM " + GROUP_TABLE;

    /**
     * Скрипт поиска группы пользователей по наименованию
     */
    public static final String SQL_FIND_GROUP_BY_NAME= "SELECT id, name FROM " + GROUP_TABLE + " WHERE name = ?";


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

    public AbstractJDBCGroupDAO(Connection conn){
        this.conn = conn;
    }


    /**
     * Создание нового экземпляра класса {@link Group}
     *
     * @param entity Объект класса @{link Group}
     * @return сохраненный в БД объект класса {@link Group} с присвоенным есц уникальным идентификатором
     */
    @Override
    abstract public Group create(Group entity) throws DAOException;

    /**
     * Удаление группы пользователей из БД
     *
     * @param entity Объект класса {@link Group}, однозначно по идентификатору характеризующий удаляемую запись в БД
     */
    @Override
    public int delete(Group entity) throws DAOException {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(SQL_DELETE_GROUP);
            return prepStmt.executeUpdate();
        }catch(SQLException sqlExc){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_GROUP_DELETE.toString(), sqlExc);
        }finally {
            try {
                prepStmt.close();
            }catch(SQLException excClose){
                throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_CLOSE_STATEMENT.toString(), excClose);
            }
        }
    }

    /**
     * Поиск группы пользователей по идентификатору
     *
     * @param id Идентификатор группы пользователей
     * @return объект класса {@link Group}, соотвествующий указанному идентификатору группы пользователей, или null - при отсутствии
     */
    @Override
    public Group findById(Integer id) throws DAOException {
        PreparedStatement prepStmt = null;
        try {
            if( id == null )
                return null;
            prepStmt = conn.prepareStatement(SQL_FIND_GROUP_BY_NAME);
            ResultSet rs = prepStmt.executeQuery();
            Group group = null;
            if(rs.next()) {
                group = new Group(rs.getString("name"));
                group.setId(rs.getInt("id"));
            }
            return group;
        }catch(SQLException sqlExc){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_GROUP_FIND_BY_ID.toString(), sqlExc);
        }finally {
            try {
                prepStmt.close();
            }catch(SQLException closeExc){
                throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_CLOSE_STATEMENT.toString(), closeExc);
            }
        }
    }

    /**
     * Обновление группы пользователей в БД по указанным в параметре новым данным
     *
     * @param group Объект класса {@link Group}, содержащий обновленные данные для обновляемой группы пользователей. Поле {@link Group#id} обязательно для заполнения
     */
    @Override
    public int update(Group group) throws DAOException {
        PreparedStatement prepStmt = null;
        try {
            if( group == null || group.getId() == null )
                return 0;
            prepStmt = conn.prepareStatement(SQL_UPDATE_GROUP);
            prepStmt.setInt(1, group.getId());
            return prepStmt.executeUpdate();

        }catch(SQLException sqlExc){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_GROUP_UPDATE.toString(), sqlExc);
        }finally{
            try {
                if( prepStmt != null)
                prepStmt.close();
            }catch(SQLException closeExc){
                throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_CLOSE_STATEMENT.toString(), closeExc);
            }
        }
    }

    /**
     * Получение количества групп пользователей в БД
     * @return  Количество групп пользователей в БД
     */
    @Override
    public int getCount() throws DAOException {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(SQL_COUNT_GROUP);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next())
                return rs.getInt(1);
            return 0;
        }catch (SQLException sqlExc){
            throw new DAOException( DAOExceptionSource.EXCEPTION_DAO_GROUP_COUNT.toString(), sqlExc );
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
     * Получения списка объектов класса {@link Group}, соотвествующим имеющимся в БД записям.
     *
     * @param offset Начальный индекс записи начала вывода
     * @param limit  Максимальное количество выводимых записей. Если = -1, то без ограничения
     */
    @Override
    abstract public List<Group> getList(Integer offset, Integer limit) throws DAOException;
}
