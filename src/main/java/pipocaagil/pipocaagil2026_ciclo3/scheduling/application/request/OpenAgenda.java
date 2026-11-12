package pipocaagil.pipocaagil2026_ciclo3.scheduling.application.request;


import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.CareModality;
import pipocaagil.pipocaagil2026_ciclo3.scheduling.domain.UfCrm;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


public record OpenAgenda (
        String CRM,
        UfCrm UF,
        CareModality modality,
        LocalDate startDate,
        LocalDate endDate,
        Set<DaysOfWeek> daysOfWeek,
        List<ServiceWindow> windows,
        Integer consultationDuration
) {
}
