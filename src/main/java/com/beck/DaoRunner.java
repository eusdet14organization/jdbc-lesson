package com.beck;

import com.beck.dao.ClientDao;
import com.beck.dao.Invoice10Dao;
import com.beck.dao.InvoiceDao;

public class DaoRunner {

    public static void main(String[] args) {

        InvoiceDao invoiceDao = InvoiceDao.getInstance();
//        List<Invoice> allInvoices = invoiceDao.findAll();
//
//        for (Invoice invoice: allInvoices){
//            System.out.println(invoice);
//        }
//        Invoice invoice = new Invoice(21,"222-222-22",3, BigDecimal.valueOf(222.22),
//                BigDecimal.valueOf(222.22), LocalDate.of(2024,01,01),
//                LocalDate.of(2024,02,02),LocalDate.of(2024,01,01));
//        invoiceDao.save(invoice);

//        System.out.println("invoiceDao.findById(1) = " + invoiceDao.findById(1));
//        Optional<Invoice> maybeInvoice = invoiceDao.findById(1);
//        invoiceDao.joinMethod(2);


//        InvoiceClientDao instance = InvoiceClientDao.getInstance();
//        System.out.println("instance.findById(2) = " + instance.findById(6));

//          invoiceDao.subqueryMethod(3);
//        ClientDao clientDao = ClientDao.getClientDao();
//        System.out.println("clientDao.findById(1) = " + clientDao.findById(1));
        Invoice10Dao instance = Invoice10Dao.getInstance();
//        System.out.println("instance.findById(1) = " + instance.findById(1));
        instance.findAll().forEach(invoice10 -> System.out.println(invoice10));

    }

}
