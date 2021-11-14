package pl.pumbakos.audioplayer.audio.resources;

public enum CliError {
    SYNTAX_ERROR("SYNTAX_ERROR", 1),
    FOLDER_CHANGE_ERROR("FOLDER_CHANGE_ERROR", 2),
    WRONG_PARAMETER_ERROR("WRONG_PARAMETER_ERROR", 3),
    CANNOT_RESOLVE_GIVEN_PARAMETER("CANNOT_RESOLVE_GIVEN_PARAMETER", 4),
    NO_FLAG_FOUND("NO_FLAG_FOUND", 5);

    private final String error;
    private final int errorCode;

    CliError(String error, int errorCode){
        this.error = error;
        this.errorCode = errorCode;
    }

    public static String createMessage(CliError error, String message){
        return error + "\n" + message;
    }

    public static String NO_FLAG_FOUND(String flag){
        return NO_FLAG_FOUND + " '" + flag + "'";
    }

    @Override
    public String toString() {
        return error;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
