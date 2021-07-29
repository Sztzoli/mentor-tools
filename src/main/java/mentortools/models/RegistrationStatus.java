package mentortools.models;

import java.util.Arrays;

public enum RegistrationStatus {
    ACTIVE, EXIT_IN_PROGRESS, EXITED;


    public static boolean hasStatus(RegistrationStatus status) {
        return Arrays.stream(RegistrationStatus.values()).anyMatch(s -> s == status);
    }
}
