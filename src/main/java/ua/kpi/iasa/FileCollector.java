package ua.kpi.iasa;

import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.io.DirectoryScanner;

import java.util.ArrayList;
import java.util.List;

class FileCollector{

    private static List<String> absolutePaths = new ArrayList<String>();

    DirectoryScanner scanner = new DirectoryScanner();

    public String[] getFilesAbsolutePath(){
        return absolutePaths.toArray(new String[0]);
    }

    public void addFilesFromDir(String dir, int startIdx, int filesNumber){

        String[] patterns = new String[filesNumber];

        int endIdx = startIdx + filesNumber;

        for(int counter = 0; startIdx < endIdx; counter++, startIdx++){
            patterns[counter] = startIdx +  "_*.txt";
        }

        scanner.setBasedir(dir);
        scanner.setIncludes(patterns);
        scanner.setCaseSensitive(false);
        scanner.scan();
        String[] files = scanner.getIncludedFiles();

        for(String file : files){
            absolutePaths.add(dir + "\\" + file);
        }
    }
}
