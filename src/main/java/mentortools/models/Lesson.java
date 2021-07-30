package mentortools.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

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

    public Lesson(String title, String url, Module module) {
        this.title = title;
        this.url = url;
        this.module = module;
    }
}
