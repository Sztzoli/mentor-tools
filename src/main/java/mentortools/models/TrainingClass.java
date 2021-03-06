package mentortools.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.exceptions.SyllabusPostMethodNotAllowed;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "training_classes")
public class TrainingClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "trainingClass")
    private Set<Registration> registrations;

    @ManyToOne
    private Syllabus syllabus;

    public TrainingClass(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void addSyllabus(Syllabus syllabus) {
        if (this.syllabus != null) {
            throw new SyllabusPostMethodNotAllowed();
        }
        this.syllabus = syllabus;
    }
}
