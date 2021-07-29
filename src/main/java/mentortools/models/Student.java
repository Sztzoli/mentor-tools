package mentortools.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Column(name = "github_username")
    private String githubUsername;

    private String comment;

    @OneToMany(mappedBy = "student")
    private Set<Registration> registrations;

    public Student(String name, String email, String githubUsername, String comment) {
        this.name = name;
        this.email = email;
        this.githubUsername = githubUsername;
        this.comment = comment;
    }
}
