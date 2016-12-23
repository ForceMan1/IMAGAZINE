package forceman.dao.jdbc;

import forceman.dao.DAOException;
import forceman.dao.DAOExceptionSource;
import forceman.dao.IUserDAO;
import forceman.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Igor on 21.12.2016.d
 * Класс для управления объектами типа User User {@link User}
 */
abstract public class AbstractJDBCUserDAO implements IUserDAO {
    /**
     * Наименование таблицы пользователей в БД
     */
    public static final String USERS_TABLE = "users";
    /**
     * Скрипт создания нового пользователя
     */
   public static final String SQL_CREATE_USER = "INSERT INTO " + USERS_TABLE + " (fio, birthday, login, password, active) " +
            "VALUES (?, ?, ?, ?, ?)";

    /**
     * Скрипт удаления пользователя
     */
    public static final String SQL_DELETE_USER = "DELETE FROM " + USERS_TABLE + " WHERE id=?";

    /**
     * Скрипт поиска пользователя по его идентификатору
     */
    public static final String SQL_FIND_USER_BY_ID = "SELECT id, fio, birthday, login, active FROM " + USERS_TABLE +
                                                                                                " where id = ?";

    /**
     * Скрипт обновления данных пользователя
     */
    public static final String SQL_UPDATE_USER = "UPDATE " + USERS_TABLE + " SET fio = ?, birthday = ?, " +
            "login = ?, active = ? WHERE id = ?";

    /**
     * Скрипт получения количества пользователей
     */
    public static final String SQL_COUNT_USER = "SELECT COUNT(id) FROM " + USERS_TABLE;

       /**
     * Коннектор до БД
     */
    protected Connection conn;

   /**
     *
     * @param conn Коннектор соединения с БД
     */
    public AbstractJDBCUserDAO(Connection conn){
        this.conn = conn;
    }

   public Connection getConn() {
        return conn;
    }

   public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * Создание нового пользователя
     * @param user Обяъект класса {@link User}
     * @return 1 - в случае успешного добавления пользователя
      */
    public int createUser(User user) throws DAOException {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(SQL_CREATE_USER);
            // fio, birthday, login, password, active
            prepStmt.setString(1, user.getFio());
            prepStmt.setDate(2, new java.sql.Date(user.getBirthday().getTime()));
            prepStmt.setString(3, user.getLogin());
            prepStmt.setString(4, user.getPassword());
            prepStmt.setBoolean(5, user.getActive());
            return prepStmt.executeUpdate();
        }catch(SQLException sqlExc){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_CREATE.name(), sqlExc);
        }finally {
            if(prepStmt != null)
                try {
                    prepStmt.close();
                }catch(SQLException closeExc){
                    throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_CLOSE_STATEMENT.name(), closeExc);
                }
        }
    }

    /**
     * Удаление пользователя
     */
    public int deleteUser(User user) throws DAOException {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(SQL_DELETE_USER);
            prepStmt.setInt(1, user.getId());
            return prepStmt.executeUpdate();
        }catch(SQLException sqlExc){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_DELETE.name(), sqlExc);
        }finally{
            if(prepStmt != null)
                try {
                    prepStmt.close();
                }catch(SQLException closeExc){
                    throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_CLOSE_STATEMENT.name(), closeExc);
                }
        }
    }

    /**
     * Поиск пользователя по идентификатору
     */
    public User findUserById(int id) throws DAOException{
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(SQL_FIND_USER_BY_ID);
            prepStmt.setInt(1, id);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next()){
                User user = new User(rs.getString("name"), rs.getDate("birthday"), rs.getString("login"),
                    rs.getBoolean("active"));
                user.setId(rs.getInt("id"));
                return user;
            }
        }catch(SQLException sqlExc){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_FIND_BY_ID.name(), sqlExc);
        }finally {
            if(prepStmt != null)
                try {
                    prepStmt.close();
                }catch(SQLException closeExc){
                    throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_CLOSE_STATEMENT.name(), closeExc);
                }
        }
        return null;
    }

    /**
     * Обновление данных клиента
     */
    public int updateUser(User user) throws DAOException {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(SQL_UPDATE_USER);
            prepStmt.setString(1, user.getFio());
            prepStmt.setDate(2, new java.sql.Date(user.getBirthday().getTime()));
            prepStmt.setString(3, user.getLogin());
            prepStmt.setBoolean(4, user.getActive());
            prepStmt.setInt(5, user.getId());
            return prepStmt.executeUpdate();

        }catch(SQLException sqlExc){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_UPDATE.name(), sqlExc);
        }finally {
            if(prepStmt != null)
                try {
                    prepStmt.close();
                }catch(SQLException closeExc){
                    throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_CLOSE_STATEMENT.name(), closeExc);
                }
        }
   }

    /**
     * Получение количества пользователей
     */
    public int getCountUsers() throws DAOException{
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(SQL_COUNT_USER);
            ResultSet rs = prepStmt.executeQuery();
            rs.next();
            return rs.getInt(1);

        }catch (SQLException sqlExc){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_COUNT.name(), sqlExc);
        }finally {
            if(prepStmt != null)
                try {
                    prepStmt.close();
                }catch(SQLException closeExc){
                    throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_CLOSE_STATEMENT.name(), closeExc);
                }
        }
     }

    /**
     * Получения списка пользователей
     * @param offset Начальный индекс записи начала вывода
     * @param limit Максимальное количество выводимых клиентов. Если = -1, то без ограничения
     */
    abstract public List<User> getListUsers(int offset, int limit) throws DAOException;


}
