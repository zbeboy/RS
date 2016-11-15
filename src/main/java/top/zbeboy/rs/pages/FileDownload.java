package top.zbeboy.rs.pages;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

/**
 * Created by zbeboy on 2016/11/15.
 */
public class FileDownload implements StreamResponse{
    /**
     *  下载文件路径
     */
    private String filePath = null;

    /**
     *  下载文件主题
     */
    private String fileName = null;

    /**
     *  构造方法
     *
     * @param fileName 生成文件名主题
     * @oaran filePath 下载文件路径
     */
    public FileDownload(String fileName, String filePath) {

        this.fileName = fileName;
        this.filePath = filePath;
    }

    /**
     * 无参构造
     */
    public FileDownload() {

    }

    /**
     *  获取返回流
     */
    public InputStream getStream() throws IOException {

        // 待下载文件
        File downFile = new File(filePath,fileName);

        ByteArrayOutputStream baos = null;
        if (filePath != null && downFile.exists()) {

            // 生成返回的文件流
            FileInputStream fis = new FileInputStream(downFile);

            BufferedInputStream bis = new BufferedInputStream(fis);

            baos = new ByteArrayOutputStream();

            byte[] buf = new byte[1024];

            // 读取bis流
            int byt = bis.read(buf, 0, 1024);

            while(byt != -1){
                baos.write(buf, 0, byt);
                byt = bis.read(buf, 0, 1024);
            }

            bis.close();
        }

        return new ByteArrayInputStream(baos != null ? baos.toByteArray() : new byte[0]);
    }

    /**
     *  设置返回流的 ContentType
     */
    public String getContentType() {

        return "application/octet-stream";
    }

    /**
     * 返回流相关设置
     */
    public void prepareResponse(Response response) {
        try {
            response.setHeader("content-disposition", "attachment; filename="
                    + new String((fileName).getBytes(), "ISO-8859-1"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the fileName
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFilePath(String fileName) {
        this.filePath = fileName;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
