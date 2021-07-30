package mentortools.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.models.Exercise;
import mentortools.models.Video;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonCompletionDto {

    private StudentDto student;
    private LessonDto lesson;
    private Video video;
    private Exercise exercise;
}
