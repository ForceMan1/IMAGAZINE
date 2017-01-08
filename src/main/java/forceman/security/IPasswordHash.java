package forceman.security;

import java.security.NoSuchAlgorithmException;

/**
 * Created by 1 on 26.12.2016.
 *  ��������� ��������� �������� ���� ������
 */
public interface IPasswordHash {
    /**
     * �������� ���� ��� ������
     * @return ���� ��� ������
     */
    public String getNextSalt();

    /**
     *  �������� ���� �� ��������� ������� � ����
     * @param password ������
     * @param salt ��������������� ����
     * @return ��� ������ + ����
     */
    String createHash(String password, String salt) throws NoSuchAlgorithmException;
}
