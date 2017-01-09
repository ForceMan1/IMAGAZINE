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
     * ������������ ������� ����� ������������� � ��
     */
    public static final String GROUP_TABLE = "groups";
    /**
     * ������ �������� ����� ������ �������������
     */
    public static final String SQL_CREATE_GROUP = "INSERT INTO " + GROUP_TABLE + " (id, name) " +
            "VALUES (?, ?)";

    /**
     * ������ �������� ������ �������������
     */
    public static final String SQL_DELETE_GROUP = "DELETE FROM " + GROUP_TABLE + " WHERE id=?";

    /**
     * ������ ������ ������ ������������� �� �� ��������������
     */
    public static final String SQL_FIND_GROUP_BY_ID = "SELECT id, name FROM " + GROUP_TABLE +
            " where id = ?";

    /**
     * ������ ���������� ������ ������ �������������
     */
    public static final String SQL_UPDATE_GROUP = "UPDATE " + GROUP_TABLE + " SET name = ? WHERE id = ?";

    /**
     * ������ ��������� ���������� ����� �������������
     */
    public static final String SQL_COUNT_GROUP = "SELECT COUNT(id) FROM " + GROUP_TABLE;

    /**
     * ������ ������ ������ ������������� �� ������������
     */
    public static final String SQL_FIND_GROUP_BY_NAME= "SELECT id, name FROM " + GROUP_TABLE + " WHERE name = ?";


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

    public AbstractJDBCGroupDAO(Connection conn){
        this.conn = conn;
    }


    /**
     * �������� ������ ���������� ������ {@link Group}
     *
     * @param entity ������ ������ @{link Group}
     * @return ����������� � �� ������ ������ {@link Group} � ����������� ��� ���������� ���������������
     */
    @Override
    abstract public Group create(Group entity) throws DAOException;

    /**
     * �������� ������ ������������� �� ��
     *
     * @param entity ������ ������ {@link Group}, ���������� �� �������������� ��������������� ��������� ������ � ��
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
     * ����� ������ ������������� �� ��������������
     *
     * @param id ������������� ������ �������������
     * @return ������ ������ {@link Group}, �������������� ���������� �������������� ������ �������������, ��� null - ��� ����������
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
     * ���������� ������ ������������� � �� �� ��������� � ��������� ����� ������
     *
     * @param group ������ ������ {@link Group}, ���������� ����������� ������ ��� ����������� ������ �������������. ���� {@link Group#id} ����������� ��� ����������
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
     * ��������� ���������� ����� ������������� � ��
     * @return  ���������� ����� ������������� � ��
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
     * ��������� ������ �������� ������ {@link Group}, �������������� ��������� � �� �������.
     *
     * @param offset ��������� ������ ������ ������ ������
     * @param limit  ������������ ���������� ��������� �������. ���� = -1, �� ��� �����������
     */
    @Override
    abstract public List<Group> getList(Integer offset, Integer limit) throws DAOException;
}
