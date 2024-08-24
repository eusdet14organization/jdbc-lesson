package com.beck.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Invoice10 {
    private Integer invoiceId;
    private String number;
    private Client client;
    private BigDecimal invoiceTotal;
    private BigDecimal paymentTotal;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private LocalDate paymentDate;




}
