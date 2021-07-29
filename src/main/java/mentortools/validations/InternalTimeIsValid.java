package mentortools.validations;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@Value
@AllArgsConstructor
public class InternalTimeIsValid {

    LocalDate startTime;
    LocalDate endTime;

    public boolean isValid() {
        return startTime != null &&
                endTime != null &&
                startTime.isBefore(endTime);
    }
}
