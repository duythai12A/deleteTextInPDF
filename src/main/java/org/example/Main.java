package org.example;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.TextChunk;
import com.itextpdf.pdfcleanup.CleanUpProperties;
import com.itextpdf.pdfcleanup.PdfCleanUpLocation;
import com.itextpdf.pdfcleanup.PdfCleanUpTool;
import com.itextpdf.pdfcleanup.autosweep.RegexBasedCleanupStrategy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import com.itextpdf.kernel.geom.Rectangle;

public class Main {
    public static void main(String[] args) throws IOException {
        String src = "Test.pdf";
        String dest = "Output.pdf";
        String lineToRemove = "PDF"; // The line to redact

        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        List<PdfCleanUpLocation> locations = new ArrayList<>();
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            PdfPage page = pdfDoc.getPage(i);
            String text = PdfTextExtractor.getTextFromPage(page, new LocationTextExtractionStrategy());
            if (text.contains(lineToRemove)) {
                float x = 0;
                float y = 0;
                float width = 100;
                float height = 100;
                Rectangle pageSize = page.getPageSize();
//                locations.add(new PdfCleanUpLocation(i, new com.itextpdf.kernel.geom.Rectangle(x, y, width, height)));
                locations.add(new PdfCleanUpLocation(i, pageSize));
            }
        }
        PdfCleanUpTool cleaner = new PdfCleanUpTool(pdfDoc, locations, new CleanUpProperties());
        cleaner.cleanUp();
        pdfDoc.close();
    }
}