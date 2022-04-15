package de.testcenter.testcenterbackend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.testcenter.testcenterbackend.models.Kunde;
import de.testcenter.testcenterbackend.models.Message;
import de.testcenter.testcenterbackend.service.KundeService;

@RestController
public class KundeController {

  private final Logger logger = LoggerFactory.getLogger(KundeController.class);

  @Autowired
  private KundeService kundeService;

  @PostMapping(value = "/createKunde", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Message> createKunde(@RequestBody final Kunde kunde) {
    try {
      logger.info("Create Kunde");
      kundeService.createKunde(kunde);
      logger.info("Create Kunde successful");
      return ResponseEntity.ok().body(new Message("0", "Success"));
    } catch (Exception exception) {
      logger.error(exception.getMessage(), exception);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping(value = "/deleteKunde/{id}")
  public ResponseEntity<Message> deleteKunde(@PathVariable String id) {
    try {
      long idInt = Long.parseLong(id);  
      logger.info("Delete Kunde");
      kundeService.deleteKunde(idInt);
      logger.info("Delete Kunde successful");
      return ResponseEntity.ok().body(new Message("0", "Success"));
    } catch (Exception exception) {
      logger.error(exception.getMessage(), exception);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping(value = "/getKunden")
  public ResponseEntity<List<Kunde>> getKunden() {
    try {
      logger.info("Get All Kunden");
      List<Kunde> kunden = kundeService.getKunden();
      logger.info("Get All Kunden successful");
      return ResponseEntity.ok().body(kunden);
    } catch (Exception exception) {
      logger.error(exception.getMessage(), exception);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}