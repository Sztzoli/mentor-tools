package mentortools.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "registrations")
public class Registration {

    @EmbeddedId
    private RegistrationKey id;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Student student;

    @ManyToOne
    @MapsId("trainingClassId")
    @JoinColumn(name = "training_class_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private TrainingClass trainingClass;

    public Registration(RegistrationKey id, Student student, TrainingClass trainingClass) {
        this.id = id;
        this.student = student;
        this.trainingClass = trainingClass;
        this.status = RegistrationStatus.ACTIVE;
    }
}
