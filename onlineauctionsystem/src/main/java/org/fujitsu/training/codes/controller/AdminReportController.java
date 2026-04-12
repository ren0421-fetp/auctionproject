package org.fujitsu.training.codes.controller;

import java.io.ByteArrayOutputStream;

import org.fujitsu.training.codes.dao.impl.AdminReportDaoImpl;
import org.fujitsu.training.codes.service.ReportPdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/reports")
public class AdminReportController {

    private final AdminReportDaoImpl adminReportDaoImpl;
    private final ReportPdfService reportPdfService;

    public AdminReportController(AdminReportDaoImpl adminReportDaoImpl,
            ReportPdfService reportPdfService) {
        this.adminReportDaoImpl = adminReportDaoImpl;
        this.reportPdfService = reportPdfService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loadReportPage(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/app/login";
        }
        return "adminReportView";
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadPdf(@RequestParam("type") String type,
            HttpSession session) throws Exception {

        if (!isAdmin(session)) {
            return ResponseEntity.status(302)
                    .header("Location", "/app/login")
                    .build();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String filename;

        switch (type) {
            case "users":
                reportPdfService.writeUserReport(adminReportDaoImpl.getUserRegistrationReport(), out);
                filename = "user_registration_report.pdf";
                break;
            case "products":
                reportPdfService.writeProductReport(adminReportDaoImpl.getAuctionItemReport(), out);
                filename = "auction_item_report.pdf";
                break;
            case "bids":
                reportPdfService.writeBidReport(adminReportDaoImpl.getBidReport(), out);
                filename = "bid_report.pdf";
                break;
            case "confirmed-bids":
                reportPdfService.writeConfirmBidReport(adminReportDaoImpl.getConfirmBidReport(), out);
                filename = "confirmed_bid_report.pdf";
                break;
            default:
                throw new IllegalArgumentException("Invalid report type.");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_PDF)
                .body(out.toByteArray());
    }

    private boolean isAdmin(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUsername");
        String userType = (String) session.getAttribute("loggedInUserType");
        return username != null && userType != null && "admin".equalsIgnoreCase(userType);
    }
}
