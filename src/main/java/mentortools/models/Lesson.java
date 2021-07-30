package mentortools.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String url;

    @ManyToOne()
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Module module;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<LessonCompletion> lessonCompletions;

    public Lesson(String title, String url, Module module) {
        this.title = title;
        this.url = url;
        this.module = module;
    }
}
