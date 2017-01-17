package forceman.service;

import forceman.dao.DAOException;
import forceman.dao.IGroupDAO;
import forceman.dao.IPermissionDAO;
import forceman.dao.IUserDAO;
import forceman.entity.User;

import java.util.List;

/**
 * Created by Igor on 17.01.2017.
 */
public class Service implements  IService<Integer> {
    private IUserDAO<Integer> userDAO = null;
    private IGroupDAO<Integer> groupDAO = null;
    private IPermissionDAO<Integer> permissionDAO = null;

    public Service(IUserDAO<Integer> userDAO, IGroupDAO<Integer> groupDAO, IPermissionDAO<Integer> permissionDAO) {
        this.userDAO = userDAO;
        this.groupDAO = groupDAO;
        this.permissionDAO = permissionDAO;
    }
    /**
     * ���������� ������ ������������ {@link User}
     *
     * @param user ������ ������ {@link User}
     * @return ��������� � �� ��������� ������ {@link User}
     * @throws DAOException
     */
    @Override
    public User create(User user) throws DAOException {
        return null;
    }

    /**
     * �������� ������������ �� ��
     *
     * @param id ������������ ���������� ������������
     * @return 1 - � ������ ��������� �������� ������������
     * @throws DAOException
     */
    @Override
    public int deleteById(Integer id) throws DAOException {
        return 0;
    }

    /**
     * ����� ���������� �� ��������������
     *
     * @param id ������������� �����������
     * @return ������ ������ {@link User}
     * @throws DAOException
     */
    @Override
    public User findById(Integer id) throws DAOException {
        return null;
    }

    /**
     * ���������� ������
     *
     * @param user ������ ������ {@link User}, ���������� ����������� ������
     * @return 1 - � ������ ��������� �����������
     * @throws DAOException
     */
    @Override
    public int update(User user) throws DAOException {
        return 0;
    }

    /**
     * ��������� ���������� �������������
     *
     * @return ���������� �������������
     * @throws DAOException
     */
    @Override
    public int getCountUsers() throws DAOException {
        return 0;
    }

    /**
     * ��������� ������ �������������
     *
     * @param limit  ������������ ���������� ��������� �������. ���� = -1, �� ��� �����������
     * @param offset ��������� ������ ������ ������ ������
     * @throws DAOException
     */
    @Override
    public List<User> getListUsers(Integer limit, Integer offset) throws DAOException {
        return null;
    }
}
