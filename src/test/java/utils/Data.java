package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Data {
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }
}
