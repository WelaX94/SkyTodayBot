package com.admin.tbot.repositories;

import com.admin.tbot.models.User;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserListRepository {

    public List<User> getUserList();

    public void updateUserList(List<User> userList);

    public void updateUserList(User user);

    public User getUser(long chatId);

}