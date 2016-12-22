package forceman.dao.jdbc;

import forceman.dao.IUserDAO;
import forceman.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by Igor on 21.12.2016.d
 * ����� ��� ���������� ��������� ���� User User {@link User}
 */
abstract public class AbstractJDBCUserDAO implements IUserDAO {
    /**
     * ������������ ������� ������������� � ��
     */
    public static final String USERS_TABLE = "users";
    /**
     * ������ �������� ������ ������������
     */
   public static final String SQL_CREATE_USER = "INSERT INTO " + USERS_TABLE + " (fio, birthday, login, password, active) " +
            "VALUES (?, ?, ?, ?, ?)";

    /**
     * ������ �������� ������������
     */
    public static final String SQL_DELETE_USER = "DELETE FROM " + USERS_TABLE + " WHERE id=?";

    /**
     * ������ ������ ������������ �� ��� ��������������
     */
    public static final String SQL_FIND_USER = "SELECT id, fio, birthday, login, active FROM " + USERS_TABLE +
                                                                                                " where id = ?";

    /**
     * ������ ���������� ������ ������������
     */
    public static final String SQL_UPDATE_USER = "UPDATE " + USERS_TABLE + " SET fio = ?, birthday = ?, " +
            "login = ?, active = ? WHERE id = ?";

    /**
     * ������ ��������� ���������� �������������
     */
    public static final String SQL_COUNT_USER = "SELECT COUNT(id) FROM " + USERS_TABLE;

    /**
     * ��������� �� ��
     */
    protected Connection conn;

   /**
     *
     * @param conn ��������� ���������� � ��
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
     * �������� ������ ������������
     * @param user ������� ������ {@link User}
     * @return 1 - � ������ ��������� ���������� ������������
      */
    public int createUser(User user) throws SQLException{
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(SQL_CREATE_USER);
            prepStmt.setString(1, user.getFio());
            prepStmt.setString(2, user.getLogin());
            prepStmt.setString(3, user.getPassword());
            prepStmt.setBoolean(4, user.getActive());
            prepStmt.setDate(1, new java.sql.Date(user.getBirthday().getTime()));
            return prepStmt.executeUpdate();
        }catch(SQLException sqlExc){

        }finally {
            if(prepStmt != null)
                prepStmt.close();
        }
        return 0;
    }

    /**
     * �������� ������������
     */
    public int deleteUser(User user) throws SQLException {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(SQL_DELETE_USER);
            prepStmt.setInt(1, user.getId());
            return prepStmt.executeUpdate();
        }catch(SQLException sqlExc){

        }finally{
            if(prepStmt != null)
                prepStmt.close();
        }
        return 0;
    }

    /**
     * ����� ������������ �� ��������������
     */
    public User findUser(int id) throws SQLException{
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(SQL_FIND_USER);
            prepStmt.setInt(1, id);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next()){
                User user = new User(rs.getString("name"), rs.getDate("birthday"), rs.getString("login"),
                    rs.getBoolean("active"));
                user.setId(rs.getInt("id"));
                return user;
            }
        }catch(SQLException sqlExc){

        }finally {
            if(prepStmt != null)
                prepStmt.close();
        }
        return null;
    }

    /**
     * ���������� ������ �������
     */
    public int updateUser(User user) throws SQLException {
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

        }finally {
            if(prepStmt != null)
                prepStmt.close();
        }
        return 0;
    }

    /**
     * ��������� ���������� �������������
     */
    public int getCountUsers() throws SQLException{
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(SQL_COUNT_USER);
            ResultSet rs = prepStmt.executeQuery();
            rs.next();
            return rs.getInt(1);

        }catch (SQLException sqlExc){

        }finally {
            if(prepStmt != null)
                prepStmt.close();
        }
        return -1;
    }

    /**
     * ��������� ������ �������������
     * @param offset ��������� ������ ������ ������ ������
     * @param limit ������������ ���������� ��������� ��������. ���� = -1, �� ��� �����������
     */
    abstract public List<User> getListUsers(int offset, int limit) throws SQLException;


}
