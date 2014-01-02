/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.oldventures;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.NonWritableChannelException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KH
 */
public class OpenFileHandler {
    
    private FileChannel fc;
    private FileLock lock;
    
    private OutputStream fOut;
    private InputStream  fIn;
    

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
    
    /*
     * Unnecessary. RandomAccessFile not suitable for text files.
     */
    public String getFileLock(String filepath){
        try {
            RandomAccessFile raFile = new RandomAccessFile(filepath,"rwd");
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
    
    public String getOutputStream(String filepath){
        File file = new File(filepath);
        if(!file.exists()) return "File not found!";
        
        try{
            fIn = new BufferedInputStream(new FileInputStream(file));
            //fOut = new BufferedOutputStream(new FileOutputStream(file));
            
        } catch (FileNotFoundException fnfe) {
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "File not found!", fnfe);
            return "File not found!";
        } catch (Exception e) {
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "Other" , e);
            return "Error opening file: "+e.getMessage();
        } 
        return "File loaded and ready!";
    }
    
    public String close(){
        try{
            if(fIn != null) fIn.close();
            if(fOut != null) fOut.close();
        } catch (IOException ioe) {
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "IO exception", ioe);
            return "Error closing file: "+ioe.getMessage();
        }   
        return "File connections closed.";
    }
    
    public String generateSequence(int numOfSequence){
        //1. Read the total number of lines first
        long numOfLines = 0L;
        String temp = "";
        try{
            BufferedReader bReader = new BufferedReader(new InputStreamReader(fIn));
            temp = bReader.readLine();
            System.out.println();
        } catch (IOException ioe){
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "IO exception", ioe);
            return "Error reading file: "+ioe.getMessage();
        }
        return temp;
    }
}
