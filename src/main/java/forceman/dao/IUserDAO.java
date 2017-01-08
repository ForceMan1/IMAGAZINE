package forceman.dao;

import forceman.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Igor on 22.12.2016.
 */
public interface IUserDAO<V extends Number> extends IBaseEntityDAO<User, V>{

    /**
     * Обновление данных клиента с новым паролем
     * @param user обновляемый экземпляр класса {@link User}
     */
    public int updateWithPassword(User user) throws DAOException;

    /**
     * Проверка существования в таблице пользователя записи с указанным логином
     * @param login Логин
     * @return true, если данный логин уже имеется в таблице пользователей
     */
    public boolean isLoginExists(String login) throws DAOException;

}
