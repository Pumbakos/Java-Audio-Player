package pl.pumbakos.audioplayer.audio.resources;

import org.intellij.lang.annotations.RegExp;

public class Regex {
    @RegExp
    public static final String FOLDER_REGEX = "^[a-zA-Z]:\\\\(((?![<>:\"\\\\|?*]).)+((?<![ .])\\\\)?){1,256}$";

    @RegExp
    public static final String COMMAND_OR_FLAG = "^((\\-)*[A-Za-z]+(\\s)*)|(\\-\\-[A-Za-z]+\\-[A-Za-z]+)|(\\-[0-9]{1,2})$";

    @RegExp
    public static final String COMMAND_REGEX = COMMAND_OR_FLAG + "|" + FOLDER_REGEX;

}
