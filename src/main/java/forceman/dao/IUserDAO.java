package forceman.dao;

import forceman.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Igor on 22.12.2016.
 */
public interface IUserDAO {
    /**
     * �������� ������ ������������
     * @param user ������� ������ {@link User}
     * @return 1 - � ������ ��������� ���������� ������������
     */
    public int createUser(User user) throws DAOException;

    /**
     * �������� ������������
     */
    public int deleteUser(User user) throws DAOException;

    /**
     * ����� ������������ �� ��������������
     */
    public User findUserById(int id) throws DAOException;

    /**
     * ���������� ������ �������
     */
    public int updateUser(User user) throws DAOException;

    /**
     * ��������� ���������� �������������
     */
    public int getCountUsers() throws DAOException;

    /**
     * ��������� ������ �������������
     * @param offset ��������� ������ ������ ������ ������
     * @param limit ������������ ���������� ��������� ��������. ���� = -1, �� ��� �����������
     */
    public List<User> getListUsers(int offset, int limit) throws DAOException;

    /**
     * ����� ������������ �� �������������� {@link User#id}
     */
}
