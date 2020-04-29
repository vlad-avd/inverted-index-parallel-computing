package ua.kpi.iasa;

import java.io.File;

public class Utils {

    private static final int FILES_TOTAL1 = 12500;
    private static final int FILES_TOTAL2 = 50000;
    private static final int FILES_NEED1 = 250;
    private static final int FILES_NEED2 = 1000;
    private static final int PARTS_NUMBER = 50;
    private static final int VARIANT = 23;

    public static File[] getFiles() {
        int startIdx1 = FILES_TOTAL1/PARTS_NUMBER * (VARIANT - 1);
        int startIdx2 = FILES_TOTAL2/PARTS_NUMBER * (VARIANT - 1);

        String dir1 = "C:\\Users\\avdie\\Desktop\\aclImdb\\test\\neg";
        String dir2 = "C:\\Users\\avdie\\Desktop\\aclImdb\\test\\pos";
        String dir3 = "C:\\Users\\avdie\\Desktop\\aclImdb\\train\\neg";
        String dir4 = "C:\\Users\\avdie\\Desktop\\aclImdb\\train\\pos";
        String dir5 = "C:\\Users\\avdie\\Desktop\\aclImdb\\train\\unsup";

        FileCollector fc = new FileCollector();
        fc.addFilesFromDir(dir1, startIdx1, FILES_NEED1);
        fc.addFilesFromDir(dir2, startIdx1, FILES_NEED1);
        fc.addFilesFromDir(dir3, startIdx1, FILES_NEED1);
        fc.addFilesFromDir(dir4, startIdx1, FILES_NEED1);
        fc.addFilesFromDir(dir5, startIdx2, FILES_NEED2);

        return fc.getFilesAbsolutePath();
    }
}

