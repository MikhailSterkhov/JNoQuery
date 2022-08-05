import lombok.experimental.UtilityClass;

@UtilityClass
public class SpeedTest {

    public interface ThrowableRunnable<E extends Exception> {

        void run() throws E;
    }

    public <E extends Exception> void fixLongSpeed(String name, ThrowableRunnable<E> command)
    throws E {

        long start = System.currentTimeMillis();

        command.run();

        long speedMillis = System.currentTimeMillis() - start;
        System.out.printf("[%s] -> Long of Action Speed: %sms. (%s in sec.)%n%n", name, speedMillis, (speedMillis / 1000d));
    }
}
