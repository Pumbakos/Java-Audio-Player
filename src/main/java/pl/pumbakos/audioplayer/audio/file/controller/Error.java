package pl.pumbakos.audioplayer.audio.file.controller;

public enum Error {
    GIVEN_PATH_IS_NOT_VALID("Given path is not valid", -1);

    private String name;
    private int code;

    Error(String name, int code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getCode() {
        return code;
    }
}
