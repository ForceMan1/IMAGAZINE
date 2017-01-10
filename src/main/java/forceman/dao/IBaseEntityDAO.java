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
     */
    public T create(T entity) throws DAOException;

    /**
     * Удаление
     * @param id Удаляемый экземпляр класса T
     */
    public int deleteById(V id) throws DAOException;

    /**
     * Поиск экземпляра по идентификатору
     * @param id Идентификатор экземпляра
     */
    public T findById(V id) throws DAOException;

    /**
     * Обновление данных
     * @param entity Обновляемый экземпляр класса T
     */
    public int update(T entity) throws DAOException;

    /**
     * Получение количества экземпляров класса T
     */
    public int getCount() throws DAOException;

    /**
     * Получения списка экземпляров класса T
     * @param limit Максимальное количество выводимых записей. Если = -1, то без ограничения
     * @param offset Начальный индекс записи начала вывода
     */
    public List<T> getList(V limit, V offset) throws DAOException;
}
