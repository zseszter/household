package com.zsubori.household.data;

import java.util.Date;

public class Todo {

    //public static int id = 0;

    private String id;
    private String name;
    private String assignee;
    private int urgency;
    private Date deadline;
    private Date fulfillDate;

    public Todo() {}

    public Todo(String n, String a, int u, Date d, Date f) {
        id = "";
        name = n;
        assignee = a;
        urgency = u;
        deadline = d;
        fulfillDate = f;
    }

    public Todo(String a, String b) {
        id = "";
        name = a;
        assignee = b;
        urgency = 0;
        deadline = null;
        fulfillDate = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String mId) {
        id = mId;
    }

    public String getName() {
        return name;
    }

    public String getAssignee() {
        return assignee;
    }

    public int getUrgency() {
        return urgency;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Date getFulfillDate() {
        return fulfillDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setFulfillDate(Date fulfillDate) {
        this.fulfillDate = fulfillDate;
    }

    public void updateAssignee(String updatedAssignee) {

    }
}
