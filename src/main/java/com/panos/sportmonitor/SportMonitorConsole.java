package com.panos.sportmonitor;

import com.panos.sportmonitor.stats.StatsParser;
import com.panos.sportmonitor.stats.StatsStore;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SportMonitorConsole {

    public static void main(String[] args) throws IOException {
        StatsStore store = new StatsStore(true);
        StatsParser parser = new StatsParser(store);

        File folder = new File("C:\\panos\\betting\\radar\\");
        String[] extensions = new String[] { "json" };
        List<File> files = (List<File>) FileUtils.listFiles(folder, extensions, false).stream().sorted().collect(Collectors.toList());
        System.out.println(String.format("Found %d files in folder %s", files.size(), folder.getAbsolutePath()));
        for (File file : files) {
            parser.parse(file);
        }

        store.print();
    }
}
