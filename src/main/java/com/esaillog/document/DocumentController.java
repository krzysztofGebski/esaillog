package com.esaillog.document;

import com.esaillog.document.training.sailor.SailorExam;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
    private final SailorExam sailorExam;

    @GetMapping("/training/{uuid}")
    public void getDocumentByUUID(@PathVariable String uuid, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=file.pdf");
        documentService.getDocument(uuid, response);
    }

    @GetMapping("/training/sailor/{uuid}")
    public void getProtocolByUUID(@PathVariable String uuid, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=file.pdf");
        sailorExam.getProtocol(uuid, response);
    }
}
