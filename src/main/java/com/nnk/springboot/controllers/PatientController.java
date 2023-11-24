package com.nnk.springboot.controllers;

import com.nnk.springboot.exceptions.PatientNotFoundException;
import com.nnk.springboot.modeles.Patient;
import com.nnk.springboot.services.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    @PostMapping
    public void createPatient(@RequestBody Patient patient) {
        if (patient != null) {
            LOGGER.info("Création du patient: {}", patient);
            patientService.savePatient(patient);
        } else {
            LOGGER.error("invalide");
        }
    }

    @GetMapping
    public List<Patient> getPatients() {
        LOGGER.info("Liste des patients");
        return patientService.listAll();
    }

    @GetMapping("/{id}")
    public Patient getPatient(@PathVariable("id") Integer id) throws PatientNotFoundException {
        LOGGER.info("Lecture du patient: {}", id);
        return patientService.getPatient(id);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable("id") Integer id, @RequestBody Patient patient) throws PatientNotFoundException {
        LOGGER.info("mise à jour du patient : {}", patient);
        return patientService.updatePatient(id, patient);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable("id") Integer id) throws PatientNotFoundException {
        LOGGER.info("Suppression du patient: {}", id);
        patientService.deletePatient(id);
    }

}
