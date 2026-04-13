/*package org.fujitsu.training.codes.helper;

import java.io.ByteArrayOutputStream;

import org.fujitsu.training.codes.dao.impl.AdminReportDaoImpl;
import org.fujitsu.training.codes.service.ReportPdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class AdminReportHelper {

    private final AdminReportDaoImpl adminReportDaoImpl;
    private final ReportPdfService reportPdfService;

    public AdminReportHelper(AdminReportDaoImpl adminReportDaoImpl,
            ReportPdfService reportPdfService) {
        this.adminReportDaoImpl = adminReportDaoImpl;
        this.reportPdfService = reportPdfService;
    }

    public void prepareLoadReportPage(Model model) {
        // Reserved for future report-page setup if needed.
    }

    public ResponseEntity<byte[]> buildPdfResponse(String type) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String filename = writeReport(type, out);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_PDF)
                .body(out.toByteArray());
    }

    private String writeReport(String type, ByteArrayOutputStream out) throws Exception {
        switch (type) {
            case "users":
                reportPdfService.writeUserReport(adminReportDaoImpl.getUserRegistrationReport(), out);
                return "user_registration_report.pdf";
            case "products":
                reportPdfService.writeProductReport(adminReportDaoImpl.getAuctionItemReport(), out);
                return "auction_item_report.pdf";
            case "bids":
                reportPdfService.writeBidReport(adminReportDaoImpl.getBidReport(), out);
                return "bid_report.pdf";
            case "confirmed-bids":
                reportPdfService.writeConfirmBidReport(adminReportDaoImpl.getConfirmBidReport(), out);
                return "confirmed_bid_report.pdf";
            default:
                throw new IllegalArgumentException("Invalid report type.");
        }
    }
}*/


package org.fujitsu.training.codes.helper;

import java.io.ByteArrayOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.impl.AdminReportDaoImpl;
import org.fujitsu.training.codes.service.ReportPdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class AdminReportHelper {
    private static final Logger logger = LogManager.getLogger("admin-flow");

    private final AdminReportDaoImpl adminReportDaoImpl;
    private final ReportPdfService reportPdfService;

    public AdminReportHelper(AdminReportDaoImpl adminReportDaoImpl,
            ReportPdfService reportPdfService) {
        this.adminReportDaoImpl = adminReportDaoImpl;
        this.reportPdfService = reportPdfService;
    }

    public void prepareLoadReportPage(Model model) {
        logger.info("Loading admin report page.");
        logger.info("Admin report page loaded.");
    }

    public ResponseEntity<byte[]> buildPdfResponse(String type) throws Exception {
        logger.info("Generating admin report PDF for type={}.", type);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            String filename = writeReport(type, out);

            logger.info("Generated admin report PDF for type={} as {}.", type, filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(out.toByteArray());
        } catch (Exception ex) {
            logger.error("Failed to generate admin report PDF for type={}: {}", type, ex.getMessage(), ex);
            throw ex;
        }
    }

    private String writeReport(String type, ByteArrayOutputStream out) throws Exception {
    	logger.info("writing report");
    	switch (type) {
            case "users":
                reportPdfService.writeUserReport(adminReportDaoImpl.getUserRegistrationReport(), out);
                logger.info("chose user report");
                return "user_registration_report.pdf";
            case "products":
                reportPdfService.writeProductReport(adminReportDaoImpl.getAuctionItemReport(), out);
                logger.info("chose products report");
                return "auction_item_report.pdf";
            case "bids":
                reportPdfService.writeBidReport(adminReportDaoImpl.getBidReport(), out);
                logger.info("chose bids report");
                return "bid_report.pdf";
            case "confirmed-bids":
                reportPdfService.writeConfirmBidReport(adminReportDaoImpl.getConfirmBidReport(), out);
                logger.info("chose confirm-bids report");
                return "confirmed_bid_report.pdf";
            default:
                throw new IllegalArgumentException("Invalid report type.");
        }
    }
}

