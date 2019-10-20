import java.time.*;  
import java.time.temporal.ChronoUnit;  
public class DurationExample3 {  
  public static void main(String[] args) {  
    Duration d = Duration.between(LocalTime.of(7, 00), LocalTime.of(16, 00));  
    System.out.println(d.get(ChronoUnit.SECONDS));
  }  
}
