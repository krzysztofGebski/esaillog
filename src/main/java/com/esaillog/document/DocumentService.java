package com.esaillog.document;

import com.esaillog.training.TrainingService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final TrainingService trainingService;
    public void getDocument(String uuid, HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("Welcome", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);
        document.close();

    }
}
