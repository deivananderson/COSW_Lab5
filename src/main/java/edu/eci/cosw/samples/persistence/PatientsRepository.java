package edu.eci.cosw.samples.persistence;

import edu.eci.cosw.jpa.sample.model.Paciente;
import edu.eci.cosw.jpa.sample.model.PacienteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by USUARIO on 28/02/2017.
 */
public interface PatientsRepository extends JpaRepository<Paciente, PacienteId> {

    @Override
    @Query("select p from Paciente p where p.id = ?1")
    public Paciente getOne(PacienteId id);

    @Query("select p from Paciente p where size(p.consultas) >= ?1")
    List<Paciente> findPatientsTop(int top);

}
