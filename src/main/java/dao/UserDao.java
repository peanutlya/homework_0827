package dao;

import entity.User;

public interface UserDao {
    User queryCountByKeywords(User currentUser);

}
