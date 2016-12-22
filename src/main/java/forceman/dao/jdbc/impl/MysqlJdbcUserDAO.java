package forceman.dao.jdbc.impl;

import forceman.dao.DAOException;
import forceman.dao.DAOExceptionSource;
import forceman.dao.jdbc.AbstractJDBCUserDAO;
import forceman.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Igor on 22.12.2016.
 * Реализация класса операций над объектами класса {@link User} для использования с СУБД MySQL или PostreSQL
 * в связи с использованием комбинации LIMIT ... OFFSET для выборки пользователей
 */
public class MysqlJdbcUserDAO extends AbstractJDBCUserDAO {
    /**
     * Скрипт получения списка пользователей
     */
    public static final String SQL_LIST_USER = "SELECT id, fio, birthday, login, active FROM users LIMIT ? OFFSET ?";

    /**
     * Конструктор с параметром объекта соединения до БД
    */
    public MysqlJdbcUserDAO(Connection conn){
        super(conn);
    }
    /**
     * Получения списка пользователей
     *
     * @param offset Начальный индекс записи начала вывода
     * @param limit  Максимальное количество выводимых клиентов. Если = -1, то без ограничения
     */
    public List<User> getListUsers(int offset, int limit) throws DAOException {
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
}
