package dao;

import forceman.dao.DAOException;
import forceman.dao.IPermissionDAO;
import forceman.dao.jdbc.impl.MysqlJdbcPermissionDAO;
import forceman.dao.jdbc.impl.MysqlJdbcUserDAO;
import forceman.entity.Permission;
import forceman.entity.User;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Igor on 11.01.2017.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JdbcPermissionDAOJUnitTest extends Assert {
    private Connection conn = null;
    private IPermissionDAO<Integer> permDAO = null;
    private static Integer id = null;

    @Before
    public void init() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/magazine";
        String username = "magazine";
        String password = "magazine";

        //try {
        conn = DriverManager.getConnection(url, username, password);
        permDAO = new MysqlJdbcPermissionDAO(conn);
    }

    // Удаление всех созданных прав доступа
    @Test
    public void _0000_delete_all_permissions() throws DAOException {
        int count = permDAO.getCount();
        List<Permission> permissions = permDAO.getList(count, 0);

        Iterator<Permission> iterPermission = permissions.iterator();
        while (iterPermission.hasNext()) {
            permDAO.deleteById(iterPermission.next().getId());
        }

        System.out.println(permDAO.getCount());
        assertTrue(permDAO.getCount() == 0);

    }

    /************ Создание прав доступа *****/
    @Test(expected = DAOException.class)
    public void _0001_create_null_permission() throws DAOException {
        permDAO.create(null);
    }

    @Test(expected = DAOException.class)
    public void _0002_create_permission_null_fields() throws DAOException {
        Permission permission  = new Permission(null, null);
        permDAO.create( permission );
        //conn.commit();
    }

    @Test()
    public void _0003_create_permission() throws DAOException, SQLException {
        Permission permission = new Permission("first permission", "FIRST_PERM");
        permission = permDAO.create(permission);
        assertTrue(permission != null);
        assertTrue( permission.getId() != null );
        assertTrue( permission.getName().equals("first permission") );
        assertTrue(permission.getCode().equals("FIRST_PERM") );

        permission = new Permission("second permission", "SECOND_PERM");
        permission = permDAO.create(permission);
        assertTrue(permission != null);
        assertTrue( permission.getId() != null );
        assertTrue( permission.getName().equals("second permission") );
        assertTrue(permission.getCode().equals("SECOND_PERM") );

        permission = new Permission("third permission", "THIRD_PERM");
        permission = permDAO.create(permission);
        assertTrue(permission != null);
        assertTrue( permission.getId() != null );
        assertTrue( permission.getName().equals("third permission") );
        assertTrue(permission.getCode().equals("THIRD_PERM") );

        permission = new Permission("fourth permission", "FOURTH_PERM");
        permission = permDAO.create(permission);
        assertTrue(permission != null);
        assertTrue( permission.getId() != null );
        assertTrue( permission.getName().equals("fourth permission") );
        assertTrue(permission.getCode().equals("FOURTH_PERM") );

        id = permission.getId();
    }

    /***** Изменение прав доступа *********/
    @Test(expected = DAOException.class)
    public void _1000_update_null_permission() throws DAOException{
        permDAO.update(null);
    }

    @Test(expected = DAOException.class)
    public void _1001_update_permission_null_fields() throws DAOException {
        Permission permission = new Permission(null, null);
        permDAO.update( permission );
    }

    @Test
    public void _1002_update_permission() throws DAOException {
        Permission permission = new Permission("STOP RULE", "STOP_RULE");
        permission.setId(id);
        int res = permDAO.update(permission);
        assertTrue( res == 1 );
    }

    // поиск права доступа по ИД
    @Test
    public void _2000_find_permission_by_id() throws DAOException {
        Permission permission = permDAO.findById(id);
        Assert.assertTrue(permission != null);
        Assert.assertTrue(permission.getName().equals("STOP RULE"));
        Assert.assertTrue(permission.getCode().equals("STOP_RULE"));
    }

    // получение количества прав доступа в БД
    @Test
    public void _3000_count_permissions() throws DAOException {
        Assert.assertTrue(permDAO.getCount() == 4);
    }

    // Удаление всех созданных прав доступа
    @Test
    public void _4000_delete_all_permissions() throws DAOException {
        int count = permDAO.getCount();
        List<Permission> permissions = permDAO.getList(count, 0);

        Iterator<Permission> iterPermission = permissions.iterator();
        while( iterPermission.hasNext() ) {
            permDAO.deleteById(iterPermission.next().getId());
        }

        System.out.println( permDAO.getCount() );
        assertTrue( permDAO.getCount() == 0 );

    }


    @After
    public void destroy() throws SQLException {
        conn.close();
    }
}
