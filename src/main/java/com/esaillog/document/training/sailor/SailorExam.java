package com.esaillog.document.training.sailor;

import com.esaillog.sailor.SailorRepository;
import com.esaillog.training.Training;
import com.esaillog.training.TrainingNotFoundException;
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
import java.util.UUID;

import static com.esaillog.document.OpenPdfUtils.*;

@Service
@RequiredArgsConstructor
public class SailorExam {
    public static final String LOGO_FILE_PATH = "./src/main/resources/pictures/Sztorm_Grupa.png";
    public final SailorRepository sailorRepository;
    public final TrainingRepository trainingRepository;

    public void getProtocol(String uuid, HttpServletResponse response) throws IOException {
        Training sailorTraining = trainingRepository.findById(UUID.fromString(uuid)).orElseThrow(() -> new TrainingNotFoundException(uuid));




        BaseFont tinos = BaseFont.createFont("./src/main/resources/fonts/Tinos-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont tinos_bold = BaseFont.createFont("./src/main/resources/fonts/Tinos-Bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);


        Document protocol = new Document(PageSize.A4);
        PdfWriter.getInstance(protocol, response.getOutputStream());

        Font mainFont = new Font(tinos, 11);
        Font mainFontBold = new Font(tinos_bold, 11);
        Font h3 = new Font(tinos_bold, 12);
        Font h2 = new Font(tinos_bold, 14);
        Font h1 = new Font(tinos_bold, 16);

        protocol.open();

        Image logo = Image.getInstance(LOGO_FILE_PATH);
        logo.scaleToFit(140, 140);

        PdfPTable headerTable = createTable(2, 100);

        PdfPCell imageCell = new PdfPCell(logo);
        imageCell.setBorderWidth(0);
        imageCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        imageCell.setVerticalAlignment(Element.ALIGN_TOP);
        headerTable.addCell(imageCell);

        PdfPCell examNumber = createCellWithAlignmentAndPadding(String.format("Nr egzaminu: %s", sailorTraining.getNumber()), Element.ALIGN_TOP, Element.ALIGN_RIGHT, 0, mainFont);
        examNumber.setBorderWidth(0);
        headerTable.addCell(examNumber);

        protocol.add(headerTable);

        Paragraph title = createFormattedParagraph("PROTOKÓŁ KOMISJI EGZAMINACYJNEJ", 0, 0, h1);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        protocol.add(title);

        Paragraph subtitle = createFormattedParagraph("NA STOPIEŃ ŻEGLARZA JACHTOWEGO", 0, 10, h2);
        subtitle.setAlignment(Paragraph.ALIGN_CENTER);
        protocol.add(subtitle);

        Paragraph line1 = createFormattedParagraph(
                "Przeprowadzony w dniach: 00.00.0000r.\nMiejsce egzaminu: Dąbrowa Górnicza Pogoria I\nDo egzaminu przystąpiło: 00 kandydatów, z których 00 zdało egzamin", 0, 0, mainFont);
        protocol.add(line1);

        Paragraph line2 = createFormattedParagraph("Skład komisji egzaminacyjnej", 0, 10, mainFontBold);
        protocol.add(line2);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(90);
        table.addCell(createCellWithAlignmentAndPadding(" ", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 6, mainFont));
        table.addCell(createCellWithAlignmentAndPadding("Imię i nazwisko", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 6, mainFont));
        table.addCell(createCellWithAlignmentAndPadding("Stopień żeglarski i nr patentu", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 6, mainFont));
        table.addCell(createCellWithAlignmentAndPadding("Stopień instruktorski i nr patentu", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 6, mainFont));

        table.addCell(createCellWithAlignmentAndPadding("Przewodniczący", Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 6, mainFont));
        table.addCell(createCellWithAlignmentAndPadding("Jan Pawłowski", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 6, mainFont));
        table.addCell(createCellWithAlignmentAndPadding("JSM 19810", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 6, mainFont));
        table.addCell(createCellWithAlignmentAndPadding("IŻ 5672", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 6, mainFont));

        table.addCell(createCellWithAlignmentAndPadding("Sekretarz", Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 6, mainFont));
        table.addCell(createCellWithAlignmentAndPadding("Krzysztof Gębski", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 6, mainFont));
        table.addCell(createCellWithAlignmentAndPadding("KJ 2282", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 6, mainFont));
        table.addCell(createCellWithAlignmentAndPadding("MIŻ 4276", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, 6, mainFont));

        protocol.add(table);


        Paragraph line5 = createFormattedParagraph("Opis egzaminu", 0, 0, mainFontBold);
        protocol.add(line5);

        Paragraph line6 = new Paragraph("Część teoretyczna: przepisy, ratownictwo, meteorologia," + " locja, teoria żeglowania, budowa jachtów", mainFont);
        protocol.add(line6);

        Paragraph line7 = new Paragraph("Część praktyczna - warunki pogodowe i manewry wykonane: 0°B," + " odejście i podejście do kei, zwrot przez sztag i rufę, stanięcie w dryf, człowiek za burtą na żaglach i silniku", mainFont);
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

}
