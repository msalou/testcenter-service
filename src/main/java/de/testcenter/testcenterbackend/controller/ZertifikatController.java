package de.testcenter.testcenterbackend.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.testcenter.testcenterbackend.models.Message;
import de.testcenter.testcenterbackend.models.Zertifikat;
import de.testcenter.testcenterbackend.service.ZertifikatService;

@RestController
public class ZertifikatController {

  private final Logger logger = LoggerFactory.getLogger(ZertifikatController.class);

  @Autowired
  private ZertifikatService zertifikatService;

  @PostMapping(value = "/sendZertifikat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Message> sendZertifikat(@RequestBody final Zertifikat zertifikat) {
    try {
      logger.info("Send Zertifikat via Mail");
      zertifikatService.sendZertifikat(zertifikat);
      logger.info("Send Zertifikat via Mail successful");
      return ResponseEntity.ok().body(new Message("0", "Success"));
    } catch (Exception exception) {
      logger.error(exception.getMessage(), exception);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping(value = "/pdf/zertifikat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_PDF_VALUE)
  public void printZertifikat(@RequestBody Zertifikat zertifikat, HttpServletResponse response) {
    try {
      logger.info("Print Zertifikat");
      response.setContentType("application/pdf");
      response.setHeader("Content-Disposition", "attachment; filename=Testresultat.pdf");
      zertifikatService.printZertifikat(zertifikat, response);
      logger.info("Print Zertifikat successful");
    } catch (Exception exception) {
      logger.error(exception.getMessage(), exception);
    }
  }
}