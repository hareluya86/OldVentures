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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KH
 */
public class OpenFileHandler {
    
    private RandomAccessFile raFile;
    private FileChannel fc;
    private FileLock lock;
    
    private OutputStream fOut; //won't be used
    private InputStream  fIn; //won't be used
    
    final Charset charset = Charset.forName("US-ASCII");
    
    BufferedReader bReader; //won't be used
    
    

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
        if(lock == null) 
            return "File locked by other programs!";
        return "File loaded and ready.";
    }
    
    public String getOutputStream(String filepath){
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
    
    public String close(){
        try{
            if(fIn != null) fIn.close();
            if(fOut != null) fOut.close();
            if(lock != null) lock.release();
        } catch (IOException ioe) {
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "IO exception", ioe);
            return "Error closing file: "+ioe.getMessage();
        }   
        return "File connections closed.";
    }
    
    public String generateSequenceBR(int numOfSequence){
        //1. Read the total number of lines first
        long numOfLines = 0L;
        int lineSizeInBytes = 0;
        String temp = "";
        try{
            /*while((temp = bReader.readLine()) != null){
                System.out.println(temp);
                numOfLines++;
            }*/
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
    
    public String generateSequenceRAF(int numOfSequence){
        long size = 0L;
        long numOfLines = 0L;
        String temp = "";
        try{
            //First, get a random sequence
            size = raFile.length();
            numOfLines = (size)/31; // assume that all lines in the file are of the same length
            
            long randLine = ((long)(Math.random()*numOfLines))*31;
            
            raFile.seek(randLine);
            temp = raFile.readLine();
            
            String[] result = parseLine(temp);
            for(String r:result){
                System.out.println(r);
            }
            System.out.println("Total number of lines: "+numOfLines);
            System.out.println("Position: "+raFile.getFilePointer());
            
            //Second, 
        }
        catch (IOException ioe){
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "IO exception", ioe);
            return "Error: "+ioe.getMessage();
        }
        System.out.println(temp);
        System.out.println(temp.getBytes().length);
        return temp;
    }
    
    public String[] parseLine(String line){
        String[] result = line.split("\"");
        return result;
    }
}
