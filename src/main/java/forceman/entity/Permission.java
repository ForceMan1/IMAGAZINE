package forceman.entity;

/**
 * Created by Igor on 21.12.2016.
 * ����� ������� �������������
 */
public class Permission extends BaseEntity<Integer>{
    /**
     * ������������ ����� ������� �������������
     */
    private String name;

    /**
     * ��� ����� ������� �������������
     */
    private String code;

    /**
     *
     * @param name ������������ ����� ������� �������������
     * @param code ��� ����� ������� �������������
     */
    public Permission(String name, String code){
        this.setName(name);
        this.setCode(code);
    }

    /**
     * ������� ��������� ������������ ����� �������
     * @return ������������ ����� �������
     */
    public String getName() {
        return name;
    }

    /**
     * ������� ��������� ������������ ����� �������
     * @param name ������������ ����� �������
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * ������� ��������� ���� ����� �������
     * @return ��� ����� �������
     */
    public String getCode() {
        return code;
    }

    /**
     * ������� ������� ���� ����� �������
     * @param code ��� ����� �������
     */
    public void setCode(String code) {
        this.code = code;
    }
}
