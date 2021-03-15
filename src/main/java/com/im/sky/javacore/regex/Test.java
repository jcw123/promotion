package com.im.sky.javacore.regex;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jiangchangwei
 * @date 2020-11-1 下午 11:18
 **/
public class Test {

    private static final String SRC_PATH = "D:/tmp/src_data.txt";

    private static final String DST_PATH = "D:/tmp/dst_data.txt";

    private static BufferedWriter bw;

    static {
        try {
             bw = new BufferedWriter(new FileWriter(DST_PATH));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<File> files = allFiles();
        String regex = "\\d{5}";
        Pattern pattern = Pattern.compile(regex);
        files.forEach(it -> process(it, pattern));
    }

    private static void process(File file, Pattern pattern) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len;
        try {
            FileInputStream fis = new FileInputStream(file);
            while((len = fis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            String data = bos.toString("utf-8");
            Matcher matcher = pattern.matcher(data);
            while(matcher.find()) {
                bw.write(matcher.group() + "\n");
            }
            bw.flush();
            fis.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static List<File> allFiles() {
        File file = new File(SRC_PATH);
        return allFiles(file);
    }

    private static List<File> allFiles(File file) {
        List<File> result = new ArrayList<>();
        if(file == null) {
            return result;
        }
        if(file.isFile()) {
            result.add(file);
        }else if(file.isDirectory()) {
            File[] files = file.listFiles();
            if(files != null) {
                for (File item : files) {
                    result.addAll(allFiles(item));
                }
            }
        }
        return result;
    }
}
