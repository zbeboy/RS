package top.zbeboy.rs.pages;


import org.apache.commons.fileupload.FileUploadException;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.HttpError;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.slf4j.Logger;
import top.zbeboy.rs.bean.FilesBean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Start page of application RS.
 */
public class Index
{
  @Inject
  private Logger logger;

  @Inject
  private AjaxResponseRenderer ajaxResponseRenderer;

  @Property
  @Inject
  @Symbol(SymbolConstants.TAPESTRY_VERSION)
  private String tapestryVersion;

  @InjectPage
  private About about;

  @Property
  private UploadedFile file;

  @InjectPage
  private FileDownload fileDownload;

  @Property
  private FilesBean filesBean;

  @Persist(PersistenceConstants.FLASH)
  @Property
  private String message;


  // Handle call with an unwanted context
  Object onActivate(EventContext eventContext)
  {
    return eventContext.getCount() > 0 ?
        new HttpError(404, "Resource not found") :
        null;
  }


  Object onActionFromLearnMore()
  {
    about.setLearn("LearnMore");

    return about;
  }

  Object onActionFromDownload(String fileName)
  {
    fileDownload.setFileName(fileName);
    fileDownload.setFilePath("file/location/");
    return fileDownload;
  }

  Object onUploadException(FileUploadException ex)
  {
    message = "Upload exception: " + ex.getMessage();

    return this;
  }

  public void onSuccess()
  {
    File path = new File("file/location/" );

    if(!path.exists()){
      path.mkdirs();
    }
    File copied = new File(path, file.getFileName());
    file.write(copied);
  }

  public Date getCurrentTime()
  {
    return new Date();
  }

  public List<FilesBean> getFiles(){
    File path = new File("file/location/" );
    List<FilesBean> list = new ArrayList<>();
    File[] files = path.listFiles();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    for(File f: files != null ? files : new File[0]){
        FilesBean filesBean = new FilesBean(f.getName(),sdf.format(new Date(f.lastModified())));
      list.add(filesBean);
    }
    return list;
  }
}
