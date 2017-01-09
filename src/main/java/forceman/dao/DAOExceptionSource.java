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
    EXCEPTION_DAO_USER_FIND_LOGIN("��� �������� ������������� ������ ��������� ������"),
    EXCEPTION_DAO_USER_FIND_LOGIN_ILLEGAL_ARGUMENT("������ ������������ �����"),

    EXCEPTION_DAO_GROUP_DELETE("������ ��� �������� ������ �������������"),
    EXCEPTION_DAO_GROUP_FIND_BY_ID("������ ��� ������ ������ ������������� �� �� ��������������"),
    EXCEPTION_DAO_GROUP_UPDATE("��� ���������� ������ ������������� ��������� ������"),
    EXCEPTION_DAO_GROUP_COUNT("��� ��������� ���������� ����� ������������� ��������� ������"),
    EXCEPTION_DAO_GROUP_CREATE("������ ��� �������� ����� ������"),
    EXCEPTION_DAO_GROUP_LIST("������ ��� ��������� ������ ����� �������������");


    /**
     *
     */
    private String desciption = "";

    DAOExceptionSource(String description){
        this.desciption = description;
    }
}
