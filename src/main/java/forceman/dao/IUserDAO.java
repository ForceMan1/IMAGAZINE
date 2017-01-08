package forceman.dao;

import forceman.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Igor on 22.12.2016.
 */
public interface IUserDAO<V extends Number> extends IBaseEntityDAO<User, V>{

    /**
     * ���������� ������ ������� � ����� �������
     * @param user ����������� ��������� ������ {@link User}
     */
    public int updateWithPassword(User user) throws DAOException;

    /**
     * �������� ������������� � ������� ������������ ������ � ��������� �������
     * @param login �����
     * @return true, ���� ������ ����� ��� ������� � ������� �������������
     */
    public boolean isLoginExists(String login) throws DAOException;

}
