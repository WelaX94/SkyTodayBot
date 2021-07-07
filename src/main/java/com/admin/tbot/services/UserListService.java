package com.admin.tbot.services;

import com.admin.tbot.models.User;
import com.admin.tbot.repositories.UserListRepository;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Getter
@Setter
@Transactional
public class UserListService implements UserListRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> getDailyMailingUsers() {
        final Session session = sessionFactory.getCurrentSession();
        final Query<User> query = session.createQuery("from User where dailyMailing = 1");
        return query.getResultList();
    }

    @Override
    public void updateUserList(List<User> userList) {
        final Session session = sessionFactory.getCurrentSession();
        for(User user: userList) {
            session.saveOrUpdate(user);
        }
    }

    @Override
    public void updateUserList(User user) {
        final Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
    }

    @Override
    public User getUser(long chatId) {
        final Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, chatId);
        if (user == null) user = new User(chatId);
        return user;
    }

}