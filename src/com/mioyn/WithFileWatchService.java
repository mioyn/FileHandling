package com.mioyn;

import java.io.IOException;
import java.nio.file.*;

public class WithFileWatchService {
    public static void main(String[] args) throws IOException {

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {

            Path dir = Path.of("C:\\Users\\midhu\\work\\sample_data");

            WatchKey watchKey = dir.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            while (watchKey.reset()) {
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    Path file = dir.resolve((Path) event.context());
                    System.out.println(event.kind() + " " + file + "was last modified at " + file.toFile().lastModified());
                }
            }
        }
    }
}
