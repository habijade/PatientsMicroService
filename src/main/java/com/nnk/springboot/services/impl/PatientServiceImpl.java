package com.nnk.springboot.services.impl;

import com.nnk.springboot.exceptions.PatientNotFoundException;
import com.nnk.springboot.modeles.Patient;
import com.nnk.springboot.repositories.PatientRepository;
import com.nnk.springboot.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Patient savePatient(Patient patient) {
        if (patient == null) {
            return null;
        } else {
            return patientRepository.save(patient);
        }
    }

    @Override
    public List<Patient> listAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatient(Integer id) throws PatientNotFoundException {
        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient non trouvé avec l'id : " + id));
    }

    @Override
    public Patient updatePatient(Integer id, Patient patientInfos) throws PatientNotFoundException {
        return patientRepository.findById(id)
                .map(patient -> {
                    patient.setNom(patientInfos.getNom());
                    patient.setPrenom(patientInfos.getPrenom());
                    patient.setGenre(patientInfos.getGenre());
                    patient.setAdressePostale(patientInfos.getAdressePostale());
                    patient.setNumeroDeTelephone(patientInfos.getNumeroDeTelephone());
                    patient.setDateDeNaissance(patientInfos.getDateDeNaissance());
                    return patientRepository.save(patient);
                }).orElseThrow(() -> new PatientNotFoundException("Patient non trouvé avec l'id : " + id));
    }

    @Override
    public String deletePatient(Integer id) {
        patientRepository.deleteById(id);
        return "patient supprimé";
    }
/*
    @Override
    public List<Patient> getPatientByIds(List<String> patientIds) {
        List<Integer> patients = patientIds.stream().map(patient -> Integer.valueOf(patient)).collect(Collectors.toList());
        return patientRepository.findByIdsIn(patients);
    }

 */
}
