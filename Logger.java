import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.io.FileWriter;

public class Logger{

  private static final String LOGS_DIR_PATH = "..\\logs\\";
  private static boolean created = false;
  private static File logFile;
  private static File errFile;

  public static void init(){
    if(created){
      return;
    }
    
    var logs_dir = new File(LOGS_DIR_PATH);
    if(!logs_dir.exists()){
      logs_dir.mkdir();
      Logger.log("Created log directory");
    }

    LocalDateTime LOCAL_TIME = LocalDateTime.now();

    var LOG_NAME = String.format("%d-%d-%d--%d-%d-%d", LOCAL_TIME.getYear(), LOCAL_TIME.getMonthValue(), LOCAL_TIME.getDayOfMonth(),
      LOCAL_TIME.getHour(), LOCAL_TIME.getMinute(), LOCAL_TIME.getSecond());
    
    var LOG_FILE_PATH = String.format("%s%s.txt", LOGS_DIR_PATH, LOG_NAME); 
    var ERR_FILE_PATH = String.format("%s%s_errors.txt", LOGS_DIR_PATH, LOG_NAME);

    logFile = new File(LOG_FILE_PATH);
    errFile = new File(ERR_FILE_PATH);

    created = true;
  }

  private static void log(File f, String msg){
    if(!created){
      System.out.println("INITALIZE SCANNER FIRST! ");
      return;
    }

    if(!f.exists()){
      try{
        f.createNewFile();
        log("NEW LOG FILE CREATED");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    }
    
    LocalDateTime LOCAL_TIME = LocalDateTime.now();

    var LOG_TIME = String.format("[%d-%d--%d-%d-%d] = ", LOCAL_TIME.getMonthValue(), LOCAL_TIME.getDayOfMonth(),
      LOCAL_TIME.getHour(), LOCAL_TIME.getMinute(), LOCAL_TIME.getSecond());

    try {
      FileWriter wr = new FileWriter(f, true);
      wr.append("\n" + LOG_TIME + " " + msg);
      wr.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static void log(String msg){
    log(logFile, msg);
  }
  
  public static void err(String msg){
    log(errFile, msg);
  }

  public static void err(Exception e){
    err(e.getMessage());
  }


}
