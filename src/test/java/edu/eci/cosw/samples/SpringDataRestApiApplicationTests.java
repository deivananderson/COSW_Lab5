package edu.eci.cosw.samples;


import edu.eci.cosw.jpa.sample.model.Consulta;
import edu.eci.cosw.jpa.sample.model.Paciente;
import edu.eci.cosw.jpa.sample.model.PacienteId;
import edu.eci.cosw.samples.persistence.PatientsRepository;
import edu.eci.cosw.samples.services.PatientServices;
import edu.eci.cosw.samples.services.ServicesException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.fail;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataRestApiApplication.class)
@WebAppConfiguration

public class SpringDataRestApiApplicationTests  {

    @Autowired
    PatientServices services;

    @Autowired
    PatientsRepository repositorio;

    @Test
    public void consultarPacienteExistente(){
        PacienteId pI = new PacienteId(1072658,"cc");
        Paciente p = new Paciente(pI,"Deivan Oliva",new Date());
        repositorio.saveAndFlush(p);

        try {
            p = services.getPatient(1072658,"cc");
            Assert.assertNotNull(p);
        }
        catch (ServicesException e){
            fail("Error consultando paciente");
        }
    }

    @Test
    public void consultarPacienteNoExistente(){
        try {
            Paciente p = services.getPatient(1072658,"cc");
            Assert.assertNull(p);
        }
        catch (ServicesException e){
            fail("Error consultando paciente no existente");
        }
    }

    @Test
    public void noPacientesNconsultas(){
        PacienteId pI = new PacienteId(1072658,"cc");
        Consulta c = new Consulta(new Date(), "PRONTO A MORIR JAJAJA");
        Set<Consulta> consultas = new HashSet<>(0);
        consultas.add(c);
        Paciente p = new Paciente(pI,"Deivan Oliva",new Date(),consultas);
        repositorio.saveAndFlush(p);
        try {
            List<Paciente> pacientes = services.topPatients(2);
            Assert.assertTrue(pacientes.isEmpty());
        }
        catch (ServicesException e){
            fail("Error consultando con N o mas consultas");
        }
    }

    @Test
    public void registro3pacientes(){
        PacienteId pI = new PacienteId(1072651,"cc");
        Paciente p = new Paciente(pI,"Deivan SIN CONSULTA",new Date());
        repositorio.save(p);

        PacienteId pI2 = new PacienteId(1072652,"cc");
        Consulta c1 = new Consulta(new Date(), "PRONTO A MORIR JAJAJA");
        Set<Consulta> consultas1 = new HashSet<>(0);
        consultas1.add(c1);
        Paciente p2 = new Paciente(pI,"Deivan CON UNA CONSULTA",new Date(),consultas1);
        repositorio.save(p2);

        PacienteId pI3 = new PacienteId(1072653,"cc");
        Consulta c2 = new Consulta(new Date(), "PRONTO A MORIR JAJAJA");
        Consulta c3 = new Consulta(new Date(), "YA MORIR JAJAJA");
        Set<Consulta> consultas2 = new HashSet<>(0);
        consultas2.add(c2);
        consultas2.add(c3);
        Paciente p3 = new Paciente(pI,"Deivan CON 2 CONSULTAS",new Date(),consultas2);
        repositorio.save(p3);
        repositorio.flush();

        try {
            List<Paciente> pacientes = services.topPatients(1);
            Assert.assertFalse(pacientes.isEmpty());
        } catch (ServicesException ex) {
            fail("Error consulta");
        }
    }



	@Test
	public void contextLoads() {
	}
        
        @Test
        public void patientLoadTest(){
    
        }

}
