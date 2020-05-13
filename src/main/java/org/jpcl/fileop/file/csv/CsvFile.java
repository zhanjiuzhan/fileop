package org.jpcl.fileop.file.csv;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Csv文件的操作
 * @author Administrator
 */
public class CsvFile {
    public static char separator = ',';

    /**
     * 读取CSV文件
     * @param filePath:全路径名
     */
    public static List<String[]> readCSV(String filePath) {
        CsvReader reader = null;
        List<String[]> dataList = new ArrayList<>();
        try {
            // 如果生产文件乱码，windows下用gbk，linux用UTF-8
            reader = new CsvReader(filePath, separator, Charset.forName("GBK"));

            // 读取表头
            reader.readHeaders();
            String[] headArray = reader.getHeaders();
            dataList.add(headArray);

            // 逐条读取记录，直至读完
            while (reader.readRecord()) {
                // 读一整行
                String[] row = reader.getValues();
                if (row.length == headArray.length) {
                    dataList.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                reader.close();
            }
        }
        return dataList;
    }

    /**
     * 生成CSV文件
     * @param dataList:数据集
     * @param filePath:全路径名
     */
    public static boolean createCSV(List<String[]> dataList, String filePath) throws Exception {
        boolean isSuccess = false;
        CsvWriter writer = null;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath, true);
            //如果生产文件乱码，windows下用gbk，linux用UTF-8
            writer = new CsvWriter(out, separator, Charset.forName("GBK"));
            for (String[] strs : dataList) {
                writer.writeRecord(strs);
            }
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return isSuccess;
    }
}
