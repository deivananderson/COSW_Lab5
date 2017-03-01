package edu.eci.cosw.samples.services;

import edu.eci.cosw.jpa.sample.model.Paciente;
import edu.eci.cosw.jpa.sample.model.PacienteId;
import edu.eci.cosw.samples.persistence.PatientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by USUARIO on 28/02/2017.
 */

@Service
public class PatientServicesImpl implements PatientServices {

    @Autowired
    private PatientsRepository repositorio;

    @Override
    public Paciente getPatient(int id, String tipoid) throws ServicesException {
        PacienteId p = new PacienteId(id,tipoid);
        return repositorio.getOne(p);
    }

    @Override
    public List<Paciente> topPatients(int n) throws ServicesException {
        return repositorio.findPatientsTop(n);
    }
}
