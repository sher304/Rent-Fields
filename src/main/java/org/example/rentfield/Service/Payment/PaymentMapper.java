package org.example.rentfield.Service.Payment;

import org.example.rentfield.Model.DTO.PaymentDTO;
import org.example.rentfield.Model.Payment;
import org.example.rentfield.Model.Reservation;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {

    public PaymentDTO map(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(paymentDTO.getAmount());
        paymentDTO.setDate(paymentDTO.getDate());
        paymentDTO.setUser_id(paymentDTO.getUser_id());
        paymentDTO.setStatus(paymentDTO.getStatus());
        paymentDTO.setReservation_id(paymentDTO.getReservation_id());
        return paymentDTO;
    }

    public Payment map(PaymentDTO paymentDTO,
                       Reservation reservation) {
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setDate(paymentDTO.getDate());
        payment.setAmount(paymentDTO.getAmount());
        payment.setStatus(paymentDTO.getStatus());
        payment.setReservation(reservation);
        return payment;
    }
}
