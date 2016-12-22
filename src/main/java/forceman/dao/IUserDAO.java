package forceman.dao;

import forceman.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Igor on 22.12.2016.
 */
public interface IUserDAO {
    /**
     * Создание нового пользователя
     * @param user Обяъект класса {@link User}
     * @return 1 - в случае успешного добавления пользователя
     */
    public int createUser(User user) throws DAOException;

    /**
     * Удаление пользователя
     */
    public int deleteUser(User user) throws DAOException;

    /**
     * Поиск пользователя по идентификатору
     */
    public User findUserById(int id) throws DAOException;

    /**
     * Обновление данных клиента
     */
    public int updateUser(User user) throws DAOException;

    /**
     * Получение количества пользователей
     */
    public int getCountUsers() throws DAOException;

    /**
     * Получения списка пользователей
     * @param offset Начальный индекс записи начала вывода
     * @param limit Максимальное количество выводимых клиентов. Если = -1, то без ограничения
     */
    public List<User> getListUsers(int offset, int limit) throws DAOException;

    /**
     * Поиск пользователя по идентификатору {@link User#id}
     */
}
