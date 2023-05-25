import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class PathFinder {

    private static Path filepath;

    /**
     * The most useful method ever, I would suggest a link, so that nobody has to
     * worry about file paths ever again
     * 
     * @param filename
     * @return
     */
    public static Path getFilePathForFile(String filename) {
        try {
            filepath = Files.walk(Paths.get("."))
                    .collect(Collectors.toList()).stream()
                    .filter(file -> !Files.isDirectory(file) &&
                            file.getFileName().toString().startsWith(filename))
                    .findFirst().get();
        } catch (IOException exception) {
            System.out.println("File Not Found!");
        }
        return filepath;
    }
}