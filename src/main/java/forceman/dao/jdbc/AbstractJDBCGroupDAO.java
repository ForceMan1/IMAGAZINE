package forceman.dao.jdbc;

import forceman.dao.DAOException;
import forceman.dao.IGroupDAO;
import forceman.entity.Group;

import java.util.List;

/**
 * Created by Igor on 21.12.2016.
 */
public class AbstractJDBCGroupDAO implements IGroupDAO {
    /**
     * Создание нового экземпляра класса {@link Group}
     *
     * @param entity Объект класса @{link Group}
     * @return сохраненный в БД объект класса {@link Group} с присвоенным есц уникальным идентификатором
     */
    @Override
    public Group create(Group entity) throws DAOException {
        return null;
    }

    /**
     * Удаление группы пользователей из БД
     *
     * @param entity Объект класса {@link Group}, однозначно по идентификтору храктеризующий удаляемую запись в БД
     */
    @Override
    public int delete(Group entity) throws DAOException {
        return 0;
    }

    /**
     * Поиск группы пользователей по идентификатору
     *
     * @param id Идентификатор группы пользователей
     * @return объект класса {@ling Group}, соотвествующий указанному идентификатору группы пользователей
     */
    @Override
    public Group findById(Integer id) throws DAOException {
        return null;
    }

    /**
     * Обновление группы пользователей в БД по указанным в параметре новым данным
     *
     * @param entity Объект класса {@link Group}, содержащий обновленные данные для обновляемой группы пользователей. Поле {@link Group#id} обязательно для заполнения
     */
    @Override
    public int update(Group entity) throws DAOException {
        return 0;
    }

    /**
     * Получение количества групп пользователей в БД
     * @return  Количество групп пользователей в БД
     */
    @Override
    public int getCount() throws DAOException {
        return 0;
    }

    /**
     * Получения списка объектов класса {@link Group}, соотвествующим имеющимся в БД записям.
     *
     * @param offset Начальный индекс записи начала вывода
     * @param limit  Максимальное количество выводимых записей. Если = -1, то без ограничения
     */
    @Override
    public List<Group> getList(Integer offset, Integer limit) throws DAOException {
        return null;
    }
}
