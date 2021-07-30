package mentortools.commands;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.models.Exercise;
import mentortools.models.Video;
import mentortools.validations.ExistLesson;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLessonCompletionCommand {

    @ExistLesson
    @Schema(description = "id of Lesson")
    private Long lessonId;

    @Schema(description = "LessonCompletion's video")
    private Video video;

    @Schema(description = "LessonCompletion's video")
    private Exercise exercise;
}
