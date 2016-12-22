package forceman.dao;

/**
 * Created by 1 on 22.12.2016.
 * ����� ������, ����������� ��� ���������� DAO �������
 */
public class DAOException extends Exception {

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }


}
