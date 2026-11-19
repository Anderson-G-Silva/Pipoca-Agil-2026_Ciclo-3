package pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.repository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import pipocaagil.pipocaagil2026_ciclo3.exception.DoctorAlreadyExistsException;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.Doctor;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.DoctorRepository;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.MedicalSpecialty;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.UfCrm;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.infrastructure.persistence.entity.DoctorEntity;
import pipocaagil.pipocaagil2026_ciclo3.util.mapper.MapperDoctor;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaDoctorRepository implements DoctorRepository {
    private final DoctorEntityRepository doctorEntityRepository;
    private final MapperDoctor mapper;

    public JpaDoctorRepository(DoctorEntityRepository doctorEntityRepository,  MapperDoctor mapper) {
        this.doctorEntityRepository = doctorEntityRepository;
        this.mapper = mapper;
    }


    @Override
    public Doctor save(Doctor doctor) {
        DoctorEntity entity = mapper.domainToEntity(doctor);
        try {
            doctorEntityRepository.save(entity);
        } catch (DataIntegrityViolationException ex) {
            if(isCrmUfViolation(ex)){
                throw new DoctorAlreadyExistsException(entity.getCRM(), entity.getUF().name());
            }
        }

        return mapper.entityToDomain(entity);
    }

    @Override
    public Doctor findByName(String name) {
        DoctorEntity entity = doctorEntityRepository.findByName(name);
        return mapper.entityToDomain(entity);
    }

    @Override
    public Optional<Doctor> findByCRMAndUF(String CRM, UfCrm UF) {
        DoctorEntity entity = doctorEntityRepository.findByCRMAndUF(CRM, UF);
        return Optional.ofNullable(entity).map(mapper::entityToDomain);

    }

    @Override
    public List<Doctor> findBySpecialty(MedicalSpecialty specialty) {
        return doctorEntityRepository.findBySpecialty(specialty)
                .stream()
                .map(mapper::entityToDomain)
                .toList();
    }

    private boolean isCrmUfViolation(Throwable ex) {
        Throwable cause = ex;
        while (cause != null) {
            if (cause instanceof org.hibernate.exception.ConstraintViolationException cve
                    && "uk_doctor_crm_uf".equals(cve.getConstraintName())) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }
}
