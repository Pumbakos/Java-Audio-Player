package audio.file.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pumbakos.audioplayer.audio.controler.LocalController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LocalControllerTest {
    @Test
    public void fragmentCommandWithOneCommand() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        LocalController lc = new LocalController();
        Method fragmentCommand = lc.getClass().getDeclaredMethod("fragmentCommand", String.class);
        String arg = "play";
        fragmentCommand.setAccessible(true);
        Assertions.assertEquals("play", fragmentCommand.invoke(lc, arg));
    }

    @Test
    public void fragmentCommandWithTwoCommands() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        LocalController lc = new LocalController();
        Method fragmentCommand = lc.getClass().getDeclaredMethod("fragmentCommand", String.class);
        String[] expected = {"play", "-l"};
        String command = "play -l";
        fragmentCommand.setAccessible(true);
        Assertions.assertEquals(expected, fragmentCommand.invoke(lc, command));
    }
}
