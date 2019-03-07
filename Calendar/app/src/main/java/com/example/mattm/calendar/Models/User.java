package com.example.mattm.calendar.Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.ArrayList;

@DynamoDBTable(tableName = "calendar-mobilehub-934323895-User")
public class User
{
    // Private Properties
    private ArrayList<String> classes;
    
    private String userId;
    
    // Constructors
    public User()
    {
        this("", new ArrayList<>());
    }
    
    public User(String userId)
    {
        this(userId, new ArrayList<>());
    }
    
    public User(String userId, ArrayList<String> classes)
    {
        this.classes = classes;
        this.userId = userId;
    }
    
    // Accessors
    @DynamoDBAttribute(attributeName = "classes")
    public ArrayList<String> getClasses()
    {
        return classes;
    }
    
    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId()
    {
        return userId;
    }

    // Mutators
    public void setClasses(ArrayList<String> classes)
    {
        this.classes = classes;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
}