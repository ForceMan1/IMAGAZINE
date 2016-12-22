package forceman.dao;

/**
 * Created by Igor on 22.12.2016.
 * ������������ ��� �������� ��������� ������ DAO ������
 */
public enum DAOExceptionSource {
    EXCEPTION_DAO_CLOSE_STATEMENT("��� DAO ������, ����������� ��� �������� ������� Statement"),

    EXCEPTION_DAO_USER_CREATE("��� DAO ������, ����������� ��� �������� ������������"),
    EXCEPTION_DAO_USER_DELETE("��� DAO ������, ����������� ��� �������� ������������"),
    EXCEPTION_DAO_USER_FIND_BY_ID("��� DAO ������, ����������� ��� ������ ������������ �� ��� ��������������"),
    EXCEPTION_DAO_USER_UPDATE("��� DAO ������, ����������� ��� ���������� ������ ������������"),
    EXCEPTION_DAO_USER_COUNT("��� DAO ������, ����������� ��� ��������� ���������� �������������"),
    EXCEPTION_DAO_USER_LIST("��� DAO ������, ����������� ��� ��������� ������ �������������");

    /**
     * �������� ���������� DAO ������
     */
    private String desciption = "";

    DAOExceptionSource(String description){
        this.desciption = description;
    }
}
