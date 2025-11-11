package com.models;

import java.time.LocalDate;

/**
 * Represents a single issuance transaction.
 * Tracks due dates, returns, and accrued fines.
 */
public class IssueRecord {
    private String id;
    private String bookId;
    private String userId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate; // nullable when not yet returned
    private int fineAmount; // INR

    public IssueRecord() {}

    public IssueRecord(String id, String bookId, String userId,
                       LocalDate issueDate, LocalDate dueDate,
                       LocalDate returnDate, int fineAmount) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fineAmount = fineAmount;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public int getFineAmount() { return fineAmount; }
    public void setFineAmount(int fineAmount) { this.fineAmount = fineAmount; }

    @Override
    public String toString() {
        return "IssueRecord{" +
                "id='" + id + '\'' +
                ", bookId='" + bookId + '\'' +
                ", userId='" + userId + '\'' +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", fineAmount=" + fineAmount +
                '}';
    }
}


