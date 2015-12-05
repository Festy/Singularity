package com.example.utsavpatel.singularity;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by danza on 11/29/15.
 */
public class User {
    private String username;
    private boolean online;
    private ArrayList<User> friends;
    ArrayList<Conversation> covList;

    public String getUsername(){
        return username;
    }

    public void setUserName(String username){
        this.username = username;
    }

    public boolean isOnline(){
        return online;
    }

    public void setOnline(boolean online){
        this.online = online;
    }

    public ArrayList<User> getFriends(){
        return tempFriendList();
    }

    private ArrayList<User> tempFriendList() {
        if(friends==null) {
            friends = new ArrayList<User>();
            addTempFriend("ankit");
            addTempFriend("akash");
        }
        return friends;
    }

    private void addTempFriend(String name) {
        User testUser = new User();
        testUser.setUserName(name);
        testUser.setOnline(true);
        friends.add(testUser);
    }

    public void saveConversation(Conversation c){
        covList.add(c);
    }

    public ArrayList<Conversation> getConversations(String buddy){
        ArrayList<Conversation> convs = new ArrayList<Conversation>();
        for(Conversation c: covList){
            if(c.getSender().equals(buddy) || c.getReceiver().equals(buddy))
                convs.add(c);
        }
        return convs;
    }


    public void saveTempConversations(){
        covList = new ArrayList<Conversation>();
        saveTempConvWithUser("ankit");
        saveTempConvWithUser("akash");
    }

    private void saveTempConvWithUser(String buddy) {
        User testUser = new User();
        testUser.setUserName(buddy);
        testUser.setOnline(true);

        Conversation cov1 = new Conversation("hello utsav",new Date(),buddy,"utsav");
        Conversation cov2 = new Conversation("hello "+buddy,new Date(),"utsav",buddy);
        covList.add(cov1);
        covList.add(cov2);
    }
}