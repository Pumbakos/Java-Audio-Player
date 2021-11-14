package pl.pumbakos.audioplayer.audio.resources;

public enum Command {
    PLAY("play"),
    STOP("stop"),
    PAUSE("pause"),
    RESUME("resume"),
    NEXT("next"),
    PREVIOUS("previous"),
    LIST("list"),
    FOLDER("folder"),
    EXIT("exit");

    private String name;

    Command(String name){
        this.name = name;
    }

    public static Command toCommand(String stringCommand){
        Command[] values = Command.values();
        for (Command value : values) {
            if (value.name.equals(stringCommand)){
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
