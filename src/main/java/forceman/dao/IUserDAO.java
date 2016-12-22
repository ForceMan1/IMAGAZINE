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
    public int createUser(User user) throws SQLException;

    /**
     * �������� ������������
     */
    public int deleteUser(User user) throws SQLException;

    /**
     * ����� ������������ �� ��������������
     */
    public User findUser(int id) throws SQLException;

    /**
     * ���������� ������ �������
     */
    public int updateUser(User user) throws SQLException;

    /**
     * ��������� ���������� �������������
     */
    public int getCountUsers() throws SQLException;

    /**
     * ��������� ������ �������������
     * @param offset ��������� ������ ������ ������ ������
     * @param limit ������������ ���������� ��������� ��������. ���� = -1, �� ��� �����������
     */
    public List<User> getListUsers(int offset, int limit) throws SQLException;

    /**
     * ����� ������������ �� �������������� {@link User#id}
     */
}
