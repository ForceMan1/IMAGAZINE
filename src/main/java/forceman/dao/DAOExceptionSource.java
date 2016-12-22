package forceman.dao;

/**
 * Created by Igor on 22.12.2016.
 * Перечисление для хранения источника ошибки DAO класса
 */
public enum DAOExceptionSource {
    EXCEPTION_DAO_CLOSE_STATEMENT("Код DAO ошибки, возникающей при закрытии объекта Statement"),

    EXCEPTION_DAO_USER_CREATE("Код DAO ошибки, возникающей при создании пользователя"),
    EXCEPTION_DAO_USER_DELETE("Код DAO ошибки, возникающей при удалении пользователя"),
    EXCEPTION_DAO_USER_FIND_BY_ID("Код DAO ошибки, возникающей при поиске пользователя по его идентификатору"),
    EXCEPTION_DAO_USER_UPDATE("Код DAO ошибки, возникающей при обновлении данных пользователя"),
    EXCEPTION_DAO_USER_COUNT("Код DAO ошибки, возникающей при получении количества пользователей"),
    EXCEPTION_DAO_USER_LIST("Код DAO ошибки, возникающей при получении списка пользователей");

    /**
     * Описание исчточника DAO ошибки
     */
    private String desciption = "";

    DAOExceptionSource(String description){
        this.desciption = description;
    }
}
