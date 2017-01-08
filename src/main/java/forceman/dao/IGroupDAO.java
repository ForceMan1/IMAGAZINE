package forceman.dao;

import forceman.entity.Group;

/**
 * Created by Igor on 22.12.2016.
 */
public interface IGroupDAO {
    public void createGroup(Group group);

    public void deleteGroup(Group group);

    public Group findGroupById(int id);


}
