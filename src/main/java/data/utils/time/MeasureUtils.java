package data.utils.time;

import java.util.concurrent.TimeUnit;

public class MeasureUtils {
    public static final TimeUnit MILLIS_SECONDS = TimeUnit.MILLISECONDS;
    public static final TimeUnit SECONDS = TimeUnit.SECONDS;
    private static volatile MeasureUtils measureUtils;

    public static MeasureUtils getInstance() {
        if (measureUtils == null) {
            synchronized (MeasureUtils.class) {
                if (measureUtils == null) {
                    measureUtils = new MeasureUtils();
                }
            }
        }

        return measureUtils;
    }

    private MeasureUtils() {
    }

    public static void measureSeconds(Runnable r) {
        getInstance().measure(r, SECONDS);
    }

    public static void measureMillis(Runnable r) {
        getInstance().measure(r, MILLIS_SECONDS);
    }

    private synchronized void measure(Runnable r, final TimeUnit typeUnit) {
        Runnable runnable = () -> {
            long startTimeMillis = System.currentTimeMillis();

            r.run();

            long runTime = (System.currentTimeMillis() - startTimeMillis);

            String timeFormatted = formatTime(runTime, typeUnit);
            System.out.printf("time elapsed: %s.", timeFormatted);
        };

        new Thread(runnable).start();
    }

    private String formatTime(long time, TimeUnit unit) {
        long timeCasted = unit.convert(time, MILLIS_SECONDS);
        return String.format("%d %s", timeCasted, unit);
    }
}
