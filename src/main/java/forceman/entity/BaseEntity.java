package forceman.entity;

/**
 * Created by 1 on 07.01.2017.
 * Базовый класс для всех сущностей
 */
public class BaseEntity<V extends Number> {
    /**
     * Уникальный идентификатор экземпляра класса
     */
    protected V id;

    /**
     * Функция установки идентификатора экземпляра класса
     * @param id Идентификатор экземпляра класса
     */
    public void setId(V id){
        this.id = id;
    }

    /**
     * Функция получения идентификатора экземпляра класса
     * @return идентификатор экземпляра класса
     */
    public V getId() {
        return id;
    }
}
