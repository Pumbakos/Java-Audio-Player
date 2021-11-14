package pl.pumbakos.audioplayer.audio.file.controller;

import pl.pumbakos.audioplayer.audio.exception.ListNotFoundException;
import pl.pumbakos.audioplayer.audio.exception.NotFoundException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.List;

public class FileDownloader {
    private static final String DIRECTORY = "D:\\Desktop\\CODE\\JAVA\\AudioPlayer\\music\\wav\\downloaded\\";
    private static final byte ERROR_STATUS = -1;

    static String requestUrl = "http://localhost:8080/file/download/";
    private String link;
    private File out;

    public FileDownloader(String link, File out) {
        this.link = link;
        this.out = out;
    }

    public FileDownloader() {
    }

    public void download(String filename) {
        long size = downloadClipSize(filename);
        if (size == ERROR_STATUS) {
            throw new NotFoundException();
        }

        try {
            downloadClip(filename, size);
        } catch (IOException e) {
            System.err.println("There was a problem with the server");
        }
    }

    public List<String> getList() throws ListNotFoundException {
        if (getListFromServer() == null) {
            throw new ListNotFoundException();
        }
        return getListFromServer();
    }

    public String getDirectory() {
        return DIRECTORY;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public static void setRequestUrl(String requestUrl) {
        FileDownloader.requestUrl = requestUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public File getOut() {
        return out;
    }

    public void setOut(File out) {
        this.out = out;
    }

    private void downloadClip(String filename, long fileSize) throws IOException {
        setOut(new File(DIRECTORY + filename));
        String request = requestUrl + filename;

        URL url = new URL(request);
        InputStream in = url.openStream();
        ReadableByteChannel urlChannel = Channels.newChannel(in);

        FileChannel channel = new FileOutputStream(getOut()).getChannel();
        channel.transferFrom(urlChannel, 0, fileSize);
    }

    private long downloadClipSize(String filename) {
        setOut(new File(DIRECTORY + filename));
        String request = requestUrl + filename + RemotePath.SIZE;

        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(request);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));

            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }

            return Long.parseLong(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_STATUS;
        }
    }

    private List<String> getListFromServer() {
        try {
            StringBuilder result = new StringBuilder();

            URL url = new URL(RemotePath.SERVER + RemotePath.FILE + RemotePath.DOWNLOAD + RemotePath.LIST);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));

            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }

            return convertToList(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<String> convertToList(String listAsString) {
        List<String> list = Arrays.asList(listAsString.replace("[", "")
                .replace("]", "").replace("\"", "").split(","));
        return list;
    }
}
