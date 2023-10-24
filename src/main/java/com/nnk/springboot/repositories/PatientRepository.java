package com.nnk.springboot.repositories;

import com.nnk.springboot.modeles.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    List<Patient> findByNom(String nom);

    /**
     * récupere les enregistrements dont l'id correspond a une des valeurs de la liste d'id
     * @param ids
     * @return retourne les patients dont l'id est identique a un des id de la liste fournit
     */
    @Query("SELECT p FROM Patient p WHERE p.id IN (:ids)")
    List<Patient> findByIdsIn(@Param("ids") List<Integer> ids);
}
