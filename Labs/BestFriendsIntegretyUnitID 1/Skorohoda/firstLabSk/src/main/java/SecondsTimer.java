import java.time.Duration;
import java.time.Instant;

public class SecondsTimer{
    public static Duration calculateTime(Runnable runnable){
        Instant start = Instant.now();
        runnable.run();
        Instant end = Instant.now();
        return Duration.between(start, end);
    }
}
