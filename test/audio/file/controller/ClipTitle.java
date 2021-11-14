package audio.file.controller;

public enum ClipTitle {
    BANDYTA("bandyta.wav"),
    FIOLKOWE_POLE("fiolkowe_pole.wav"),
    IM_YOUR_BIGGEST_FAN("im_your_biggest_fan.wav"),
    PID_OBLACZKOM("pid_oblaczkom.wav"),
    TO_JA("to_ja.wav"),
    WODYMIDAJ("wodymidaj.wav");

    private String s;

    ClipTitle(String s) {
        this.s = s;
    }

    public String toString() {
        return s;
    }
}
