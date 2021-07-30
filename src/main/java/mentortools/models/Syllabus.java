package mentortools.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "syllabuses")
public class Syllabus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Syllabus(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "syllabus")
    public Set<TrainingClass> trainingClasses;

    @ManyToMany
    public Set<Module> modules;

    public void addModule(Module module) {
        if (modules == null) {
            modules = new HashSet<>();
        }
        modules.add(module);
    }
}
