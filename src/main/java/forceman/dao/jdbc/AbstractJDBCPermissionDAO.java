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
     * ������������ ������� ���� ������� � ��
     */
    public static final String PERMISSION_TABLE = "permissions";
    /**
     * ������ �������� ������ ����� �������
     */
    public static final String SQL_CREATE_PERMISSION = "INSERT INTO " + PERMISSION_TABLE + " (id, name, code) " +
            "VALUES (?, ?, ?)";

    /**
     * ������ �������� ����� �������
     */
     public static final String SQL_DELETE_PERMISSION = "DELETE FROM " + PERMISSION_TABLE + " WHERE id=?";

     /**
     * ������ ������ ����� ������� ��� ��������������
     */
    public static final String SQL_FIND_PERMISSION_BY_ID = "SELECT id, name, code FROM " + PERMISSION_TABLE +
            " where id = ?";

    /**
     * ������ ���������� ������ ����� �������
     */
    public static final String SQL_UPDATE_PERMISSION = "UPDATE " + PERMISSION_TABLE + " SET name = ?, " +
            "code = ? WHERE id = ?";

    /**
     * ������ ��������� ���������� ���� �������
     */
    public static final String SQL_COUNT_PERMISSION = "SELECT COUNT(id) FROM " + PERMISSION_TABLE;

    /**
     * ������ ������ ����� ������� �� ��� ����
     */
    public static final String SQL_FIND_PERMISSION_BY_CODE = "SELECT id, name, code FROM " + PERMISSION_TABLE +
            " WHERE code = ?";

    /**
     * ������ ������ ����� ������� �� ��� ������������
     */
    public static final String SQL_FIND_PERMISSION_BY_NAME = "SELECT id, name, code FROM " +
            PERMISSION_TABLE + " WHERE name = ?";


    protected Connection conn;

    /**
     * ������� ��������� ���������� ��
     * @return ��������� ��
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * ������� ��������� ���������� ��
     * @param conn ��������� ��
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public AbstractJDBCPermissionDAO(Connection conn){
        this.conn = conn;
    }

    /**
     * ���������� ����� ������� � ��
     *
     * @param entity ������ ����� ������� {@link Permission}
     * @return ��������� ��������� ����� ������� � ����������� ��� ���������� ���������������
     */
    @Override
    abstract public Permission create(Permission entity) throws DAOException;

    /**
     * �������� ����� ������� �� �� �� ��� �������������� {@link Permission#id}
     *
     * @param permission ������ ������ ����� ������� {@link Permission}, ���������� ����������������
     *                  �������� ����� ������� �� ��� �������������� {@link Permission#id}
     * @return 1 - � ������ ��������� �������� ����� �������
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
     * ����� ����� ������� �� ��� ��������������
     *
     * @param id ������������� ����� �������
     * @return ��������� ������ ����� ������� {@link Permission} ��� null
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
     * ���������� ����� �������
     *
     * @param permission ��������� ������ {@link Permission}, ���������� ��������������� ����������� �����
     *                   ������� �� ������������� {@link Permission#id}
     * @return 1 - � ������ ��������� ���������� ����� �������
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
     * ��������� ���������� ���� �������
     * @return ���������� ���� �������
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
     * ��������� ������ ����������� ������ T
     *
     * @param limit  ������������ ���������� ��������� �������. ���� = -1, �� ��� �����������
     * @param offset ��������� ������ ������ ������ ������
     */
    @Override
    abstract public List<Permission> getList(Integer limit, Integer offset) throws DAOException;
}
