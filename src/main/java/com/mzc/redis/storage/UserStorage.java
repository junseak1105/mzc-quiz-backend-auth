package com.mzc.redis.storage;

import java.util.HashSet;
import java.util.Set;

public class UserStorage {
    private static UserStorage instance;
    private Set<String> users;
    
    private UserStorage(){
        users = new HashSet<>();
        users.add("test1");
        users.add("test2");
        users.add("test3");
        users.add("test4");
        users.add("test5");
    }

    public static synchronized UserStorage getInstance(){
        if(instance == null){
            instance=new UserStorage();
        }
        return instance;
    }

    public Set<String> getUsers(){
        return  users;
    }

    public void setUsers(String userName) throws Exception{
        if(users.contains(userName)){
            throw new Exception(userName + "은(는) 이미 존재하는 이름입니다!");
        }
        users.add(userName);
    }
}
