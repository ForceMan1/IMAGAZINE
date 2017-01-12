package forceman.dao.jdbc.impl;

import forceman.dao.DAOException;
import forceman.dao.DAOExceptionSource;
import forceman.dao.jdbc.AbstractJDBCUserDAO;
import forceman.entity.User;
import forceman.security.IPasswordHash;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 22.12.2016.
 * ���������� ������ �������� ��� ��������� ������ {@link User} ��� ������������� � ���� MySQL
 * � ����� � �������������� ���������� LIMIT ... OFFSET ��� ������� �������������
 * � ���������� �������� �������� �������� ������� nextval()
 */
public class MysqlJdbcUserDAO extends AbstractJDBCUserDAO {
    /**
     * ������ ��������� ������ �������������
     */
    public static final String SQL_LIST_USER = "SELECT id, fio, birthday, login, active FROM " + USER_TABLE + " LIMIT ? OFFSET ?";

    /**
     * ������ ������������� ������� ������������������� ��� ������� �������������
     */
    public static final String SQL_SEQUENCE_INIT_USERS = "INSERT INTO SEQUENCE_DATA (SEQUENCE_NAME) VALUES ('users')";
    /**
     * ������ ��������� ���������� �������� ������������������ ��� ������� �������������
     */
    public static final String SQL_ID_NEXTVAL = "SELECT nextval('users')";

    /**
     * ������ ��������� �������� �������� ������������������ ��� ������� �������������
     */
    public static final String SQL_ID_CURRVAL = "SELECT currval('users')";

    /**
     * ����������� � ���������� ������� ���������� �� ��
    */
    public MysqlJdbcUserDAO(Connection conn, IPasswordHash pswdHashImpl){
        super(conn, pswdHashImpl);
    }


    /**
     * ������� ��������� ������ �������������
     *
     * @param limit  ������������ ���������� ��������� ��������. ���� = -1, �� ��� �����������
     * @param offset ��������� ������ ������ ������ ������
     * @return ������ �������������
     * @throws DAOException - � ������ ������������� ������ ��� ���������� SQL �������� ��� ��� �������� � �������� �������� - null
     *        � ��������� limit ��� offset
     */
    public List<User> getList(Integer limit, Integer offset) throws DAOException {
        if( limit == null || offset ==null )
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_LIST.toString(), new NullPointerException());
        PreparedStatement prepStmt = null;
        ArrayList<User> users = new ArrayList<>();
        try {
            prepStmt = conn.prepareStatement(SQL_LIST_USER);
            prepStmt.setInt(1, limit);
            prepStmt.setInt(2, offset);

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
     * �������� ������ ������������
     * @param user ������� ������ {@link User}
     * @return ��������� ������ {@link User} - � ������ ��������� ���������� ������������
     * @throws DAOException - � ������ ������������� ������ ��� ���������� SQL �������� ��� ��� �������� � �������� �������� - null
     *        � ���� ��������� user ��� null - ������ user ������� (���������� ������ {@link User})
     */
    public User create(User user) throws DAOException {
        if( user == null )
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_CREATE.toString(), new NullPointerException());
        if( user.getFio() == null || user.getFio().trim().isEmpty() )
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_CREATE.toString(), new NullPointerException());
        if( user.getBirthday() == null || user.getBirthday().toString().trim().isEmpty() )
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_CREATE.toString(), new NullPointerException());
        if( user.getLogin() == null || user.getLogin().trim().isEmpty())
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_USER_CREATE.toString(), new NullPointerException());

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


            // ��������� ������� ������ � ������� ������������������ ��� �������������
            prepStmt = conn.prepareStatement( SQL_ID_CURRVAL );
            ResultSet rs = prepStmt.executeQuery();
            Integer id = null;
            if(rs.next())
                id = rs.getObject(1) != null ? rs.getInt(1) : null;
            prepStmt.close();
            if( id == null ) // ����������� ������ � ������� ������������������ ��� ������� �������������
            {
                // ������� ������ ������
                prepStmt = conn.prepareStatement( SQL_SEQUENCE_INIT_USERS );
                prepStmt.execute();
                prepStmt.close();
            }

            // �������� ����� ID ��� ������������
            prepStmt = conn.prepareStatement( SQL_ID_NEXTVAL );
            rs = prepStmt.executeQuery();
            if( rs.next() )
                id = rs.getObject(1) != null ? rs.getInt(1) : null;
            if( id == null ) // ����������� ������ � ������� ������������������ ��� ������� �������������
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
