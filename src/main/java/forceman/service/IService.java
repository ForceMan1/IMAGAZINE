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
     * Сохранение нового пользователя {@link User}
     * @param user Объект класса {@link User}
     * @return созданный в БД экземпляр класса {@link User}
     * @throws DAOException
     */
    public User create(User user) throws DAOException;

    /**
     * Удаление пользователя из БД
     * @param id Идентифкатор удаляемого пользователя
     * @return 1 - в случае успешного создания пользователя
     * @throws DAOException
     */
    public int deleteById(V_USER id) throws DAOException;

    /**
     * Поиск экземпляра по идентификатору
     * @param id Идентификатор ользователя
     * @return Объект класса {@link User}
     * @throws DAOException
     */
    public User findById(V_USER id) throws DAOException;

    /**
     * Обновление данных
     * @param user Объект класса {@link User}, содержащий обновленные данные
     * @return 1 - в случае успешного ообновления
     * @throws DAOException
     */
    public int update(User user) throws DAOException;

    /**
     * Получение количества пользователей
     * @return Количество пользователей
     * @throws DAOException
     */
    public int getCount() throws DAOException;

    /**
     * Получения списка пользователей
     * @param limit Максимальное количество выводимых записей. Если = -1, то без ограничения
     * @param offset Начальный индекс записи начала вывода
     * @throws DAOException
     */
    public List<User> getList(Integer limit, Integer offset) throws DAOException;
}
