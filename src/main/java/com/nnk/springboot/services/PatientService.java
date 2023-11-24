package com.nnk.springboot.services;

import com.nnk.springboot.exceptions.PatientNotFoundException;
import com.nnk.springboot.modeles.Patient;

import java.util.List;

public interface PatientService {
    Patient savePatient(Patient patient);

    List<Patient> listAll();

    Patient getPatient(Integer id) throws PatientNotFoundException;

    Patient updatePatient(Integer id, Patient patient) throws PatientNotFoundException;

    String deletePatient(Integer id);

}
