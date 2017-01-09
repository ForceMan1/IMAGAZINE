package forceman.dao;

/**
 * Created by Igor on 22.12.2016.
 * ???????????? ??? ???????? ????????? ?????? DAO ??????
 */
public enum DAOExceptionSource {
    EXCEPTION_DAO_CLOSE_STATEMENT(""),

    EXCEPTION_DAO_USER_CREATE(""),
    EXCEPTION_DAO_USER_DELETE(""),
    EXCEPTION_DAO_USER_FIND_BY_ID(""),
    EXCEPTION_DAO_USER_UPDATE(""),
    EXCEPTION_DAO_USER_COUNT(""),
    EXCEPTION_DAO_USER_LIST(""),
    EXCEPTION_DAO_USER_CREATE_PASSWORD(""),
    EXCEPTION_DAO_USER_AUTHENTICATE(""),
    EXCEPTION_DAO_USER_FIND_LOGIN("При проверке существования логина произошла ошибка"),
    EXCEPTION_DAO_USER_FIND_LOGIN_ILLEGAL_ARGUMENT("Введен некорректный логин"),

    EXCEPTION_DAO_GROUP_DELETE("Ошибка при удалении группы пользователей"),
    EXCEPTION_DAO_GROUP_FIND_BY_ID("Ошибка при поиске группы пользователей по ее идентификатору"),
    EXCEPTION_DAO_GROUP_UPDATE("При обновлении группу пользователей произошла ошибка"),
    EXCEPTION_DAO_GROUP_COUNT("При получении количества групп пользователей произошла ошибка"),
    EXCEPTION_DAO_GROUP_CREATE("Ошибка при создании новой группы"),
    EXCEPTION_DAO_GROUP_LIST("Ошибка при получении списка групп пользователей");


    /**
     *
     */
    private String desciption = "";

    DAOExceptionSource(String description){
        this.desciption = description;
    }
}
