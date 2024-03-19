package com.esaillog.document;

import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class OpenPdfUtils {
    public static Paragraph createFormattedParagraph(String content, float spacingBefore, float spacingAfter, Font font) {
        Paragraph paragraph = new Paragraph(content, font);
        paragraph.setSpacingBefore(spacingBefore);
        paragraph.setSpacingAfter(spacingAfter);
        return paragraph;
    }

    public static PdfPTable createTable(int columns, float widthPercentage) {
        PdfPTable table = new PdfPTable(columns);
        table.setWidthPercentage(widthPercentage);
        return table;
    }

    public static PdfPCell createCellWithAlignmentAndPadding(String content, int verticalAlignment, int horizontalAlignment, int padding, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setVerticalAlignment(verticalAlignment);
        cell.setHorizontalAlignment(horizontalAlignment);
        cell.setPadding(padding);
        return cell;
    }
}
