package pl.pumbakos.audioplayer.audio.exception;

import org.jetbrains.annotations.NotNull;

public class SongNotSetException extends RuntimeException{
    public SongNotSetException(){
        super();
    }

    public SongNotSetException(@NotNull String message){
        super(message);
    }
}
