package de.testcenter.testcenterbackend.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import de.testcenter.testcenterbackend.models.Kunde;
import de.testcenter.testcenterbackend.models.Testung;
import de.testcenter.testcenterbackend.models.Zertifikat;

@Service
public class ZertifikatPrintService {
    private final Logger logger = LoggerFactory.getLogger(ZertifikatPrintService.class);

    @Value("${feature.toggle.qrcode}") 
    boolean featureToggleQRCode;
    
    public void printZertifikatToStream(Zertifikat zertifikat, OutputStream stream) throws IOException {

        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, stream);

        document.open();

        PdfContentByte cb = writer.getDirectContent();
        printTemplate(writer, cb);
        printText(zertifikat, cb);
        if (featureToggleQRCode) {
            printQRCode(zertifikat.getKunde(), cb);
        }

        document.close();
    }

    private PdfContentByte printTemplate(PdfWriter writer, PdfContentByte cb) throws IOException {
        Resource resource = new ClassPathResource("pdf/bescheinigung.pdf");
        PdfReader reader = new PdfReader(resource.getInputStream());
        PdfImportedPage page = writer.getImportedPage(reader, 1);
        cb.addTemplate(page, 0, 0);
        return cb;
    }

    private void printText(Zertifikat zertifikat, PdfContentByte cb) throws IOException {
        Kunde kunde = zertifikat.getKunde();
        Testung testung = zertifikat.getTestung();

        BaseFont font = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        cb.setFontAndSize(font, 12);
        cb.setColorFill(new CMYKColor(255, 125, 0, 25));

        cb.beginText();
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, testung.getErgebnis() + "en Antigentests", 85, 715, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, kunde.getNachname(), 85, 640, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, kunde.getVorname(), 295, 640, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, kunde.getStrasse(), 85, 590, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, kunde.getPlz() + " " + kunde.getOrt(), 85, 575, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, kunde.getGeburtsdatum(), 345, 590, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, kunde.getTelefon() + " / " + kunde.getEmail(), 85, 505, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Siehe ausführende Stelle", 85, 440, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "HEALTHY-Teststationen", 85, 382, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Eschholzstr. 19 • 79106 Freiburg", 85, 367, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Tel.: 0 178 / 7 53 87 37", 85, 352, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "-", 85, 340, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Friedhofweg 3 • 79249 Merzhausen", 85, 328, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, testung.getTestername(), 85, 290, 0);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, testung.getDatum() + ", " + testung.getUhrzeit(), 85, 140, 0);
        cb.endText();
    }

    private void printQRCode(Kunde kunde, PdfContentByte cb) {
        try (ByteArrayOutputStream image = new ByteArrayOutputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            String kundeString = mapper.writeValueAsString(kunde);

            QRCodeWriter barcodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = barcodeWriter.encode(kundeString, BarcodeFormat.QR_CODE, 120, 120);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", image);
            Image qrcode = Image.getInstance(image.toByteArray());
            qrcode.setAbsolutePosition(480, 730);
            cb.addImage(qrcode);
            // cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Ihr persönlicher QR-Code", 453, 730, 0);
            // cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Bei der nächsten Testung vorzeigen", 400, 715, 0);
        } catch (Exception e) {
            logger.error("Error on generating QR code", e);
        }
    }

}
