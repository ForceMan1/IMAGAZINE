package forceman.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by Igor on 20.12.2016.
 *  Класс пользователя
 *  @version 2016212101
 *
 */
public class User {
    /**
     * Уникальный идентификатор пользователя
     */
    private int id;

    /**
     * ФИО пользователя
     */
    private String fio;

    /**
     * День рождения пользователя
     */
    private Date birthday;

    /**
     * Логин пользователя
     */
    private String login;

    /**
     *  Пароль пользователя
     */
    private String password;

    /**
     * Флаг активности пользователя
     */
    private boolean active;

    /**
     * Список группп, в которые входит пользователь
     */
    private List<Group> groups;

    /**
     * Функция получения идентификатора пользователя {@link User#id}
     * @return идентификатор пользователя
     */
    public int getId() {
        return id;
    }

    /**
     * Функция установки идентификатора пользователч
     * @param id Новый идентификатор пользователя {@link User#id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Функция получения ФИО пользователя {@link User#fio}
     * @return ФИО пользователя
     */
    public String getFio() {
        return fio;
    }

    /**
     * Функция установки нового ФИО пользователя {@link User#fio}
     * @param fio Новый ФИО пользователя
     */
    public void setFio(String fio) {
        this.fio = fio;
    }

    /**
     * Функция получения даты рождения пользователя {@link User#birthday}
     * @return Дата рождения пользователя
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Функция установки новой даты рождения пользователя {@link User#birthday}
     * @param birthday Новая дата рождения
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Функция получения логина пользователя {@link User#login}
     * @return Логин пользователя
     */
    public String getLogin() {
        return login;
    }

    /**
     * Функция установки нового логина пользователя {@link User#login}
     * @param login Новый логин
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Функция получения md5 пароля пользователя {@link User#password}
     * @return
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }



    public User(String fio, Date birthday, String login, boolean active) {
        this.fio = fio;
        this.birthday = birthday;
        this.login = login;
        this.active = active;
    }


}
