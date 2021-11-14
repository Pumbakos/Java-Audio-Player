package pl.pumbakos.audioplayer.audio.file.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileController {
    private final Scanner scanner = new Scanner(System.in);
    private String defaultFolder;
    private List<File> songClips = new ArrayList();
    private volatile String currentSong;
    private volatile String previousSong;
    private volatile String nextSong;
    private volatile int index;

    protected FileController() {
    }

    public FileController(String defaultFolder) {
        this.defaultFolder = defaultFolder;
        openFolder();
    }

    public final boolean openFolder() {
        File file = new File(defaultFolder);
        for (File f : Objects.requireNonNull(file.listFiles())) {
            if (!f.isDirectory()) {
                songClips.add(new File(f.getName()));
            }
        }
        return true;
    }

    public final void listSongs() {
        for (int i = 0; i < songClips.size(); i++) {
            System.out.println((i + 1) + ". " + songClips.get(i).getName().
                    substring(0, songClips.get(i).getName().length() - 4).replace('_', ' '));
        }
    }

    /**
     * *Displays the songs in the current folder and waits for the user's keyboard input
     *
     * @return chosen song
     * @throws InputMismatchException when the usersSongChoice is a non-number
     */
    public final String setCurrentSong() throws InputMismatchException {
        System.out.print("\nChoose song (1-" + songClips.size() + "): ");
        String input = scanner.nextLine();
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.err.println("Please choose between the range (1-" + songClips.size() + "): ");
        }
        if (index > songClips.size() || index < 0) {
            throw new InputMismatchException("Out of the range.");
        }

        currentSong = songClips.get(index).getName();

        setPreviousSong(index);
        setNextSong(index);

        return currentSong;
    }

    public final String setCurrentSong(int index) {
        if (index < 0) {
            currentSong = songClips.get(songClips.size() - 1).getName();
            this.index = songClips.size() - 1;

            setPreviousSong(this.index);
            setNextSong(this.index);

            return currentSong = songClips.get(this.index).getName();
        }
        if (index > songClips.size() - 1) {
            currentSong = songClips.get(0).getName();
            this.index = 0;

            setPreviousSong(this.index);
            setNextSong(this.index);

            return currentSong = songClips.get(this.index).getName();
        }
        this.index = index;
        setPreviousSong(this.index);
        setNextSong(this.index);
        return currentSong = songClips.get(this.index).getName();
    }

    /**
     * *Checks whether currentSong is not the first element of list
     *
     * @param indexOfCurrentSong defines which song to play previous
     *                           * @code if (indexOfCurrentSong < 1) : we have here '< 1'  because in 'else' we have -1
     *                           * and then it throws exception described below. If we had '< 0' then 'previousSong' would have -1 index
     * @return previousSong's name
     * @throws NullPointerException if 'indexOfCurrentSong' is lower than 0
     */
    public final String setPreviousSong(int indexOfCurrentSong) throws NullPointerException {
        if (indexOfCurrentSong < 1) {
            previousSong = songClips.get(songClips.size() - 1).toString();
        } else {
            previousSong = songClips.get(indexOfCurrentSong - 1).toString();
        }
        return previousSong;
    }

    /**
     * Checks whether currentSong is not the last element of list
     *
     * @param indexOfCurrentSong defines which song to play next
     *                           * if (indexOfCurrentSong > musicList.size() -2) : we have here '-2' because in 'else' we have +1
     *                           * and then it throws exception described below. If we had '- 1' then 'nextSong' would have musicList.size() index
     * @return nextSong's name
     * @throws ArrayIndexOutOfBoundsException if 'indexOfCurrentSong' is greater than size of list -1
     */
    public final String setNextSong(int indexOfCurrentSong) throws ArrayIndexOutOfBoundsException {
        if (indexOfCurrentSong > songClips.size() - 2) {
            nextSong = songClips.get(0).toString();
        } else {
            nextSong = songClips.get(indexOfCurrentSong + 1).toString();
        }
        return nextSong;
    }

    public String getPreviousSong() {
        return previousSong;
    }

    public String getNextSong() {
        return nextSong;
    }

    public String getCurrentSong() {
        return currentSong;
    }

    public String getDefaultFolder() {
        return defaultFolder;
    }

    public boolean setDefaultFolder(String defaultFolder) {
        if (defaultFolder == null || !Files.exists(Path.of(defaultFolder))) {
            System.err.println(Error.GIVEN_PATH_IS_NOT_VALID);
            return false;
        }
        setDefaults();

        this.defaultFolder = defaultFolder;
        return openFolder();
    }

    private void setDefaults() {
        Iterator<File> fit = songClips.listIterator();
        while (fit.hasNext()) {
            fit.next();
            fit.remove();
        }

        currentSong = "";
        nextSong = "";
        previousSong = "";
        index = 0;
    }

    public int getIndex() {
        return index;
    }

    public List<File> getSongClips() {
        return songClips;
    }
}
