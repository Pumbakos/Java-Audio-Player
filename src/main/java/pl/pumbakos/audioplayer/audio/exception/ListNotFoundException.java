package pl.pumbakos.audioplayer.audio.exception;

import org.jetbrains.annotations.NotNull;

public class ListNotFoundException extends NotFoundException{
    public ListNotFoundException(){
        super();
    }

    public ListNotFoundException(@NotNull String message){
        super(message);
    }

    public ListNotFoundException(@NotNull String message, @NotNull Throwable e){
        super(message, e);
    }
}
