package top.zbeboy.rs.bean;

/**
 * Created by zbeboy on 2016/11/15.
 */
public class FilesBean {

    private String fileName;
    private String modifyTime;

    public FilesBean(String fileName, String modifyTime) {
        this.fileName = fileName;
        this.modifyTime = modifyTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}
