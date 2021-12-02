package console;

import org.fusesource.jansi.internal.Kernel32;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleUI {
    public static String getProblemPath() throws IOException {

        String problem;
        boolean isConsoleUICompatible = Kernel32.GetConsoleScreenBufferInfo(Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE), new Kernel32.CONSOLE_SCREEN_BUFFER_INFO()) != 0;

//        if (isConsoleUICompatible)
//            problem = advancedConsole();
//        else
        problem = classicConsole();

        return problem;
    }

    private static String classicConsole() throws IOException {
        String year = "";
        String exercise;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String response;

        // CLIENT
        do {
            System.out.print(ConsoleColor.CYAN);
            System.out.println("Select year: ");
            System.out.print(ConsoleColor.GREEN);
            System.out.println("1. 2021");
            System.out.println("2. 2016");
            System.out.println("3. 2015");
            response = br.readLine().trim();
            if (response.equals("1") || response.equals("2021"))
                year = "2021";
            if (response.equals("2") || response.equals("2016"))
                year = "2016";
            if (response.equals("3") || response.equals("2015"))
                year = "2015";

        } while (year.isEmpty());

        do {
            System.out.print(ConsoleColor.CYAN);
            System.out.println("Enter exercise: ");

            exercise = br.readLine().trim();

            System.out.print(ConsoleColor.WHITE_BOLD_BRIGHT);
        } while (exercise.isEmpty());

        // FILENAME

        return "yr" + year + ".exercises.Ex" + exercise;
    }

//    private static String advancedConsole() throws IOException {
//        Config userOptions = new Config();
//        AnsiConsole.systemInstall();
//        ConsolePrompt prompt = new ConsolePrompt();
//        PromptBuilder promptBuilder = new PromptBuilder();
//
//        // CLIENT
//        promptBuilder.createListPrompt()
//                .name("client").message("Selecciona un cliente")
//                .newItem("b2bsixva").text("b2bsixva").add()
//                .newItem("sixva").text("sixva").add()
//                .addPrompt();
//
//        HashMap<String, ? extends PromtResultItemIF> result = prompt.prompt(promptBuilder.build());
//        String response = ((ListResult) result.get("client")).getSelectedId();
//        if (response.equals("b2bsixva"))
//            userOptions.setClient(B2BSIXVA);
//        if (response.equals("sixva"))
//            userOptions.setClient(SIXVA);
//
//        // FILENAME
//        final List<String> localFiles = getLocalFiles();
//        switch (localFiles.size()) {
//            case 0:
//                throw new FileNotFoundException("Put XLSX in the same directory");
//            case 1:
//                userOptions.setFileName(localFiles.get(0));
//                break;
//            default:
//                promptBuilder = new PromptBuilder();
//                final ListPromptBuilder message = promptBuilder.createListPrompt()
//                        .name("file").message("Selecciona un fichero");
//                for (String fileName : localFiles) {
//                    message.newItem(fileName).text(fileName).add();
//                }
//                message.addPrompt();
//                result = prompt.prompt(promptBuilder.build());
//                response = ((ListResult) result.get("file")).getSelectedId();
//                userOptions.setFileName(response);
//        }
//        return userOptions;
//    }
}
