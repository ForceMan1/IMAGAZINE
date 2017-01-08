package forceman.entity;

/**
 * Created by Igor on 21.12.2016.
 * Права доступа пользователей
 */
public class Permission extends BaseEntity<Integer>{
    /**
     * Наименование права доступа пользователей
     */
    private String name;

    /**
     * Код права доступа пользователей
     */
    private String code;

    /**
     *
     * @param name Наименование права доступа пользователей
     * @param code Код права доступа пользователей
     */
    public Permission(String name, String code){
        this.setName(name);
        this.setCode(code);
    }

    /**
     * Функция получения наименования права доступа
     * @return наименование права доступа
     */
    public String getName() {
        return name;
    }

    /**
     * Функция установки наименования права доступа
     * @param name Наименование права доступа
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Функция получения кода права доступа
     * @return Код права доступа
     */
    public String getCode() {
        return code;
    }

    /**
     * Функция уставки кода права доступа
     * @param code Код права доступа
     */
    public void setCode(String code) {
        this.code = code;
    }
}
