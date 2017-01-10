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
 * ���������� ������ �������� ��� ��������� ������ {@link Permission} ��� ������������� � ���� MySQL
 * � ����� � �������������� ���������� LIMIT ... OFFSET ��� ������� ����� �������������
 * � ���������� �������� �������� �������� ������� nextval()
 */
public class MysqlJdbcPermissionDAO extends AbstractJDBCPermissionDAO {
    /**
     * ������ ��������� ������ ���� �������
     */
    public static final String SQL_LIST_PERMISSION = "SELECT id, name, code FROM " + PERMISSION_TABLE + " LIMIT ? OFFSET ?";

    /**
     * ������ ������������� ������� ������������������� ��� ������� ���� �������
     */
    public static final String SQL_SEQUENCE_INIT_PERMISSIONS = "INSERT INTO SEQUENCE_DATA (SEQUENCE_NAME) VALUES ('permissions')";
    /**
     * ������ ��������� ���������� �������� ������������������ ��� ������� ���� �������
     */
    public static final String SQL_ID_NEXTVAL = "SELECT nextval('permissions')";

    /**
     * ������ ��������� �������� �������� ������������������ ��� ������� ���� �������
     */
    public static final String SQL_ID_CURRVAL = "SELECT currval('permissions')";


    /**
     * ���������� ����� ������� � ��
     *
     * @param permission ������ ����� ������� {@link Permission}
     * @return ��������� ��������� ����� ������� � ����������� ��� ���������� ���������������
     */
    @Override
    public Permission create(Permission permission) throws DAOException {
        if(permission == null)
            return null;
        PreparedStatement prepStmt = null;
        try {
            // �������� ������������� ������� ������������������ ��� ���� �������
            Integer id = null;
            prepStmt = conn.prepareStatement(SQL_ID_CURRVAL);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next())
                id = rs.getObject(1) != null ? rs.getInt(1) : null;
            if( id == null ) // ���������� ������������� ������� ������������������ ��� ���� �������
            {
                // ������� ������ ������
                prepStmt = conn.prepareStatement( SQL_SEQUENCE_INIT_PERMISSIONS );
                prepStmt.execute();
                prepStmt.close();
            }
            // �������� ����� ������������� ������ ���� �������
            prepStmt = conn.prepareStatement(SQL_ID_NEXTVAL);
            rs = prepStmt.executeQuery();
            if( rs.next() )
                id = rs.getObject(1) != null ? rs.getInt(1) : null;
            if( id == null ) // ����������� ������ � ������� ������������������ ��� ������� ���� �������
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
     * ��������� ������ ���� ������� �� ��
     *
     * @param limit  ������������ ���������� ��������� �������. ���� = -1, �� ��� �����������
     * @param offset ��������� ������ ������ ������ ������
     * @return ������ ���� �������, null - � ������ ������������ �������� ���������� limit � offset
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
