/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.oldventures;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.NonWritableChannelException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KH
 */
public class OpenFileHandler {
    
    public final static int DEFAULT_LINE_SIZE = 31;
    private RandomAccessFile raFile;
    private FileChannel fc;
    private FileLock lock;
    
    final Charset charset = Charset.forName("US-ASCII");
    
    private String rawSequences;

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

    public String getRawSequences() {
        return rawSequences;
    }

    public void setRawSequences(String rawSequences) {
        this.rawSequences = rawSequences;
    }
    
    public String getFileLock(String filepath){
        try {
            raFile = new RandomAccessFile(filepath,"rwd");
            fc = raFile.getChannel();
            lock = fc.tryLock();
            
            //lock = fc.lock();//blocking call, so don't ever use!
        } catch (FileNotFoundException fnfe) {
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "File not found!", fnfe);
            return "File not found!";
        } catch(NonWritableChannelException nwce){
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "File cannot be locked!", nwce);
            return "File cannot be locked!";
        } catch (IOException ioe) {
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "IO exception", ioe);
            return "IO exception!";
        } catch (Exception e) {
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "Other" , e);
            return "Error: "+e.getMessage();
        }
        if(lock == null) {
            return "File locked by other programs!";
        }
        return "File loaded and ready.";
    }
    
    
    public String close(){
        try{
            if(lock != null){
                lock.close();
            } //release
        } catch (IOException ioe) {
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "IO exception", ioe);
            return "Error closing file: "+ioe.getMessage();
        }   
        return "File connections closed.";
    }
    
    
    public String generateSequenceRAF(){
        long size = 0L;
        long numOfLines = 0L;
        String temp = "";
        rawSequences = new String(); //clears away the previous sequences
        try{
            //First, get a random sequence
            
            size = raFile.length();
            /**
             * 1. Assume that all lines in the file are of the same length
             * 2. Always use ceiling function because a line can be a partial line
             * eg. a line without '\n' at the end
             */
            numOfLines = (long)Math.ceil(size/DEFAULT_LINE_SIZE); 
            
            long randLine = ((long)(Math.random()*numOfLines))*DEFAULT_LINE_SIZE;
            
            raFile.seek(randLine);
            temp = raFile.readLine();
            
            rawSequences = temp;
            System.out.println("Total file size: "+size);
            System.out.println("Total number of lines: "+numOfLines);
            System.out.println("Position: "+raFile.getFilePointer());
            
            //Second, remove from file
            
        }
        catch (IOException ioe){
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "IO exception", ioe);
            return "Error: "+ioe.getMessage();
        }
        System.out.println(temp);
        System.out.println(temp.getBytes().length);
        return "Sequence(s) generated!";
    }
    
    /* obsolete methods
    public String generateSequenceBR(int numOfSequence){
        //1. Read the total number of lines first
        long numOfLines = 0L;
        int lineSizeInBytes = 0;
        String temp = "";
        try{
            /*while((temp = bReader.readLine()) != null){
                System.out.println(temp);
                numOfLines++;
            }
            temp = bReader.readLine();
            if(temp != null)
                lineSizeInBytes = temp.getBytes().length;
        } catch (IOException ioe){
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "IO exception", ioe);
            return "Error reading file: "+ioe.getMessage();
        }
        System.out.println(lineSizeInBytes);
        return temp;
    }
    * 
    * public String getOutputStream(String filepath){
        File file = new File(filepath);
        if(!file.exists()) return "File not found!";
        
        try{
            //fIn = new BufferedInputStream(new FileInputStream(file));
            //bReader = new BufferedReader(new InputStreamReader(fIn));
            bReader = Files.newBufferedReader(file.toPath(),charset);
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
    */
}
