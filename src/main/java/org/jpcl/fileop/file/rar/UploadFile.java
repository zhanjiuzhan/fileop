package org.jpcl.fileop.file.rar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class UploadFile {

    private String id;

    /**
     * 文件相对于本地服务器保存的绝对地址
     */
    private String localAbsolutePath;

    private String dirPath;

    /**
     * rar 文件在服务器上保存的绝对地址
     */
    private String saveFilePath;

    /**
     * 在浏览器中可以被访问的地址
     */
    private String accessFilePath;

    /**
     * 文件在服务器上的解压目录
     */
    private String parentDir;

    /**
     * 解压后子文件的绝对地址
     */
    private List<String> winRarFile = new ArrayList<>();

    /**
     * 解压后子文件的绝对地址
     */
    private List<String> cdnWinRarFile = new ArrayList<>();

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getSaveFilePath() {
        return saveFilePath;
    }

    public void setSaveFilePath(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    public String getAccessFilePath() {
        return accessFilePath;
    }

    public void setAccessFilePath(String accessFilePath) {
        this.accessFilePath = accessFilePath;
    }

    public String getParentDir() {
        return parentDir;
    }

    public void setParentDir(String parentDir) {
        this.parentDir = parentDir;
    }

    public List<String> getWinRarFile() {
        return winRarFile;
    }

    public void setWinRarFile(List<String> winRarFile) {
        this.winRarFile = winRarFile;
    }

    public List<String> getCdnWinRarFile() {
        return cdnWinRarFile;
    }

    public void setCdnWinRarFile(List<String> cdnWinRarFile) {
        this.cdnWinRarFile = cdnWinRarFile;
    }

    public String getLocalAbsolutePath() {
        return localAbsolutePath;
    }

    public void setLocalAbsolutePath(String localAbsolutePath) {
        this.localAbsolutePath = localAbsolutePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UploadFile{" +
                "id='" + id + '\'' +
                ", localAbsolutePath='" + localAbsolutePath + '\'' +
                ", dirPath='" + dirPath + '\'' +
                ", saveFilePath='" + saveFilePath + '\'' +
                ", accessFilePath='" + accessFilePath + '\'' +
                ", parentDir='" + parentDir + '\'' +
                ", winRarFile=" + winRarFile +
                ", cdnWinRarFile=" + cdnWinRarFile +
                '}';
    }
}
