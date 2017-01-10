package dao;

import forceman.dao.DAOException;
import forceman.dao.IGroupDAO;
import forceman.dao.jdbc.impl.MysqlJdbcGroupDAO;
import forceman.entity.Group;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Igor on 10.01.2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JdbcGroupDAOJUnitTest extends Assert{
    private Connection conn = null;
    private IGroupDAO<Integer> groupDAO = null;
    private static int id;

    @Before
    public void initTest() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/magazine";
        String username = "magazine";
        String password = "magazine";

        //try {
        conn = DriverManager.getConnection(url, username, password);
        groupDAO = new MysqlJdbcGroupDAO(conn);
    }

    // Удаление всех созданных групп
    @Test
    public void _0000_delete_all_groups() throws DAOException {
        int count = groupDAO.getCount();
        List<Group> groups = groupDAO.getList(0, count);

        Iterator<Group> iterGroup = groups.iterator();
        while (iterGroup.hasNext()) {
            groupDAO.deleteById(iterGroup.next().getId());
        }

        System.out.println(groupDAO.getCount());
        assertTrue(groupDAO.getCount() == 0);
    }

    /************ Создание группы пользователей *****/
    @Test(expected = DAOException.class)
    public void _0001_create_null_group() throws DAOException {
        groupDAO.create(null);
    }

    @Test(expected = DAOException.class)
    public void _0002_create_group_null_fields() throws DAOException {
        Group group = new Group(null);
        groupDAO.create( group );
        //conn.commit();
    }

    @Test()
    public void _0003_create_group() throws DAOException, SQLException {
        Date date = null;
        Group group = new Group("first group");
        group = groupDAO.create(group);
        assertTrue(group != null);
        assertTrue( group.getId() != null );

        group = new Group("second group");
        group = groupDAO.create(group);
        assertTrue(group != null);
        assertTrue( group.getId() != null );

        group = new Group("third group");
        group = groupDAO.create(group);
        assertTrue(group != null);
        assertTrue( group.getId() != null );

        group = new Group("fourth group");
        group = groupDAO.create(group);
        assertTrue(group != null);
        assertTrue( group.getId() != null );

        id = group.getId();
    }

    /***** Изменение группы пользователей *********/
    @Test(expected = DAOException.class)
    public void _1000_update_null_group() throws DAOException{
        groupDAO.update(null);
    }

    @Test(expected = DAOException.class)
    public void _1001_update_group_null_fields() throws DAOException {
        Group group = new Group(null);
        groupDAO.update(group);
    }

    @Test(expected=DAOException.class)
    public void _1002_update_group_with_null_fields() throws DAOException {
        Group group = new Group(null);
        groupDAO.update(group);

    }

    @Test
    public void _1003_update_group() throws DAOException {
        Group group = new Group("STOP group");
        group.setId(id);
        int res = groupDAO.update(group);
        assertTrue( res == 1 );
    }

    // поиск группы пользователей по ИД
    @Test
    public void _2000_find_group_by_id() throws DAOException {
        Group group = groupDAO.findById(id);
        Assert.assertTrue(group != null);
        Assert.assertTrue(group.getName().equals("STOP group"));
    }

    // получение количества групп в БД
    @Test
    public void _3000_count_groups() throws DAOException {
        Assert.assertTrue(groupDAO.getCount() == 4);
    }

    // Удаление всех созданных групп
    @Test
    public void _4000_delete_all_groups() throws DAOException {
        int count = groupDAO.getCount();
        List<Group> groups = groupDAO.getList(0, count);

        Iterator<Group> iterGroup = groups.iterator();
        while( iterGroup.hasNext() ) {
            groupDAO.deleteById(iterGroup.next().getId());
        }

        System.out.println( groupDAO.getCount() );
        assertTrue( groupDAO.getCount() == 0 );

    }


    @After
    public void destroyTest(){
        try {
            if (conn != null)
                conn.close();
        }catch(SQLException sqlExc){
        }
    }

}
