package com.example.bookstore.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.example.bookstore.domain.Order;
import com.example.bookstore.domain.OrderDetail;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

/**
 * PDF view for an {@link Order}.
 * 
 * 
 * 
 */
public class OrderPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        Order order = (Order) model.get("order");

        document.addTitle("Order :" + order.getId());
        document.add(new Paragraph("Order date: " + order.getOrderDate()));
        document.add(new Paragraph("Delivery date: " + order.getDeliveryDate()));

        Table orderDetails = new Table(4);
        orderDetails.addCell("Title");
        orderDetails.addCell("Price");
        orderDetails.addCell("#");
        orderDetails.addCell("Total");

        for (OrderDetail detail : order.getOrderDetails()) {
            orderDetails.addCell(detail.getBook().getTitle());
            orderDetails.addCell(detail.getBook().getPrice().toString());
            orderDetails.addCell(String.valueOf(detail.getQuantity()));
            orderDetails.addCell(detail.getPrice().toString());
        }

        document.add(orderDetails);

    }

}
