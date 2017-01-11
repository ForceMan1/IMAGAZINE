package forceman.dao.jdbc;

import forceman.dao.DAOException;
import forceman.dao.DAOExceptionSource;
import forceman.dao.IUserDAO;
import forceman.entity.User;
import forceman.security.IPasswordHash;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Igor on 21.12.2016.d
 * ����� ��� ���������� ��������� ���� User User {@link User}
 */
abstract public class AbstractJDBCUserDAO implements IUserDAO<Integer> {
    /**
     * ������������ ������� ������������� � ��
     */
    public static final String USER_TABLE = "users";
    /**
     * ������ �������� ������ ������������
     */
   public static final String SQL_CREATE_USER = "INSERT INTO " + USER_TABLE + " (id, fio, birthday, login, password, salt, active) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * ������ �������� ������������
     */
    public static final String SQL_DELETE_USER = "DELETE FROM " + USER_TABLE + " WHERE id=?";

    /**
     * ������ ������ ������������ �� ��� ��������������
     */
    public static final String SQL_FIND_USER_BY_ID = "SELECT id, fio, birthday, login, active FROM " + USER_TABLE +
                                                                                                " where id = ?";

    /**
     * ������ ���������� ������ ������������ (��� ���������� ������)
     */
    public static final String SQL_UPDATE_USER = "UPDATE " + USER_TABLE + " SET fio = ?, birthday = ?, " +
            "login = ?, active = ? WHERE id = ?";

    /**
     * ������ ���������� ������ ������������ (� ����������� ������)
     */
    public static final String SQL_UPDATE_USER_WITH_PASSWORD = "UPDATE " + USER_TABLE + " SET fio = ?, birthday = ?, " +
            "login = ?, password = ?, salt = ? , active = ? WHERE id = ?";

    /**
     * ������ ��������� ���������� �������������
     */
    public static final String SQL_COUNT_USER = "SELECT COUNT(id) FROM " + USER_TABLE;

    /**
     * ������ ������������� ������ � ������� �������������
     */
    public static final String SQL_FIND_LOGIN = "SELECT login FROM " + USER_TABLE + " WHERE login = ?";



    /**
     * ����� ������������ ���� ������
     */
    protected IPasswordHash pswdHashImpl;

    /**
     * ��������� �� ��
     */
    protected Connection conn;

   /**
     *
     * @param conn ��������� ���������� � ��
     */
    public AbstractJDBCUserDAO(Connection conn, IPasswordHash pswdHashImpl){
        this.conn = conn;
        this.pswdHashImpl = pswdHashImpl;
    }

   public Connection getConn() {
        return conn;
    }

   public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * �������� ������ ������������
     * @param user ������ ������ @{link User}
     * @return ������ ������ @{link User}, ����������� � �� � ������������ ��� ���������� ���������������
     * @throws DAOException - � ������ ������������� ������ ��� ���������� SQL �������� ��� ��� �������� � �������� �������� - null
     *        � �������� user ��� ��� ����
      */
    abstract public User create(User user) throws DAOException;

    /**
     * ��������� ������ ����������� ������ T
     * @param limit ������������ ���������� ��������� �������. ���� = -1, �� ��� �����������
     * @param offset ��������� ������ ������ ������ ������
     * @return ������ �������������
     * @throws DAOException - � ������ ������������� ������ ��� ���������� SQL �������� ��� ��� �������� � �������� �������� - null
     *        � ��������� limit ��� offset
     */
    abstract public List<User> getList(Integer limit, Integer offset) throws DAOException;

    /**
     * �������� ������������
     * @param id ������������� ������������
     * @return 1 - ��� ������� �������� ������������
     * @throws DAOException - � ������ ������������� ������ ��� ���������� SQL �������� ��� ��� �������� � �������� �������� - null
     *        � �������� user ��� ��� ���� id.
     */
    public int deleteById(Integer id) throws DAOException {
        if( id == null  )
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_DELETE.toString(), new NullPointerException());
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(SQL_DELETE_USER);
            prepStmt.setInt(1, id);
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
     * ����� ������������ �� ��������������
     * @param id ������������� ������������
     * @return ������ ������ {@link User}, �������������� ���������� ������������� ��� null - ��� ���������� ������� ������������
     * @throws DAOException - � ������ ������������� ������ ��� ���������� SQL �������� ��� ��� �������� � �������� �������� - null
     *        � ��������� id
     */
    public User findById(Integer id) throws DAOException{
        if( id == null )
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_FIND_BY_ID.toString(), new NullPointerException());
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
     * ���������� ������ ������� ()
     */
    public int update(User user) throws DAOException {
        if( user == null )
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_UPDATE.toString());
        if( user.getFio() == null || user.getFio().trim().isEmpty() )
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_UPDATE.toString());
        if( user.getBirthday() == null || user.getBirthday().toString().trim().isEmpty() )
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_UPDATE.toString());
        if( user.getLogin() == null || user.getLogin().trim().isEmpty())
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_UPDATE.toString());

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

    public int updateWithPassword(User user) throws DAOException {
        if( user == null )
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_UPDATE.toString());
        if( user.getFio() == null || user.getFio().trim().isEmpty() )
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_UPDATE.toString());
        if( user.getBirthday() == null || user.getBirthday().toString().trim().isEmpty() )
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_UPDATE.toString());
        if( user.getLogin() == null || user.getLogin().trim().isEmpty())
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_UPDATE.toString());

        PreparedStatement prepStmt = null;
        try {
                String salt = pswdHashImpl.getNextSalt();
                String saltedPaswd = null;
                try {
                    saltedPaswd = pswdHashImpl.createHash(user.getPassword(), salt);
                    user.setPassword("");
                }catch ( NoSuchAlgorithmException algExc ){
                    throw new DAOException( DAOExceptionSource.EXCEPTION_DAO_USER_CREATE_PASSWORD.toString(), algExc );
                }catch( IllegalArgumentException illegalArgExc) {
                    throw new DAOException( DAOExceptionSource.EXCEPTION_DAO_USER_CREATE_PASSWORD.toString(), illegalArgExc);
                }
                prepStmt = conn.prepareStatement(SQL_UPDATE_USER_WITH_PASSWORD);
                prepStmt.setString(1, user.getFio());
                prepStmt.setDate(2, new java.sql.Date(user.getBirthday().getTime()));
                prepStmt.setString(3, user.getLogin());
                prepStmt.setString(4, saltedPaswd);
                prepStmt.setString(5, salt);
                prepStmt.setBoolean(6, user.getActive());
                prepStmt.setInt(7, user.getId());
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
     * ��������� ���������� �������������
     */
    public int getCount() throws DAOException{
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
     * �������� ������������� � ������� ������������ ������ � ��������� �������
     * @param login �����
     * @return true, ���� ������ ����� ��� ������� � ������� �������������
     */
    public boolean isLoginExists(String login) throws DAOException {
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            if(login == null || login.isEmpty())
                throw new DAOException( DAOExceptionSource.EXCEPTION_DAO_USER_FIND_LOGIN_ILLEGAL_ARGUMENT.toString() );
            prepStmt = conn.prepareStatement( SQL_FIND_LOGIN );
            prepStmt.setString(1, login);
            rs = prepStmt.executeQuery();
            if( rs.next() ) {
                return rs.getObject(1) != null;
            }else
                return false;
        }catch (SQLException sqlExc) {
            throw new DAOException( DAOExceptionSource.EXCEPTION_DAO_USER_FIND_LOGIN.toString(), sqlExc );
        }finally {
            if( prepStmt != null )
                try {
                    prepStmt.close();
                }catch(SQLException exc){
                    throw new DAOException( DAOExceptionSource.EXCEPTION_DAO_CLOSE_STATEMENT.toString(), exc);
                }
        }
    }
}
