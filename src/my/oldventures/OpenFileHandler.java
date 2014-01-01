/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.oldventures;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.NonWritableChannelException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KH
 */
public class OpenFileHandler {
    
    private FileChannel fc;
    private FileLock lock;

    public FileChannel getFc() {
        return fc;
    }

    public void setFc(FileChannel fc) {
        this.fc = fc;
    }

    public FileLock getLock() {
        return lock;
    }

    public void setLock(FileLock lock) {
        this.lock = lock;
    }
    
    public String getFileLock(String filename){
        try {
            RandomAccessFile raFile = new RandomAccessFile(filename,"rwd");
            fc = raFile.getChannel();
            lock = fc.tryLock();
        } catch (FileNotFoundException fnfe) {
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "File not found!", fnfe);
            return "File not found!";
        } catch(NonWritableChannelException nwce){
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "File cannot be locked!", nwce);
            return "File cannot be locked!";
        } catch (IOException ioe) {
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "IO exception", ioe);
            return "IO exception";
        } catch (Exception e) {
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "Other" , e);
            return "Error opening file: "+e.getMessage();
        }
        
        return "File loaded and ready!";
    }
}
