package audio.player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.pumbakos.audioplayer.audio.controler.ClipQueue;
import pl.pumbakos.audioplayer.audio.player.SoundClip;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SoundClipTest {
    private final String defaultFolder = "D:\\Desktop\\CODE\\JAVA\\AudioPlayer\\music\\wav\\";
    private SoundClip clip = SoundClip.getInstance();
    private String packageName = SoundClip.class.getPackage().getName();
    private String path = packageName + ".SoundClip";

    @Test
    void setDefaultFolderToNull() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> clip.setDefaultFolder(null));
    }

    @Test
    void prepareClipWithNullPath() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        Method m = clip.getClass().getDeclaredMethod("prepareClip", String.class);
        clip.setDefaultFolder(defaultFolder);
        String arg = null;
        m.setAccessible(true);
        Assertions.assertFalse((Boolean) m.invoke(clip, arg));
    }

    @Test
    void playWithNullThread() {
        NullPointerException e = assertThrows(NullPointerException.class, clip::play);
    }

    @Test
    void stopWithNullSong() {
        NullPointerException e = assertThrows(NullPointerException.class, clip::stop);
    }

    @Test
    void pauseWithNullSong() {
        NullPointerException e = assertThrows(NullPointerException.class, clip::pause);
    }

    @Test
    @DisplayName("Add subscriber to subs list")
    void addSubscriber() {
        assertTrue(clip.subscribe(new ClipQueue()));
    }

    @Test
    @DisplayName("Add null subscriber to subs list")
    void addNullSubscriber() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> clip.subscribe(null));
    }

    @Test
    @DisplayName("Remove subscriber from subs list")
    void removeSubscriber() {
        ClipQueue cq = new ClipQueue();
        clip.subscribe(cq);
        assertTrue(clip.unsubscribe(cq));
    }

    @Test
    @DisplayName("Remove null subscriber from subs list")
    void removeNullSubscriber() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> clip.unsubscribe(null));
    }

    @Test
    @DisplayName("Remove subscriber from empty subs list")
    void removeSubscriberFromEmptyList() {
        ClipQueue cq = new ClipQueue();
        clip.subscribe(cq);
        clip.removeAllSubscribers();
        NullPointerException e = assertThrows(NullPointerException.class, () -> clip.unsubscribe(cq));
    }

    @Test
    @DisplayName("Remove null subscriber from empty subs list")
    void removeNullSubscriberWhenListIsEmpty() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> clip.unsubscribe(null));
    }
}