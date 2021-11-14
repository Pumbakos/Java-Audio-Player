package pl.pumbakos.audioplayer.audio.controler;

import org.jetbrains.annotations.NotNull;
import pl.pumbakos.audioplayer.audio.exception.RegexNotMatchesException;
import pl.pumbakos.audioplayer.audio.resources.CliError;
import pl.pumbakos.audioplayer.audio.resources.Command;
import pl.pumbakos.audioplayer.audio.resources.CommandFlag;
import pl.pumbakos.audioplayer.audio.resources.Regex;

import java.util.*;

import static pl.pumbakos.audioplayer.audio.resources.Command.toCommand;
import static pl.pumbakos.audioplayer.audio.resources.Command.valueOf;

public class LocalController extends Controller {
    private static final byte SINGLE_COMMAND_PROCESSING = 0;
    private static final byte FLAGGED_COMMAND_PROCESSING = 1;
    private Stack<String> commandStack = new Stack<>();
    private volatile String primaryCommand;

    public LocalController() {
        super();
    }

//    public static void main(String[] args) {
//
//        LocalController lc = new LocalController();
//        lc.chooseExecuteMethod("play -l");
//    }

    @Override
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
    }

    @Override
    public void cmd() {
        menu();
        clip.list();
        clip.setCurrentSong();
        lastCommand = "play";
        do {
            lastCommand = command;
            System.out.print(">> ");
            command = scanner.nextLine().toLowerCase();
            chooseExecuteMethod(command);
        } while (true);
    }

    /**
     * Jeśli komenda jest jedna to zwraca ją, jeśli są dodane flagi to mapuje je i sprawdza ich poprawność
     * czy flaga jest poprawna dla komendy
     *
     * @param command command that you enter in CLI
     * @return temporary return
     */
    private int fragmentCommand(@NotNull String command) throws RegexNotMatchesException {
        String[] fragmentedCommand = command.split("\\s");

        if (fragmentedCommand.length == 1) {
            return SINGLE_COMMAND_PROCESSING;
        } else {
            for (String s : fragmentedCommand) {
                if (!s.matches(Regex.COMMAND_REGEX)) {
                    throw new RegexNotMatchesException(CliError.SYNTAX_ERROR.toString());
                }
            }

            List<String> commands = Arrays.asList(fragmentedCommand);

            if(!commandStack.empty()){
                Iterator<String> it = commandStack.iterator();
                while(it.hasNext()){
                    it.next();
                    it.remove();
                }
            }

            for (int i = fragmentedCommand.length -1; i > 0 ; --i) {
                commandStack.push(fragmentedCommand[i].replace("--", "").replace("-", ""));
            }

            this.primaryCommand = commands.get(0);

            return FLAGGED_COMMAND_PROCESSING;
        }
    }

    private boolean chooseExecuteMethod(String command) {
        int whatToDo;

        try {
            whatToDo = fragmentCommand(command);
        } catch (RegexNotMatchesException e) {
            e.printStackTrace();
            return false;
        }

        if (whatToDo == SINGLE_COMMAND_PROCESSING) {
            executeCommand(command);
        } else {
            try {
                executeCommand(this.commandStack);
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println(CliError.CANNOT_RESOLVE_GIVEN_PARAMETER);
            }
        }

        return true;
    }

    private void executeCommand(String command) {
        Command cmd = toCommand(command);
        switch (Objects.requireNonNull(cmd)) {
            case PLAY-> {
                clip.play();
            }
            case STOP, EXIT -> {
                clip.stop();
            }
            case NEXT -> {
                clip.next();
            }
            case PREVIOUS -> {
                clip.previous();
            }
            case RESUME -> {
                clip.resume();
            }
            case PAUSE -> {
                clip.pause();
            }
            case LIST -> {
                clip.list();
            }
            default -> help();
        }
    }

    /**
     * @throws NullPointerException when no Command match
     * @see Command
     */
    private void executeCommand(Stack<String> stack) throws NullPointerException {
        Command command = toCommand(this.primaryCommand);
        switch (Objects.requireNonNull(command)) {
            case PLAY: {
//                Iterator<String> iterator = stack.iterator();

                while (!stack.empty()) {
//                    String cmd = iterator.next();
                    String cmd = stack.pop();
                    System.out.println("STOS" + stack);

                    switch (cmd) {
                        case CommandFlag.CHOOSE, CommandFlag.Short.CHOOSE: {
                            int index;
                            try {
                                index = Integer.parseInt(stack.pop());
                                if (!clip.isPlaybackCompleted()) {
                                    clip.stop();
                                }

                                clip.getFileController().setCurrentSong(index + 1);
                            } catch (NumberFormatException e) {
                                System.err.println(CliError.WRONG_PARAMETER_ERROR);
                            }
                            clip.play();
                        }
                        break;

                        case CommandFlag.LOOP_OVER, CommandFlag.Short.LOOP_OVER: {
                            System.out.println("TOGGLED LOOP FLAG");
                            clip.setLoopOverClip(true);
                        }

                        default:
                            System.out.println(CliError.NO_FLAG_FOUND(cmd));
                            break;
                    }
                }
            }
            case STOP: {
                clip.stop();
            }
            break;
            case PAUSE: {
                clip.pause();
            }
            break;
            case RESUME: {
                clip.resume();
            }
            break;
            case NEXT: {
                clip.next();
            }
            break;
            case PREVIOUS: {
                clip.previous();
            }
            break;
            case LIST: {
                clip.list();
            }
            break;
            case FOLDER: {
                Iterator<String> iterator = stack.iterator();

                while (iterator.hasNext()) {
                    String cmd = iterator.next();
//                    System.out.println("STOS" + stack);

                    switch (cmd) {
                        case CommandFlag.CHOOSE, CommandFlag.Short.CHOOSE: {
                            String path = commandStack.pop();
                            System.out.println("FOLDER SET TO " + path);
                            clip.setDefaultFolder(path);
                        }

                        case CommandFlag.LOOP_OVER, CommandFlag.Short.LOOP_OVER: {
                            System.out.println("TOGGLED LOOP FLAG");
                            clip.setLoopOverFolder(true);
                        }

                        default:
                            System.out.println(CliError.NO_FLAG_FOUND);
                            break;
                    }
                    iterator.remove();
                }
            }
            break;
            case EXIT: {
                System.exit(0);
            }
            break;

            default:
                System.out.println(CliError.CANNOT_RESOLVE_GIVEN_PARAMETER);
                break;
        }
    }

    private Command getCommand(String command) {
        return valueOf(command);
    }
}
