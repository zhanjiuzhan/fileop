package org.jpcl.fileop.demo;

import org.jpcl.fileop.read.FileRead;
import org.jpcl.fileop.write.FileWrite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class Demo1 {
    public static void main(String[] args) {
        List<String> list = FileRead.readTextFileLine("C:\\Users" +
                "\\Administrator\\Desktop\\1.sql");

        List<String> t = new ArrayList<>(list.size());

        for (String str : list) {
            String tmp = str.substring("INSERT INTO `` VALUES (".length(),
                    str.lastIndexOf(");"));
            String[] a = tmp.split(",");

            t.add("UPDATE vip_base_user SET assign_user = " + a[1] + " WHERE " +
                    "yyuid = " + a[0] + ";");
        }

        FileWrite.writeTextFileByLine("C:\\Users\\Administrator\\Desktop\\2" +
                ".sql", t);
    }
}
