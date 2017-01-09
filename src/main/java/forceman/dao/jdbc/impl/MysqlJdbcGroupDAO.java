package forceman.dao.jdbc.impl;

import forceman.dao.DAOException;
import forceman.dao.DAOExceptionSource;
import forceman.dao.jdbc.AbstractJDBCGroupDAO;
import forceman.entity.Group;
import forceman.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Igor on 09.01.2017.
 * ���������� ������ �������� ��� ��������� ������ {@link Group} ��� ������������� � ���� MySQL
 * � ����� � �������������� ���������� LIMIT ... OFFSET ��� ������� ����� �������������
 * � ���������� �������� �������� �������� ������� nextval()
 */
public class MysqlJdbcGroupDAO extends AbstractJDBCGroupDAO {
    /**
     * ������ ��������� ������ ����� �������������
     */
    public static final String SQL_LIST_GROUP = "SELECT id, name FROM " + GROUP_TABLE + " LIMIT ? OFFSET ?";

    /**
     * ������ ������������� ������� ������������������� ��� ������� ����� �������������
     */
    public static final String SQL_SEQUENCE_INIT_GROUPS = "INSERT INTO SEQUENCE_DATA (SEQUENCE_NAME) VALUES ('groups')";
    /**
     * ������ ��������� ���������� �������� ������������������ ��� ������� ����� �������������
     */
    public static final String SQL_ID_NEXTVAL = "SELECT nextval('groups')";

    /**
     * ������ ��������� �������� �������� ������������������ ��� ������� ����� �������������
     */
    public static final String SQL_ID_CURRVAL = "SELECT currval('groups')";

    /**
     * �������� ������ ���������� ������ {@link Group}
     *
     * @param group ������ ������ @{link Group}
     * @return ����������� � �� ������ ������ {@link Group} � ����������� ��� ���������� ���������������, ��� null - � ������ group == null
     */
    @Override
    public Group create(Group group) throws DAOException {
        if( group == null )
            return null;
        PreparedStatement prepStmt = null;

        try {
            prepStmt = conn.prepareStatement(SQL_ID_CURRVAL);
            ResultSet rs = prepStmt.executeQuery();
            Integer id = null;
            if(rs.next())
                id = rs.getObject(1) != null ? rs.getInt(1) : null;
            if( id == null ) // ����������� ������ � ������� ������������������ ��� ������� ����� �������������
            {
                // ������� ������ ������
                prepStmt = conn.prepareStatement( SQL_SEQUENCE_INIT_GROUPS );
                prepStmt.execute();
                prepStmt.close();
            }
            // �������� ����� ������������� ������ �������������
            prepStmt = conn.prepareStatement(SQL_ID_NEXTVAL);
            rs = prepStmt.executeQuery();
            if( rs.next() )
                id = rs.getObject(1) != null ? rs.getInt(1) : null;
            if( id == null ) // ����������� ������ � ������� ������������������ ��� ������� ����� �������������
            {
                throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_GROUP_CREATE.toString());
            }
            group.setId( id );
            prepStmt.close();

            prepStmt = conn.prepareStatement(SQL_CREATE_GROUP);
            prepStmt.setInt(1, id);
            prepStmt.setString(2, group.getName());
            prepStmt.executeUpdate();
            return group;
        }catch(SQLException sqlExc){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_GROUP_CREATE.toString(), sqlExc);
        }finally {
            if( prepStmt != null )
                try {
                    prepStmt.close();
                }catch (SQLException closeExc){
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
    public List<Group> getList(Integer offset, Integer limit) throws DAOException {
        PreparedStatement prepStmt = null;
        List<Group> groups = null;
        try {
            prepStmt = conn.prepareStatement(SQL_LIST_GROUP);

        }catch(SQLException sqlExc){
            throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_GROUP_LIST.toString(), sqlExc);
        }finally{
            try {
                prepStmt.close();
            }catch(SQLException closeExc){
                throw new DAOException(DAOExceptionSource.EXCEPTION_DAO_CLOSE_STATEMENT.toString(), closeExc);
            }
        }
        return null;
    }

    public MysqlJdbcGroupDAO(Connection conn){
        super(conn);
    }
}
