package com.mtp.simplecoding.FirebaseDataBase;

public class paymentPojo {
    String Amount="";
    String fromDate="";
    String toDate="";
    String reminder="";
    String Status="";
    String nameOfOwner="";
    String description="";

    public paymentPojo() {
    }

    public paymentPojo(String amount, String fromDate, String toDate, String reminder, String status, String nameOfOwner,String description
    ) {
        Amount = amount;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reminder = reminder;
        this.Status = status;
        this.nameOfOwner = nameOfOwner;
        this.description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }


    public String getNameOfOwner() {
        return nameOfOwner;
    }

    public void setNameOfOwner(String nameOfOwner) {
        this.nameOfOwner = nameOfOwner;
    }
}
