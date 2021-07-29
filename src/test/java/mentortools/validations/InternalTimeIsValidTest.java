package mentortools.validations;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InternalTimeIsValidTest {



    @Test
    void isValid() {
        InternalTimeIsValid valid = new InternalTimeIsValid(LocalDate.now(), LocalDate.now().plusDays(1));
        assertTrue(valid.isValid());
    }

    @Test
    void isInValid() {
        InternalTimeIsValid valid = new InternalTimeIsValid(LocalDate.now(), LocalDate.now().minusDays(1));
        assertFalse(valid.isValid());
    }

    @Test
    void isInValidNull() {
        InternalTimeIsValid valid = new InternalTimeIsValid(LocalDate.now(), null);
        assertFalse(valid.isValid());
    }

    @Test
    void isInValidNull2() {
        InternalTimeIsValid valid = new InternalTimeIsValid(null, LocalDate.now());
        assertFalse(valid.isValid());
    }

}