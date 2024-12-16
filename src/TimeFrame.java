/**
 * Class TimeFrame
 * Author: Andrew Parkes
 */
public class TimeFrame {

    private int hourStart;
    private int minuteStart;
    private int hourEnd;
    private int minuteEnd;

    public TimeFrame(int hourStart, int minuteStart, int hourEnd, int minuteEnd) {
        this.hourStart = hourStart;
        this.minuteStart = minuteStart;
        this.hourEnd = hourEnd;
        this.minuteEnd = minuteEnd;
    }

    public int getHourStart() {
        return hourStart;
    }

    public int getMinuteStart() {
        return minuteStart;
    }

    public int getHourEnd() {
        return hourEnd;
    }

    public int getMinuteEnd() {
        return minuteEnd;
    }

    public boolean overlaps(TimeFrame other) {
        int startMinutes = hourStart * 60 + minuteStart;
        int endMinutes = hourEnd * 60 + minuteEnd;
        int otherStartMinutes = other.hourStart * 60 + other.minuteStart;
        int otherEndMinutes = other.hourEnd * 60 + other.minuteEnd;

        return (startMinutes < otherEndMinutes && otherStartMinutes < endMinutes);
    }
}