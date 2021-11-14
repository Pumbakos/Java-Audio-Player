package pl.pumbakos.audioplayer.audio.player;

import jdk.jfr.Label;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Label("FlagParam")
@Target(ElementType.FIELD)
public @interface FlagParam {
}
