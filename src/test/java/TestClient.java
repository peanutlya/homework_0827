import dao.UserDao;
import entity.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import utils.MyBatisUtil;

import java.io.IOException;

public class TestClient {
    @Test
    public void testConnect() throws IOException {
        User currentUser=new User("user","user");
        User user=null;
        try(SqlSession session= MyBatisUtil.getSqlSession()){
            UserDao userDao = session.getMapper(UserDao.class);
            user = userDao.queryCountByKeywords(currentUser);
            System.out.println(user.toString());
        }
    }
}
