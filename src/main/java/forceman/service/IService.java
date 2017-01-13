package forceman.service;

import forceman.dao.DAOException;
import forceman.entity.Group;
import forceman.entity.Permission;
import forceman.entity.User;

import java.util.List;

/**
 * Created by Igor on 12.01.2017.
 */
public interface IService<V_USER extends Number, V_GROUP extends Number, V_PERM extends Number> {
    /** USER ****************/
    /**
     * ���������� ������ ������������ {@link User}
     * @param user ������ ������ {@link User}
     * @return ��������� � �� ��������� ������ {@link User}
     * @throws DAOException
     */
    public User create(User user) throws DAOException;

    /**
     * �������� ������������ �� ��
     * @param id ������������ ���������� ������������
     * @return 1 - � ������ ��������� �������� ������������
     * @throws DAOException
     */
    public int deleteById(V_USER id) throws DAOException;

    /**
     * ����� ���������� �� ��������������
     * @param id ������������� �����������
     * @return ������ ������ {@link User}
     * @throws DAOException
     */
    public User findById(V_USER id) throws DAOException;

    /**
     * ���������� ������
     * @param user ������ ������ {@link User}, ���������� ����������� ������
     * @return 1 - � ������ ��������� �����������
     * @throws DAOException
     */
    public int update(User user) throws DAOException;

    /**
     * ��������� ���������� �������������
     * @return ���������� �������������
     * @throws DAOException
     */
    public int getCount() throws DAOException;

    /**
     * ��������� ������ �������������
     * @param limit ������������ ���������� ��������� �������. ���� = -1, �� ��� �����������
     * @param offset ��������� ������ ������ ������ ������
     * @throws DAOException
     */
    public List<User> getList(Integer limit, Integer offset) throws DAOException;
}
