package org.jpcl.fileop.file.rar;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 只能解压5.0以下的
 * @author Administrator
 */
public class WinRarFile {

    public final static String RAR_REGEXP = "^.+\\.rar$" ;
    public final static String SAVE_PATH = "/upload/";
    public final static String WIN_PATH = "\\";
    public final static String LINUX_PATH = "/";

    public void delUploadFile() {

    }

    /**
     * 上传文件进行保存 并解压成同名的文件保存在本地中
     * @param file
     * @param uploadFile
     * @param absolutePath
     * @throws Exception
     */
    public void saveUploadFile(MultipartFile file,
                               UploadFile uploadFile,
                               String absolutePath) throws Exception {
        // 获取文件存储路径（绝对路径）
        String path = absolutePath;
        String dirPath = uploadFile.getId();
        uploadFile.setDirPath(dirPath);
        uploadFile.setLocalAbsolutePath(path);
        //logger.info("开始进行文件解压...");

        // 获取原文件名 必须是rar文件才行
        String fileName = file.getOriginalFilename();
        if (!fileName.matches(RAR_REGEXP)) {
            throw new Exception(fileName + " - 文件类型不支持");
        }

        // 创建文件实例 文件不允许重名 重名其实会被覆盖
        File rarFile = new File(path, fileName);
        if (rarFile.exists()) {
            throw new Exception(fileName + " - 文件重名");
        }

        // 如果文件目录不存在，创建目录 目录基本是属于绝对存在的
        if (!rarFile.getParentFile().exists()) {
            rarFile.getParentFile().mkdirs();
        }

        // 写入文件
        try {
            file.transferTo(rarFile);
            // 写入成功 设置文件的本地存储的绝对路径 accessFilePath也就是用来测试的
            uploadFile.setAccessFilePath(SAVE_PATH + fileName);
            uploadFile.setSaveFilePath(rarFile.getPath());

            // 创建解压文件夹 文件夹名称为创意的id
            String outPutFileDir = getWinOrLinuxPath(path, dirPath);
            File dir = new File(outPutFileDir);
            if(!dir.exists() || !dir.isDirectory()) {
                // 文件解压目录创建ok
                dir.mkdirs();
                uploadFile.setParentDir(dir.getPath());
            }

            winRarFile(rarFile, outPutFileDir, uploadFile);
        } catch (IOException e) {
            //logger.error(e.toString());
            throw new Exception(fileName + " - 写操作失败");
        }
    }

    /**
     * 文件进行解压并保存起来
     * @param rarFile
     * @param outPutFileDir
     * @param uploadFile
     * @throws Exception
     * @throws IOException
     */
    private void winRarFile(File rarFile, String outPutFileDir, UploadFile uploadFile) throws Exception, IOException {
        // 获取文件存储路径（绝对路径）
        String fileName = rarFile.getName();
        FileOutputStream fos = null;
        Archive rarArchive = null;
        List<String> winRarPath = new ArrayList<>();
        try {
            //创建一个rar档案文件
            rarArchive = new Archive(rarFile);
            if (rarArchive == null) {
                return;
            }
            // 判断是否有加密 关闭资源
            if(rarArchive.isEncrypted()) {
                //logger.error(fileName + "该rar压缩包为加密资源，无法解压。");
                rarArchive.close();
                throw new Exception(fileName + "该rar压缩包为加密资源，无法解压。");
            }

            // 进行子文件的解压保存
            FileHeader fileHeader = rarArchive.nextFileHeader();
            while(fileHeader != null) {
                if (!fileHeader.isDirectory()) {

                    // 取得文件的解压文件名
                    String name = fileHeader.getFileNameW().trim();
                    if (name == null || name.trim().length() < 1) {
                        name = fileHeader.getFileNameString();
                    }

                    int hasDir = -1;
                    if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
                        hasDir = name.lastIndexOf(WIN_PATH);
                    } else {
                        hasDir = name.lastIndexOf(LINUX_PATH);
                    }
                    if (hasDir > 0) {
                        // 证明有次级目录
                        String childDir = getWinOrLinuxPath(outPutFileDir, name.substring(0, hasDir));
                        File childDirFile = new File(childDir);
                        if(!childDirFile.exists()) {
                            childDirFile.mkdirs();
                        }
                    }

                    //保存解压的文件
                    File saveFile = new File(outPutFileDir, name);
                    fos = new FileOutputStream(saveFile);
                    rarArchive.extractFile(fileHeader, fos);
                    winRarPath.add(saveFile.getPath());
                    //关闭资源
                    fos.close();
                    fos = null;
                }
                fileHeader = rarArchive.nextFileHeader();
            }
            //logger.info("解压缩完成, 文件信息展示: [{}]", uploadFile.toString());
        } catch (Exception e) {
            //logger.error(e.toString());
            throw new Exception(fileName + " 文件解压失败!");
        } finally {
            uploadFile.setWinRarFile(winRarPath);
            //关闭资源
            if (fos != null) {
                fos.close();
            }
            if (rarArchive != null) {
                rarArchive.close();
            }
        }
    }

    private String getWinOrLinuxPath(String dir, String fileName) {
        if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
            return dir + WIN_PATH + fileName;
        }
        return dir + LINUX_PATH + fileName;
    }
}
