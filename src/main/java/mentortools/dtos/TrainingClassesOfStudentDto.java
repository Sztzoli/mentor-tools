package mentortools.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingClassesOfStudentDto {

    private Long trainingClassId;
    private String trainingClassName;
}
