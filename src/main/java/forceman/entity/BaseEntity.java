package forceman.entity;

/**
 * Created by 1 on 07.01.2017.
 * ������� ����� ��� ���� ���������
 */
public class BaseEntity<V extends Number> {
    /**
     * ���������� ������������� ���������� ������
     */
    protected V id;

    /**
     * ������� ��������� �������������� ���������� ������
     * @param id ������������� ���������� ������
     */
    public void setId(V id){
        this.id = id;
    }

    /**
     * ������� ��������� �������������� ���������� ������
     * @return ������������� ���������� ������
     */
    public V getId() {
        return id;
    }
}
