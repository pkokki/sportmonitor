package com.panos.sportmonitor;

import com.panos.sportmonitor.stats.StatsParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SportMonitorConsole {
    public static void main(String[] args) throws IOException {
        StatsParser parser = new StatsParser();

        File folder = new File("C:\\panos\\betting\\radar\\");
        String[] extensions = new String[] { "json" };
        List<File> files = (List<File>) FileUtils.listFiles(folder, extensions, false);
        System.out.println(String.format("Found %d files in folder %s", files.size(), folder.getAbsolutePath()));
        for (File file : files) {
            parser.parse(file);
        }
    }
}
