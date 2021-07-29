package mentortools.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationKey implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "training_class_id")
    private Long trainingClassId;
}
