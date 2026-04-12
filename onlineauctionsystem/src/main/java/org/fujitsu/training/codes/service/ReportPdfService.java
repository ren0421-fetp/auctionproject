package org.fujitsu.training.codes.service;

import java.io.OutputStream;
import java.util.List;

import org.fujitsu.training.codes.model.data.Bid;
import org.fujitsu.training.codes.model.data.ConfirmBidReportRow;
import org.fujitsu.training.codes.model.data.ProductReportRow;
import org.fujitsu.training.codes.model.data.UserReportRow;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ReportPdfService {

    public void writeUserReport(List<UserReportRow> rows, OutputStream out) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();
        document.add(new Paragraph("User Registration Report"));
        document.add(buildUserTable(rows));
        document.close();
    }

    public void writeProductReport(List<ProductReportRow> rows, OutputStream out) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();
        document.add(new Paragraph("Auction Item Report"));
        document.add(buildProductTable(rows));
        document.close();
    }

    public void writeBidReport(List<Bid> rows, OutputStream out) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();
        document.add(new Paragraph("Bid Report"));
        document.add(buildBidTable(rows));
        document.close();
    }

    public void writeConfirmBidReport(List<ConfirmBidReportRow> rows, OutputStream out) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();
        document.add(new Paragraph("Confirmed Bid Report"));
        document.add(buildConfirmBidTable(rows));
        document.close();
    }

    private PdfPTable buildUserTable(List<UserReportRow> rows) {
        PdfPTable table = new PdfPTable(6);
        addHeader(table, "Username", "First Name", "Last Name", "Email", "User Type", "Locked");
        for (UserReportRow row : rows) {
            table.addCell(value(row.getUsername()));
            table.addCell(value(row.getFirstName()));
            table.addCell(value(row.getLastName()));
            table.addCell(value(row.getEmail()));
            table.addCell(value(row.getUserType()));
            table.addCell(String.valueOf(row.getIsLocked()));
        }
        return table;
    }

    private PdfPTable buildProductTable(List<ProductReportRow> rows) {
        PdfPTable table = new PdfPTable(7);
        addHeader(table, "Product Id", "Product Name", "Seller", "Category", "Min Bid", "Status", "End Date");
        for (ProductReportRow row : rows) {
            table.addCell(String.valueOf(row.getProductId()));
            table.addCell(value(row.getProductName()));
            table.addCell(value(row.getSellerUsername()));
            table.addCell(value(row.getCategoryName()));
            table.addCell(String.valueOf(row.getMinBidPrice()));
            table.addCell(value(row.getStatus()));
            table.addCell(String.valueOf(row.getEndDate()));
        }
        return table;
    }

    private PdfPTable buildBidTable(List<Bid> rows) {
        PdfPTable table = new PdfPTable(6);
        addHeader(table, "Bid Id", "Product", "Seller", "Bidder", "Bid Price", "Bid Date");
        for (Bid row : rows) {
            table.addCell(String.valueOf(row.getBidId()));
            table.addCell(value(row.getProductName()));
            table.addCell(value(row.getSellerUsername()));
            table.addCell(value(row.getBidderUsername()));
            table.addCell(String.valueOf(row.getBidPrice()));
            table.addCell(String.valueOf(row.getBidDate()));
        }
        return table;
    }

    private PdfPTable buildConfirmBidTable(List<ConfirmBidReportRow> rows) {
        PdfPTable table = new PdfPTable(6);
        addHeader(table, "Confirm Id", "Bid Id", "Product", "Winner", "Confirmed Price", "Confirmed At");
        for (ConfirmBidReportRow row : rows) {
            table.addCell(String.valueOf(row.getConfirmBidId()));
            table.addCell(String.valueOf(row.getBidId()));
            table.addCell(value(row.getProductName()));
            table.addCell(value(row.getWinnerUsername()));
            table.addCell(String.valueOf(row.getConfirmedPrice()));
            table.addCell(String.valueOf(row.getConfirmedAt()));
        }
        return table;
    }

    private void addHeader(PdfPTable table, String... headers) {
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }

    private String value(Object value) {
        return value == null ? "" : String.valueOf(value);
    }
}
