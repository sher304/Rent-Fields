package org.example.rentfield.Repository.Payment;

import org.example.rentfield.Model.Payment;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class PaymentDelegate {
    Set<Payment>  payments;

    public PaymentDelegate(Set<Payment> payments) {
        this.payments = payments;
    }
}
