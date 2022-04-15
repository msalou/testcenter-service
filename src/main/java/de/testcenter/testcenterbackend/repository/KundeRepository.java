package de.testcenter.testcenterbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.testcenter.testcenterbackend.models.Kunde;

@Repository
public interface KundeRepository extends JpaRepository<Kunde, Long> {}