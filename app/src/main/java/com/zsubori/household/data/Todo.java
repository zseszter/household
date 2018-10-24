package com.zsubori.household.data;

import java.util.Date;

public class Todo {
    private String name;
    private String assignee;
    private int urgency;
    private Date deadline;
    private Date fulfillDate;

    Todo() {}

    public Todo(String n, String a, int u, Date d, Date f) {
        name = n;
        assignee = a;
        urgency = u;
        deadline = d;
        fulfillDate = f;
    }

    public Todo(String a, String b) {
        name = a;
        assignee = b;
        urgency = 0;
        deadline = null;
        fulfillDate = null;
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
}
