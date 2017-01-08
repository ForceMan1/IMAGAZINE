package forceman.dao.jdbc;

import forceman.dao.DAOException;
import forceman.dao.IGroupDAO;
import forceman.entity.Group;

import java.util.List;

/**
 * Created by Igor on 21.12.2016.
 */
public class AbstractJDBCGroupDAO implements IGroupDAO {
    /**
     * �������� ������ ���������� ������ {@link Group}
     *
     * @param entity ������ ������ @{link Group}
     * @return ����������� � �� ������ ������ {@link Group} � ����������� ��� ���������� ���������������
     */
    @Override
    public Group create(Group entity) throws DAOException {
        return null;
    }

    /**
     * �������� ������ ������������� �� ��
     *
     * @param entity ������ ������ {@link Group}, ���������� �� ������������� �������������� ��������� ������ � ��
     */
    @Override
    public int delete(Group entity) throws DAOException {
        return 0;
    }

    /**
     * ����� ������ ������������� �� ��������������
     *
     * @param id ������������� ������ �������������
     * @return ������ ������ {@ling Group}, �������������� ���������� �������������� ������ �������������
     */
    @Override
    public Group findById(Integer id) throws DAOException {
        return null;
    }

    /**
     * ���������� ������ ������������� � �� �� ��������� � ��������� ����� ������
     *
     * @param entity ������ ������ {@link Group}, ���������� ����������� ������ ��� ����������� ������ �������������. ���� {@link Group#id} ����������� ��� ����������
     */
    @Override
    public int update(Group entity) throws DAOException {
        return 0;
    }

    /**
     * ��������� ���������� ����� ������������� � ��
     * @return  ���������� ����� ������������� � ��
     */
    @Override
    public int getCount() throws DAOException {
        return 0;
    }

    /**
     * ��������� ������ �������� ������ {@link Group}, �������������� ��������� � �� �������.
     *
     * @param offset ��������� ������ ������ ������ ������
     * @param limit  ������������ ���������� ��������� �������. ���� = -1, �� ��� �����������
     */
    @Override
    public List<Group> getList(Integer offset, Integer limit) throws DAOException {
        return null;
    }
}
