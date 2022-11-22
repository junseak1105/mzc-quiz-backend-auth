package com.mzc.redis.storage;

import com.mzc.redis.model.Conversation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConversationStorage {
    private static ConversationStorage instance;
    private HashMap<String, List<Conversation>> conversationList;

    private ConversationStorage(){
        conversationList = new HashMap<>();
    }

    public static synchronized ConversationStorage getInstance(){
        if(instance == null){
            instance = new ConversationStorage();
        }
        return instance;
    }

    public List<Conversation> getConversation(String userName){
        List<Conversation> temp = new ArrayList<>();
        if(conversationList.containsKey(userName)){
            temp=conversationList.get(userName);
        }
        return temp;
    }
}
