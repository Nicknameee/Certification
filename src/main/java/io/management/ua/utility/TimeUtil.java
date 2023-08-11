package io.management.ua.utility;

import java.time.Clock;
import java.time.ZonedDateTime;

public class TimeUtil {
    public static ZonedDateTime getCurrentTime() {
        return ZonedDateTime.now(Clock.systemUTC());
    }
}
