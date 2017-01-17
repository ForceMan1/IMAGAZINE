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
     * Сохранение нового пользователя {@link User}
     *
     * @param user Объект класса {@link User}
     * @return созданный в БД экземпляр класса {@link User}
     * @throws DAOException
     */
    @Override
    public User create(User user) throws DAOException {
        return null;
    }

    /**
     * Удаление пользователя из БД
     *
     * @param id Идентифкатор удаляемого пользователя
     * @return 1 - в случае успешного создания пользователя
     * @throws DAOException
     */
    @Override
    public int deleteById(Integer id) throws DAOException {
        return 0;
    }

    /**
     * Поиск экземпляра по идентификатору
     *
     * @param id Идентификатор ользователя
     * @return Объект класса {@link User}
     * @throws DAOException
     */
    @Override
    public User findById(Integer id) throws DAOException {
        return null;
    }

    /**
     * Обновление данных
     *
     * @param user Объект класса {@link User}, содержащий обновленные данные
     * @return 1 - в случае успешного ообновления
     * @throws DAOException
     */
    @Override
    public int update(User user) throws DAOException {
        return 0;
    }

    /**
     * Получение количества пользователей
     *
     * @return Количество пользователей
     * @throws DAOException
     */
    @Override
    public int getCountUsers() throws DAOException {
        return 0;
    }

    /**
     * Получения списка пользователей
     *
     * @param limit  Максимальное количество выводимых записей. Если = -1, то без ограничения
     * @param offset Начальный индекс записи начала вывода
     * @throws DAOException
     */
    @Override
    public List<User> getListUsers(Integer limit, Integer offset) throws DAOException {
        return null;
    }
}
