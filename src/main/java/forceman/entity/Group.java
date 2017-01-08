package forceman.entity;

/**
 * Created by Igor on 20.12.2016.
 * Группа пользователей
 */
public class Group extends BaseEntity<Integer>{
    /**
     * Наименование группы пользователей
     */
    private String name;

    /**
     *
     * @param name Наименование группы пользователей
     */
    public Group(String name){
        this.setName(name);
    }

    /**
     * Получить наименование группы
     * @return наименование группы
     */
    public String getName() {
        return name;
    }

    /**
     * Установить наименование группы
     * @param name Наименование группы
     */
    public void setName(String name) {
        this.name = name;
    }
}
