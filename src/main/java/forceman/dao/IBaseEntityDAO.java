package forceman.dao;

import forceman.entity.BaseEntity;

import java.util.List;

/**
 * Created by 1 on 07.01.2017.
 */
public interface IBaseEntityDAO<T extends BaseEntity, V extends Number> {
    /**
     * �������� ������ ���������� ������ T
     * @param entity ������ ������ T
     * @return ��������� ��������� ������ T
     * @throws DAOException
     */
    public T create(T entity) throws DAOException;

    /**
     * ��������
     * @param id ��������� ��������� ������ T
     * @return 1 - � ������ �������� ��������
     * @throws DAOException
     */
    public int deleteById(V id) throws DAOException;

    /**
     * ����� ���������� �� ��������������
     * @param id ������������� ����������
     * @return ��������� ������� T
     * @throws DAOException
     */
    public T findById(V id) throws DAOException;

    /**
     * ���������� ������
     * @param entity ����������� ��������� ������ T
     * @return  1 - � ������ ��������� ����������
     * @throws DAOException
     */
    public int update(T entity) throws DAOException;

    /**
     * ��������� ���������� ����������� ������ T
     * @return ���������� ����������� ������ T
     * @throws DAOException
     */
    public int getCount() throws DAOException;

    /**
     * ��������� ������ ����������� ������ T
     * @param limit ������������ ���������� ��������� �������. ���� = -1, �� ��� �����������
     * @param offset ��������� ������ ������ ������ ������
     * @return ������ ����������� ������ T
     * @throws DAOException
     */
    public List<T> getList(V limit, V offset) throws DAOException;
}
