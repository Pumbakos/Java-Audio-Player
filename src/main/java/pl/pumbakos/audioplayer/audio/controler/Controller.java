package pl.pumbakos.audioplayer.audio.controler;

import pl.pumbakos.audioplayer.audio.player.SoundClip;

import java.util.Scanner;

public class Controller {
    protected final Scanner scanner = new Scanner(System.in);
    protected SoundClip clip;
    protected ClipQueue queue;
    protected volatile String command;
    protected volatile String lastCommand;

    public Controller(){}

    public void setProperties(ClipQueue queue, SoundClip clip){
        this.clip = clip;
        this.queue = queue;
    }

    public void menu() {
        System.out.println("\t\t\tMENU");
        System.out.println("Enter 'play' to start playing.");
        System.out.println("Enter 'stop' to stop playing.");
        System.out.println("Enter 'pause' to pause.");
        System.out.println("Enter 'resume' to resume.");
        System.out.println("Enter 'next' for next song.");
        System.out.println("Enter 'previous' for previous song.");
        System.out.println("Enter 'list' for listing all songs.");
        System.out.println("Enter 'folder -c' to change default folder.");
        System.out.println("Enter 'help' for help.");
//        System.out.println("Enter 'queue' to see current queue");

        System.out.println("\nHere are songs from your folder, choose one:");
        clip.list();
        clip.setCurrentSong();
    }

    public void help() {
        System.out.println("Incorrect value.");
        System.out.println("Enter 'help' for help");
    }

    public void cmd() {
        lastCommand = "play";
        do{
            lastCommand = command;
            System.out.print(">> ");
            command = scanner.nextLine().toLowerCase();
            switch (command) {
                case "", " ","  " -> {}

                case "play" -> {
                    clip.play();
                }
                case "stop", "exit" -> {
                    clip.stop();
                }
                case "next" -> {
                    clip.next();
                }
                case "previous" -> {
                    clip.previous();
                }
                case "resume" -> {
                    clip.resume();
                }
                case "pause" -> {
                    clip.pause();
                }
                case "list" -> {
                    clip.list();
                }
                case "folder -c" -> {
                    System.out.print("Enter path to folder: ");
                    clip.setDefaultFolder(new Scanner(System.in).nextLine());
                }
                case "queue" -> {
                    queue.createQueue();
                    queue.print();
                }
                default -> help();
            }
        }while(!command.equalsIgnoreCase("exit"));
    }

    public String getLastCommand(){
        return lastCommand;
    }
}
