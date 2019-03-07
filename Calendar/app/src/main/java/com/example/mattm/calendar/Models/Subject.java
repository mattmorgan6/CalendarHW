package com.example.mattm.calendar.Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.ArrayList;
import java.util.List;

@DynamoDBTable(tableName = "calendar-mobilehub-934323895-Classes")
public class Subject
{
    // Private Properties
    private List<String> enrolledStudents;
    
    private String period;
    private String subject;
    private String teacherName;
    
    // Constructors
    public Subject()
    {
        this("", "", "", new ArrayList<>());
    }
    
    public Subject(String subject, String teacherName, String period)
    {
        this(subject, teacherName, period, new ArrayList<>());
    }
    
    public Subject(String subject, String teacherName, String period, ArrayList<String> enrolledStudents)
    {
        this.enrolledStudents = enrolledStudents;
        this.period = period;
        this.subject = subject;
        this.teacherName = teacherName;
    }
    
    // Static Methods
    
    /**
     * Converts am ArrayList of String that represents the Subject key in the Database into an
     * ArrayList of String that only has the subject name for display purposes.
     *
     * @param list The ArrayList of String that contains Subject IDs.
     *
     * @return An ArrayList of String that only contains the Subject name.
     */
    public static ArrayList<String> ConvertListToReadable(List<Subject> list)
    {
        ArrayList<String> result = new ArrayList<>();
        
        for (Subject item : list)
            result.add(String.format("%s (%s)", item.getSubject(), item.getTeacherName()));
        
        return result;
    }
    
    /**
     * Converts a List of String that represents the Subject key in the Database into an ArrayList
     * of String that only has the subject name for display purposes.
     *
     * @param list The List of String that contains Subject IDs.
     *
     * @return A List of String that only contains the Subject name.
     */
    public static ArrayList<String> ConvertArrayListToReadable(List<String> list)
    {
        ArrayList<String> result = new ArrayList<>();
        
        for (String dbKey : list)
            result.add(ConvertToReadable(dbKey));
        
        return result;
    }
    
    /**
     * Converts a string that represents the Subject key in the Database into a String that only has the subject
     * for display purposes.
     *
     * @param dbKey The String that represents the Database key for any Subject.
     *
     * @return A String that represents the name of the Subject for display purposes.
     */
    public static String ConvertToReadable(String dbKey)
    {
        return dbKey.split("_")[2];
    }
    
    // Accessors
    @DynamoDBAttribute(attributeName = "enrolledStudents")
    public List<String> getEnrolledStudents()
    {
        return enrolledStudents;
    }
    
    @DynamoDBRangeKey(attributeName = "period")
    @DynamoDBAttribute(attributeName = "period")
    public String getPeriod()
    {
        return period;
    }
    
    @DynamoDBAttribute(attributeName = "subject")
    public String getSubject()
    {
        return subject;
    }
    
    @DynamoDBHashKey(attributeName = "teacher")
    @DynamoDBAttribute(attributeName = "teacher")
    public String getTeacherName()
    {
        return teacherName;
    }
    
    // Mutators
    public void setEnrolledStudents(List<String> enrolledStudents)
    {
        this.enrolledStudents = enrolledStudents;
    }
    
    public void setPeriod(String period)
    {
        this.period = period;
    }
    
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
    
    public void setTeacherName(String teacherName)
    {
        this.teacherName = teacherName;
    }
    
    // Overridden Methods
    @Override
    public String toString()
    {
        return String.format("%s_%s_%s", getTeacherName(), getPeriod(), getSubject());
    }
}