package pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.repository;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.Patient;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.PatientRepository;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.entity.PatientEntity;
import pipocaagil.pipocaagil2026_ciclo3.util.mapper.MapperPatient;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaPatientRepository implements PatientRepository {
    private final PatientEntityRepository patientEntityRepository;
    private final MapperPatient mapper;

    public JpaPatientRepository(PatientEntityRepository patientEntityRepository, MapperPatient mapper) {
        this.patientEntityRepository = patientEntityRepository;
        this.mapper = mapper;
    }

    @Override
    public Patient save(Patient patient) {
        PatientEntity entity = mapper.domainToEntity(patient);
        patientEntityRepository.save(entity);
        return mapper.entityToDomain(entity);
    }

    @Override
    public Patient findByCPF(String CPF) {
        PatientEntity entity= patientEntityRepository.findByCPF(CPF);
        return mapper.entityToDomain(entity);
    }

    @Override
    public Patient findByName(String name) {
        PatientEntity entity = patientEntityRepository.findByName(name);
        return mapper.entityToDomain(entity);
    }

    @Override
    public List<Patient> findAll() {
        return patientEntityRepository.findAll().stream().map(mapper::entityToDomain).toList();
    }

    @Override
    public Patient deleteByCPF(String CPF) {
        PatientEntity entity = patientEntityRepository.findByCPF(CPF);
        patientEntityRepository.delete(entity);
        return mapper.entityToDomain(entity);
    }
}
