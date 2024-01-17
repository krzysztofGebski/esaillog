package com.esaillog.document.training.sailor;

import com.esaillog.sailor.SailorRepository;
import com.esaillog.training.TrainingRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SailorExam {
    public final SailorRepository sailorRepository;
    public final TrainingRepository trainingRepository;

    public void getProtocol(String uuid, HttpServletResponse response) throws IOException {

        BaseFont tinos = BaseFont.createFont("./src/main/resources/fonts/Tinos-Regular.ttf",
                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont tinos_bold = BaseFont.createFont("./src/main/resources/fonts/Tinos-Bold.ttf",
                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont tinos_italic = BaseFont.createFont("./src/main/resources/fonts/Tinos-Italic.ttf",
                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont tinos_bold_italic = BaseFont.createFont("./src/main/resources/fonts/Tinos-BoldItalic.ttf",
                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);



        Document protocol = new Document(PageSize.A4);
        PdfWriter.getInstance(protocol, response.getOutputStream());

        Font mainFont = new Font(tinos, 11);
        Font mainFontBold = new Font(tinos_bold, 11);
        Font h3 = new Font(tinos_bold, 12);
        Font h2 = new Font(tinos_bold, 14);
        Font h1 = new Font(tinos_bold, 16);

        protocol.open();

        Paragraph examNumber = createFormattedParagraph("Nr egzaminu: XXX/Ż/KAT/24", 0f, 40f, mainFont);
        examNumber.setAlignment(Paragraph.ALIGN_RIGHT);
        protocol.add(examNumber);

        Paragraph title = new Paragraph("PROTOKÓŁ KOMISJI EGZAMINACYJNEJ", h1);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        protocol.add(title);
        title.setSpacingAfter(15f);

        Paragraph subtitle = new Paragraph("NA STOPIEŃ ŻEGLARZA JACHTOWEGO", h2);
        subtitle.setAlignment(Paragraph.ALIGN_CENTER);
        subtitle.setSpacingAfter(20f);
        protocol.add(subtitle);

        Paragraph line1 = new Paragraph("Przeprowadzony w dniach: 00.00.0000r.", mainFont);
        protocol.add(line1);

        Paragraph line2 = new Paragraph("Miejsce egzaminu: Dąbrowa Górnicza Pogoria I", mainFont);
        protocol.add(line2);

        Paragraph line3 = new Paragraph("Do egzaminu przystąpiło: 00 kandydatów, z których 00 zdało egzamin");
        protocol.add(line3);

        Paragraph line4 = new Paragraph("Skład komisji egzaminacyjnej", mainFontBold);
        protocol.add(line4);

        PdfPTable table = new PdfPTable(4);
        table.addCell(new PdfPCell(new Phrase("", mainFont)));
        table.addCell(new PdfPCell(new Phrase("Imię i nazwisko", mainFont)));
        table.addCell(new PdfPCell(new Phrase("Stopień żeglarski i nr patentu", mainFont)));
        table.addCell(new PdfPCell(new Phrase("Stopień instruktorski i nr patentu", mainFont)));

        table.addCell(new PdfPCell(new Phrase("Przewodniczący", mainFont)));
        table.addCell(new PdfPCell(new Phrase("Jan Pawłowski", mainFont)));
        table.addCell(new PdfPCell(new Phrase("JSM 19810", mainFont)));
        table.addCell(new PdfPCell(new Phrase("IŻ 5672", mainFont)));

        table.addCell(new PdfPCell(new Phrase("Sekretarz", mainFont)));
        table.addCell(new PdfPCell(new Phrase("Krzysztof Gębski", mainFont)));
        table.addCell(new PdfPCell(new Phrase("KJ 2282", mainFont)));
        table.addCell(new PdfPCell(new Phrase("MIŻ 4276", mainFont)));

        table.addCell(new PdfPCell(new Phrase("Członek", mainFont)));
        table.addCell(new PdfPCell(new Phrase("", mainFont)));
        table.addCell(new PdfPCell(new Phrase("", mainFont)));
        table.addCell(new PdfPCell(new Phrase("", mainFont)));
        protocol.add(table);

        Paragraph line5 = new Paragraph("Opis egzaminu", mainFontBold);
        protocol.add(line5);

        Paragraph line6 = new Paragraph("Część teoretyczna: przepisy, ratownictwo, meteorologia," +
                " locja, teoria żeglowania, budowa jachtów", mainFont);
        protocol.add(line6);

        Paragraph line7 = new Paragraph("Część praktyczna - warunki pogodowe i manewry wykonane: 0B," +
                " odejeśćie i podejście do kei, zwrot przez sztag i rufę, stanięcie w dryf, człowiek za burtą na żaglach i silniku", mainFont);
        protocol.add(line7);

        Paragraph line8 = new Paragraph("Podpisy i pieczątki komisji egzaminacyjnej", mainFontBold);
        protocol.add(line8);

        Phrase phrase1 = new Phrase("....................", mainFont);
        Phrase phrase2 = new Phrase("....................", mainFont);
        Paragraph breakLine = new Paragraph("");
        Phrase phrase3 = new Phrase("Sekretarz KE", mainFont);
        Phrase phrase4 = new Phrase("Członek KE", mainFont);

        protocol.add(phrase1);
        protocol.add(phrase2);
        protocol.add(breakLine);
        protocol.add(phrase3);
        protocol.add(phrase4);
        protocol.add(breakLine);

        Paragraph line9 = new Paragraph("Poświadczam prawidłowość przeprowadzonego egzaminu", mainFontBold);
        protocol.add(line9);

        Paragraph line10 = new Paragraph("Dąbrowa Górnicza, dnia 00.00.000", mainFontBold);
        protocol.add(line10);

        Paragraph line11 = new Paragraph("....................", mainFontBold);
        line11.setAlignment(Element.ALIGN_RIGHT);
        protocol.add(line11);

        Paragraph line12 = new Paragraph("Przewodniczący KE", mainFontBold);
        line12.setAlignment(Element.ALIGN_RIGHT);
        protocol.add(line12);

        protocol.close();
    }

    private Paragraph createFormattedParagraph(String content, float spacingBefore, float spacingAfter, Font font) {
        Paragraph paragraph = new Paragraph(content, font);
        paragraph.setSpacingBefore(spacingBefore);
        paragraph.setSpacingAfter(spacingAfter);
        return paragraph;
    }
}
