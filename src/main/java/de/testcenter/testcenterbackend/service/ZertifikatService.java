package de.testcenter.testcenterbackend.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.DocumentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.testcenter.testcenterbackend.models.Zertifikat;

@Service
public class ZertifikatService {

    @Autowired
    ZertifikatPrintService zertifikatPrintService;

    @Autowired
    EmailSenderService emailSenderService;

    public void sendZertifikat(Zertifikat zertifikat) throws IOException {
        String zertifikatFileName = "Testresultat.pdf";
        OutputStream stream = new FileOutputStream(zertifikatFileName);
        zertifikatPrintService.printZertifikatToStream(zertifikat, stream);

        List<File> files = new ArrayList<>();
        File zertifikatFile = new File(zertifikatFileName);
        files.add(zertifikatFile);
        emailSenderService.sendMail(zertifikat.getKunde().getEmail().trim(), files, zertifikat);
        Files.deleteIfExists(zertifikatFile.toPath());
    }

    public void printZertifikat(Zertifikat zertifikat, HttpServletResponse response) throws DocumentException, IOException {
        OutputStream stream = response.getOutputStream();
        zertifikatPrintService.printZertifikatToStream(zertifikat, stream);
    }
}
