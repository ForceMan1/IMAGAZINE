package forceman.dao;

import forceman.entity.BaseEntity;

import java.util.List;

/**
 * Created by 1 on 07.01.2017.
 */
public interface IBaseEntityDAO<T extends BaseEntity, V extends Number> {
    /**
     * Создание нового экземпляра класса T
     * @param entity Объект класса T
     * @return созданный экземпляр класса T
     * @throws DAOException
     */
    public T create(T entity) throws DAOException;

    /**
     * Удаление
     * @param id Удаляемый экземпляр класса T
     * @return 1 - в случае удачного удаления
     * @throws DAOException
     */
    public int deleteById(V id) throws DAOException;

    /**
     * Поиск экземпляра по идентификатору
     * @param id Идентификатор экземпляра
     * @return Экземпляр классаа T
     * @throws DAOException
     */
    public T findById(V id) throws DAOException;

    /**
     * Обновление данных
     * @param entity Обновляемый экземпляр класса T
     * @return  1 - в случае успешного обновления
     * @throws DAOException
     */
    public int update(T entity) throws DAOException;

    /**
     * Получение количества экземпляров класса T
     * @return Количество экземпляров класса T
     * @throws DAOException
     */
    public int getCount() throws DAOException;

    /**
     * Получения списка экземпляров класса T
     * @param limit Максимальное количество выводимых записей. Если = -1, то без ограничения
     * @param offset Начальный индекс записи начала вывода
     * @return Список экземаляров класса T
     * @throws DAOException
     */
    public List<T> getList(V limit, V offset) throws DAOException;
}
