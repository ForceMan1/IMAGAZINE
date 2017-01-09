package forceman.dao.jdbc.impl;

import forceman.dao.DAOException;
import forceman.dao.DAOExceptionSource;
import forceman.dao.jdbc.AbstractJDBCUserDAO;
import forceman.entity.User;
import forceman.security.IPasswordHash;
import forceman.security.PasswordHash;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Igor on 22.12.2016.
 * Реализация класса операций над объектами класса {@link User} для использования с СУБД MySQL
 * в связи с использованием комбинации LIMIT ... OFFSET для выборки пользователей
 * и реализации хранимых процедур эмуляции функции nextval()
 */
public class MysqlJdbcUserDAO extends AbstractJDBCUserDAO {
    /**
     * Скрипт получения списка пользователей
     */
    public static final String SQL_LIST_USER = "SELECT id, fio, birthday, login, active FROM " + USER_TABLE + " LIMIT ? OFFSET ?";

    /**
     * Скрипт инициализации таблицы последовательностей для таблицы пользователей
     */
    public static final String SQL_SEQUENCE_INIT_USERS = "INSERT INTO SEQUENCE_DATA (SEQUENCE_NAME) VALUES ('users')";
    /**
     * Скрипт получения следующего значения последовательности для таблицы пользователей
     */
    public static final String SQL_ID_NEXTVAL = "SELECT nextval('users')";

    /**
     * Скрипт получения текущего значения последовательности для таблицы пользователей
     */
    public static final String SQL_ID_CURRVAL = "SELECT currval('users')";

    /**
     * Конструктор с параметром объекта соединения до БД
    */
    public MysqlJdbcUserDAO(Connection conn, IPasswordHash pswdHashImpl){
        super(conn, pswdHashImpl);
    }


    /**
     * Функция получения списка пользователей
     *
     * @param offset Начальный индекс записи начала вывода
     * @param limit  Максимальное количество выводимых клиентов. Если = -1, то без ограничения
     */
    public List<User> getList(Integer offset, Integer limit) throws DAOException {
        PreparedStatement prepStmt = null;
        ArrayList<User> users = new ArrayList<>();
        try {
            prepStmt = conn.prepareStatement(SQL_LIST_USER);
            prepStmt.setInt(1, offset);
            prepStmt.setInt(2, limit);

            ResultSet rs = prepStmt.executeQuery();
            User user = null;
            while(rs.next()){
                user = new User(rs.getString("fio"), rs.getDate("birthday"), rs.getString("login"),
                        rs.getBoolean("active"));
                user.setId(rs.getInt("id"));
                users.add(user);
            }
         }catch(SQLException sqlExc){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_LIST.name(), sqlExc);
        }finally {
            if(prepStmt != null)
                try {
                    prepStmt.close();
                }catch(SQLException closeExc){
                    throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_CLOSE_STATEMENT.name(), closeExc);
                }
        }
        return users;
    }

    /**
     * Создание нового пользователя
     * @param user Обяъект класса {@link User}
     * @return экземпляр класса {@link User} - в случае успешного добавления пользователя
     */
    public User create(User user) throws DAOException {
        if( user == null )
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_CREATE.toString());
        if( user.getFio() == null || user.getFio().trim().isEmpty() )
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_CREATE.toString());
        if( user.getBirthday() == null || user.getBirthday().toString().trim().isEmpty() )
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_CREATE.toString());
        if( user.getLogin() == null || user.getLogin().trim().isEmpty())
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_CREATE.toString());

        PreparedStatement prepStmt = null;
        String saltedPswd = null;
        String salt = null;
        try {
            salt = pswdHashImpl.getNextSalt();
            try {
                saltedPswd = pswdHashImpl.createHash(user.getPassword(), salt);
            }catch(NoSuchAlgorithmException algExc) {
                throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_CREATE_PASSWORD.toString(), algExc);
            }


            // Проверяем наличие записи в таблице последовательности для пользователей
            prepStmt = conn.prepareStatement( SQL_ID_CURRVAL );
            ResultSet rs = prepStmt.executeQuery();
            Integer id = null;
            if(rs.next())
                id = rs.getObject(1) != null ? rs.getInt(1) : null;
            prepStmt.close();
            if( id == null ) // Отсутствует запись в таблице последовательности для таблицы пользователей
            {
                // Добавим данную запись
                prepStmt = conn.prepareStatement( SQL_SEQUENCE_INIT_USERS );
                prepStmt.execute();
                prepStmt.close();
            }

            // Получаем новый ID для пользователя
            prepStmt = conn.prepareStatement( SQL_ID_NEXTVAL );
            rs = prepStmt.executeQuery();
            if( rs.next() )
                id = rs.getObject(1) != null ? rs.getInt(1) : null;
            if( id == null ) // Отсутствует запись в таблице последовательности для таблицы пользователей
            {
                throw new DAOException( DAOExceptionSource.EXCEPTION_DAO_USER_CREATE.toString() );
            }
            prepStmt.close();
            user.setId( id );

            user.setPassword("");
            prepStmt = conn.prepareStatement(SQL_CREATE_USER);
            // id, fio, birthday, login, password, salt, active

            prepStmt.setInt(1, user.getId() );
            prepStmt.setString(2, user.getFio());
            prepStmt.setDate(3, new java.sql.Date(user.getBirthday().getTime()));
            prepStmt.setString(4, user.getLogin());
            prepStmt.setString(5, saltedPswd);
            prepStmt.setString(6, salt);
            prepStmt.setBoolean(7, user.getActive());
            prepStmt.executeUpdate();
            return user;
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

}
