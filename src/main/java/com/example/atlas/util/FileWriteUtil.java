package com.example.atlas.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriteUtil {
    private FileWriter fw;
    private PrintWriter pw;

    private static FileWriteUtil fileWrite = null;

    private FileWriteUtil(String path) throws IOException {
        this.fw = new FileWriter(new File(path), true);
        this.pw = new PrintWriter(fw);
    }

    public static FileWriteUtil getFileWrite(String path) throws IOException {
        if (fileWrite == null) {
            fileWrite = new FileWriteUtil(path);
        }
        return fileWrite;
    }

    public void writeFile(String data) throws IOException {
        pw.println(data);
        pw.flush();
        fw.flush();
    }

    public void closeIO() throws IOException {
        pw.close();
        fw.close();
    }

    public static String getRootPath() {
        File directory = new File("");//参数为空
        return directory.getAbsolutePath();
    }
}
