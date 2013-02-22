package com.example.bookstore.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.web.servlet.view.document.AbstractJExcelView;

import com.example.bookstore.domain.Order;
import com.example.bookstore.domain.OrderDetail;

/**
 * Excel view for an {@link Order}.
 * 
 * 
 * 
 */
public class OrderExcelView extends AbstractJExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, WritableWorkbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Order order = (Order) model.get("order");

        WritableSheet sheet = workbook.createSheet("Order", 0);
        sheet.addCell(new Label(1, 1, "Order :" + order.getId()));
        sheet.addCell(new Label(1, 2, "Order Date"));
        sheet.addCell(new DateTime(2, 2, order.getOrderDate()));
        sheet.addCell(new Label(1, 2, "Delivery Date"));
        sheet.addCell(new DateTime(2, 2, order.getDeliveryDate()));

        sheet.addCell(new Label(1, 4, "Title"));
        sheet.addCell(new Label(2, 4, "Price"));
        sheet.addCell(new Label(3, 4, "#"));
        sheet.addCell(new Label(4, 4, "Total"));

        int row = 4;
        for (OrderDetail detail : order.getOrderDetails()) {
            row++;
            sheet.addCell(new Label(1, row, detail.getBook().getTitle()));
            sheet.addCell(new jxl.write.Number(2, row, detail.getBook().getPrice().doubleValue()));
            sheet.addCell(new jxl.write.Number(3, row, detail.getQuantity()));
            sheet.addCell(new jxl.write.Number(2, row, detail.getPrice().doubleValue()));
        }
    }

}
