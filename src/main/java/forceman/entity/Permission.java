package forceman.entity;

/**
 * Created by Igor on 21.12.2016.
 */
public class Permission {
    private int id;
    private String name;
    private String code;

    public Permission(String name, String code){
        this.name = name;
        this.code = code;
    }
}
