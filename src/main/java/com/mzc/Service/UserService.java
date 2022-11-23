package com.mzc.Service;

import com.mzc.domain.User;

import java.util.List;

public interface UserService {
    public User getUser(long id);
    public List<User> getAll();
    public void delete(long id);
    public User update(User user);
    public User create(User user);
}
