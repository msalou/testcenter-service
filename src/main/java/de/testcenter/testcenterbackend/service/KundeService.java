package de.testcenter.testcenterbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.testcenter.testcenterbackend.models.Kunde;
import de.testcenter.testcenterbackend.repository.KundeRepository;

@Service
public class KundeService {
    @Autowired
    private KundeRepository kundeRepository;

    public void createKunde(Kunde kunde) {
        kundeRepository.save(kunde);
    }

    public void deleteKunde(long id) {
        kundeRepository.deleteById(id);
    }

    public List<Kunde> getKunden() {
        return kundeRepository.findAll();
    }
}
