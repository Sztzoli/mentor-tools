package mentortools.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "modules")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String url;

    @OneToMany(mappedBy = "module", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Lesson> lessons;

    public Module(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
