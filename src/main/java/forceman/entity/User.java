package forceman.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by Igor on 20.12.2016.
 *  ����� ������������
 *  @version 2016212101
 *
 */
public class User {
    /**
     * ���������� ������������� ������������
     */
    private int id;

    /**
     * ��� ������������
     */
    private String fio;

    /**
     * ���� �������� ������������
     */
    private Date birthday;

    /**
     * ����� ������������
     */
    private String login;

    /**
     *  ������ ������������
     */
    private String password;

    /**
     * ���� ���������� ������������
     */
    private boolean active;

    /**
     * ������ ������, � ������� ������ ������������
     */
    private List<Group> groups;

    /**
     * ������� ��������� �������������� ������������ {@link User#id}
     * @return ������������� ������������
     */
    public int getId() {
        return id;
    }

    /**
     * ������� ��������� �������������� ������������
     * @param id ����� ������������� ������������ {@link User#id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * ������� ��������� ��� ������������ {@link User#fio}
     * @return ��� ������������
     */
    public String getFio() {
        return fio;
    }

    /**
     * ������� ��������� ������ ��� ������������ {@link User#fio}
     * @param fio ����� ��� ������������
     */
    public void setFio(String fio) {
        this.fio = fio;
    }

    /**
     * ������� ��������� ���� �������� ������������ {@link User#birthday}
     * @return ���� �������� ������������
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * ������� ��������� ����� ���� �������� ������������ {@link User#birthday}
     * @param birthday ����� ���� ��������
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * ������� ��������� ������ ������������ {@link User#login}
     * @return ����� ������������
     */
    public String getLogin() {
        return login;
    }

    /**
     * ������� ��������� ������ ������ ������������ {@link User#login}
     * @param login ����� �����
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * ������� ��������� md5 ������ ������������ {@link User#password}
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
