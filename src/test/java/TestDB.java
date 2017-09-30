import com.db.DB;
import org.junit.Test;

import java.sql.PreparedStatement;

import static junit.framework.TestCase.assertEquals;

public class TestDB {

    @Test
    public void testDB() throws Exception {
        // !_ note _! this is just init
// it will not create a connection
        DB mysqlConnect = new DB();

        String sql = "SELECT name FROM `new_table`";
        PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);

        assertEquals("Test Passed", 1, statement.executeQuery().findColumn("name"));

        mysqlConnect.disconnect();

    }
}
