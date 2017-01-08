package forceman.entity;

/**
 * Created by Igor on 20.12.2016.
 * ������ �������������
 */
public class Group extends BaseEntity<Integer>{
    /**
     * ������������ ������ �������������
     */
    private String name;

    /**
     *
     * @param name ������������ ������ �������������
     */
    public Group(String name){
        this.setName(name);
    }

    /**
     * �������� ������������ ������
     * @return ������������ ������
     */
    public String getName() {
        return name;
    }

    /**
     * ���������� ������������ ������
     * @param name ������������ ������
     */
    public void setName(String name) {
        this.name = name;
    }
}
