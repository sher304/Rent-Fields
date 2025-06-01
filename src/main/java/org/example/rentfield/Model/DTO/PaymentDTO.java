package org.example.rentfield.Model.DTO;

import jakarta.persistence.*;
import org.example.rentfield.Model.Enums.PaymentStatus;

import java.util.Date;

public class PaymentDTO {

    private int user_id;
    private int amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private Date date;
    private int reservation_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }
}
