package mentortools.repositories;

import mentortools.dtos.RegistrationDto;
import mentortools.dtos.TrainingClassesOfStudentDto;
import mentortools.models.Registration;
import mentortools.models.RegistrationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, RegistrationKey> {

    @Query("select new mentortools.dtos.RegistrationDto(r.student.id, r.student.name, r.status)" +
            " from Registration r where r.id = :id")
    RegistrationDto getRegistrationDto(RegistrationKey id);

    @Query("select new mentortools.dtos.RegistrationDto(r.student.id, r.student.name, r.status)" +
            " from Registration r where r.trainingClass.id= :id")
    List<RegistrationDto> listRegistrationByTrainingClassId(Long id);

    @Query("select new mentortools.dtos.TrainingClassesOfStudentDto(r.trainingClass.id, r.trainingClass.name) " +
            "from Registration r where r.student.id = :studentId")
    List<TrainingClassesOfStudentDto> listTrainingClassesOfStudent(Long studentId);
}
