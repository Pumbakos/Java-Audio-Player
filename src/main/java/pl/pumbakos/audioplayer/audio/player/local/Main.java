package pl.pumbakos.audioplayer.audio.player.local;


import pl.pumbakos.audioplayer.audio.controler.ClipQueue;
import pl.pumbakos.audioplayer.audio.player.SoundClip;

public class Main {
    public static void main(String[] args) {
        SoundClip clip = SoundClip.getInstance();
        ClipQueue queue = new ClipQueue(clip);


        clip.setProperties(queue, clip);
        clip.subscribe(queue);

//        clip.menu();
        clip.cmd();
    }
}
