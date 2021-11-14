package audio.file.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import pl.pumbakos.audioplayer.audio.file.controller.FileController;

class FileLocalControllerTest {
    private final String DEFAULT_PATH = "D:\\Desktop\\CODE\\JAVA\\AudioPlayer\\music\\wav\\";

    @Test
    void setDefaultFolderToNull() {
        FileController fc = new FileController(DEFAULT_PATH);
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> fc.setDefaultFolder(null));
    }

    @RepeatedTest(value = 6)
    void setCurrentSongWithIndex(RepetitionInfo repetitionInfo){
        ClipTitle[] titles = ClipTitle.values();
        FileController fc = new FileController(DEFAULT_PATH);
        fc.openFolder();
        Assertions.assertEquals(titles[repetitionInfo.getCurrentRepetition() -1].toString(),
                fc.setCurrentSong(repetitionInfo.getCurrentRepetition() -1));
    }
}