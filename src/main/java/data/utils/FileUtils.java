package data.utils;

import lombok.NonNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public class FileUtils {

    public static final Charset STANDARD_CHARSET = StandardCharsets.UTF_8;

    public FileUtils() {
    }

    // Creator file methods.

    public synchronized File createNewFile(String filePath) {
        File file;

        try {
            file = new File(filePath);

            if (!file.exists()) {
                Path parentPath = file.toPath().getParent();
                if (parentPath != null) {
                    parentPath.toFile().mkdirs();
                }

                file.createNewFile();
            }

        } catch (IOException e) {
            throw new NullPointerException("The file was unable to create.");
        }

        return file;
    }

    // Reader file methods.

    public synchronized List<String> readFileLines(final String filePath) throws IOException {
        Path path = getPathOf(filePath);
        return readFileLines(path);
    }

    public synchronized List<String> readFileLines(final Path filePath) throws IOException {
        return Files.readAllLines(filePath, STANDARD_CHARSET);
    }

    public synchronized String readFileBody(final String filePath) throws IOException {
        Path path = getPathOf(filePath);
        return readFileBody(path);
    }

    public synchronized String readFileBody(final Path filePath) throws IOException {
        return Files.readString(filePath, STANDARD_CHARSET);
    }

    /**
     * Build a path of the provided string text.
     */
    public Path getPathOf(String path) {
        return Paths.get(path);
    }

    // Writer file methods.

    /**
     * Write data on file the provided lines to the body. If the file already exists, override the data.
     */
    public synchronized void writeOnFile(Path fileGeneratedPath, @NonNull String... bodyFile) {
        NullableUtils.executeNonNull(fileGeneratedPath,
                filePath -> writeOnFile(fileGeneratedPath, List.of(bodyFile), StandardOpenOption.WRITE));
    }

    /**
     * Append data on file the provided lines to the body.
     */
    public synchronized void appendOnFile(Path fileGeneratedPath, @NonNull String... bodyLines) {
        NullableUtils.executeNonNull(fileGeneratedPath,
                path -> writeOnFile(path, List.of(bodyLines), StandardOpenOption.APPEND));
    }

    /**
     * Write data inside the file with the provided {@code path}.
     * Also use
     */
    private void writeOnFile(Path path, @NonNull List<String> body, final OpenOption... openOptions) {
        try (BufferedWriter fileWriter = Files.newBufferedWriter(path, STANDARD_CHARSET, openOptions)) {
            for (String str : body) {
                fileWriter.write(str);
            }
        } catch (IOException ignored) {
        }
    }

}
