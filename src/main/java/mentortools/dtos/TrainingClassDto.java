package mentortools.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.models.Syllabus;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingClassDto {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private SyllabusDto syllabus;
}
