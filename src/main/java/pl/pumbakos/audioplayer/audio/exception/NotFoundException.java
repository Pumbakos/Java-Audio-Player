package pl.pumbakos.audioplayer.audio.exception;

import org.jetbrains.annotations.NotNull;

public class NotFoundException extends RuntimeException {
    public NotFoundException(){
        super();
    }

    public NotFoundException(@NotNull String message){
        super(message);
    }

    public NotFoundException(@NotNull String message, @NotNull Throwable e){
        super(message, e);
    }
}
