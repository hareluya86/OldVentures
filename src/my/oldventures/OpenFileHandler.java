/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.oldventures;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.NonWritableChannelException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KH
 */
public class OpenFileHandler {
    
    public int DEFAULT_LINE_SIZE;
    /**
     * DEFAULT_NEWLINE_SIZE: this is the platform-dependent default length of 
     * a newline character:
     * <p>
     * Windows  :   2 bytes - '\r' and '\n'
     * Unix     :   1 byte - '\n'
     * 
     */
    public final int DEFAULT_NEWLINE_SIZE;
    private RandomAccessFile raFile;
    private FileChannel fc;
    private FileLock lock;
    
    final Charset charset = Charset.forName("US-ASCII");
    
    private String rawSequences;

    public OpenFileHandler() {
        String os = System.getProperty("os.name");
        if(os.contains("Window")){
            DEFAULT_NEWLINE_SIZE = 2;
        } else if(os.contains("unix")){
            DEFAULT_NEWLINE_SIZE = 1;
        } else {
            throw new RuntimeException("OS not recognizeable! Please contact developer!");
        }
    }
    
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
            
            //Check byte length of file and set the default line size
            String line = raFile.readLine();
            DEFAULT_LINE_SIZE = line.getBytes().length + DEFAULT_NEWLINE_SIZE;
            
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
    
    
    public String generateSequenceRAF(int numOfSequence){
        int size = 0;
        int numOfLines = 0;
        String temp = "";
        int i = numOfSequence;
        List<Integer> pool = new ArrayList<>();
        
        rawSequences = new String(); //clears away the previous sequences
        try{
            
            //First, get a random sequence
            size = (int)raFile.length();
            /**
             * 1. Assume that all lines in the file are of the same length
             * 2. Always use ceiling function because a line can be a partial line
             * eg. a line without '\n' at the end
             */
            numOfLines = (int)Math.ceil(size/((double)DEFAULT_LINE_SIZE)); //must cast denom so that the product is a double for the ceiling function to work
            if(numOfSequence > numOfLines)
                return "Only "+numOfLines+" sequence(s) left in file!";
            
            while(i>0){
                int randLine = ((int)(Math.random()*numOfLines));
                int randPointer = randLine*DEFAULT_LINE_SIZE;
                if(pool.contains(randLine)){
                    continue;
                }

                raFile.seek(randPointer);
                String temp1 = raFile.readLine();
                System.out.println("Line size: "+temp1.getBytes().length);
                temp = temp.concat(temp1).concat("\n");

                i--;
                boolean add = pool.add(randLine);
            }
            
            this.setRawSequences(temp);
            System.out.println("Total file size: "+size);
            System.out.println("Total number of lines: "+numOfLines);
            
            //Second, remove from file
            Collections.sort(pool);
            removeSequencesFromFile(pool,fc,raFile,DEFAULT_LINE_SIZE);
        }
        catch (IOException ioe){
            Logger.getLogger(OpenFileHandler.class.getName()).log(Level.SEVERE, "IO exception", ioe);
            String message = ioe.getMessage();
            if(rawSequences != null && !rawSequences.isEmpty()){
                //If exception was thrown either while reading the file or writing the file, the entire sequence is 
                message = "Error but sequence partially generated: "+message;
            }
            else
                message = "Error: "+message;
            return message;
        }
        System.out.println(temp);
        return numOfSequence+" Sequence(s) generated!";
    }
    
    public void removeSequencesFromFile(List<Integer> positions, FileChannel fc, RandomAccessFile raf, int lineSize) throws IOException{
        long timeTaken;
        long fileSize;
        int numLines;
        int initialbBufferSize = lineSize;
        int numPositions = positions.size();
        try {
            fileSize = fc.size();
            double exactLines = fileSize/(double)lineSize; //must cast denom to double before an exact double value can be produced by division
            numLines = (int) Math.ceil(exactLines);
            Date startDate = new Date();
            MappedByteBuffer map = fc.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);
            
            
            //Byte counters/pointers
            int bufferStart = positions.get(0)*lineSize; //start straight from the first file position. this points to where the buffer starts
            int bufferSize = initialbBufferSize; //this will be increased later
            int bufferEnd = bufferStart + bufferSize; //tells us where the buffer ends
            int position = 0; //position of the discarded sequences in the given array, start from the 2nd one
            
            final int fileEnd = (int) (fileSize); //tells us the last point of the buffer
            
            //File line counters/pointers
            int bufferLineStart = bufferStart/lineSize;
            int bufferLineSize = bufferSize/lineSize;
            int bufferLineEnd = (bufferEnd/lineSize)-1; //Line tells us where the last line is
            
            while(bufferEnd <= fileEnd){
                
                
                //check if the buffer contains the positions of the sequences to be discarded
                //the first sequence will always increment the buffer for the 1st time
                //int sequenceLine = positions[position];
                while(position<positions.size() 
                        && positions.get(position) >= bufferLineStart 
                        && positions.get(position) <= bufferLineEnd){
                    position++; //go to the next sequence
                    bufferSize += lineSize; //increase bufferSize by 1 line
                    bufferLineEnd++;
                }
                bufferStart = bufferLineStart*lineSize;
                bufferEnd = bufferStart + bufferSize;
                
                int correctBufferSize = Math.min(bufferSize, fileEnd-bufferStart);
                
                map.position(bufferStart);
                byte[] byteBuffer = new byte[correctBufferSize];
                try{
                    map.get(byteBuffer, 0, correctBufferSize);
                } catch(java.nio.BufferUnderflowException buex){
                    System.out.println(bufferLineStart);
                }
                
                
                //Swap first line and last line
                int i = 0;
                int j = correctBufferSize-lineSize;
                while(j<correctBufferSize){ 
                    byte b = byteBuffer[i];
                    byteBuffer[i++] = byteBuffer[j];
                    byteBuffer[j++] = b;
                }
                map.position(bufferStart);
                map.put(byteBuffer, 0, correctBufferSize);
                
                bufferStart += lineSize;
                bufferLineStart = bufferStart/lineSize;
                bufferEnd = bufferStart + correctBufferSize;
                bufferLineEnd = (bufferEnd/lineSize)-1;
            }
            map = null;
            //System.gc();System.gc();System.gc();
            /*map = fc.map(FileChannel.MapMode.READ_WRITE, 0, 0);
            System.gc();System.gc();System.gc();
            try{
                fc.truncate(fileSize-(positions.size()*lineSize));
            } catch (IOException ioe) {
                map = fc.map(FileChannel.MapMode.READ_WRITE, 0, 0);
                System.gc();
                try{
                    fc.truncate(fileSize-(positions.size()*lineSize));
                } catch (IOException ioe2) {
                    throw ioe;
                }
            }*/
            boolean pass = false;
            while(!pass){
                try{
                    fc.truncate(fileSize-(positions.size()*lineSize));
                    pass = true;
                } catch (IOException ioe) {
                    System.gc();
                }
            }
            
            //raf.setLength(fileSize-(positions.size()*lineSize));
            
            Date endDate = new Date();
            timeTaken = endDate.getTime()-startDate.getTime();
            System.out.println("Time taken: "+timeTaken);

        } catch (IOException ioe){
            System.out.println(ioe.getMessage());
            throw ioe;
        }
    }
    
    public void removeSequencesFromFile2(List<Integer> positions, FileChannel fc, int lineSize, int maxBufferSize) throws IOException{
        long timeTaken;
        long fileSize;
        int numLines;
        int initialbBufferSize = 0;
        int numPositions = positions.size();
        if(numPositions <= 0) return; //no need to remove
        
        try {
            fileSize = fc.size();
            double exactLines = fileSize/(double)lineSize; //must cast denom to double before an exact double value can be produced by division
            numLines = (int) Math.ceil(exactLines);
            Date startDate = new Date();
            //MappedByteBuffer map = fc.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);
            
            //Byte counters/pointers
            int bufferStart = positions.get(0)*lineSize; //start straight from the first file position. this points to where the buffer starts
            int bufferSize = initialbBufferSize; //this will be increased later
            int bufferEnd = bufferStart; //tells us where the buffer ends
            int offSet = 0; //number of sequences found so far
            int nextPosition = positions.get(offSet);
            
            final int fileEnd = (int) (fileSize); //tells us the last point of the buffer
            /*
            if(positions.size() > 1){
                bufferEnd = (positions.get(1)-1)*lineSize;
                offSet++;
            }else{
                bufferEnd = fileEnd;
            }*/
            //File line counters/pointers
            int bufferLineStart = bufferStart/lineSize;
            int bufferLineSize = bufferSize/lineSize;
            int bufferLineEnd = bufferEnd/lineSize; //Line tells us where the last line is
            
            while(offSet<=positions.size()){ 
                
                byte[] bArray = new byte[bufferSize];
                MappedByteBuffer map = fc.map(FileChannel.MapMode.READ_WRITE, bufferStart, bufferSize);
                map.get(bArray);
                bArray = shift(bArray,offSet*lineSize);
                map.position(0);
                map.put(bArray);
                
                //Advance the bufferStart
                bufferStart = bufferEnd - (offSet)*lineSize;
                bufferLineStart = bufferStart/lineSize;
                
                if(offSet < positions.size()-1){
                    nextPosition = positions.get(++offSet); //get the next position
                    if(nextPosition*lineSize - bufferStart > maxBufferSize){
                        nextPosition = bufferStart + maxBufferSize; //use maxBufferSize, do not increment offSet
                        --offSet;//decrement offSet
                    }else{
                        //use nextPosition, increment offSet
                    }
                }
                else{ //last run of the loop
                    nextPosition = numLines - 1; //go to the last line
                    offSet++; //increment offSet so that the loop won't run again
                }
                
                //Advance the bufferEnd
                bufferLineEnd = nextPosition; //do not minus 1
                bufferEnd = bufferLineEnd*lineSize; 
                
                bufferSize = bufferEnd - bufferStart;
                
            }
            /*try{
                //fc.truncate(fileSize-(positions.size()*lineSize));
            } catch (IOException ioe) {
                //map = fc.map(FileChannel.MapMode.READ_WRITE, 0, 0);
                System.gc();
                try{
                    fc.truncate(fileSize-(positions.size()*lineSize));
                } catch (IOException ioe2) {
                    throw ioe;
                }
            }*/
            
            
            //raf.setLength(fileSize-(positions.size()*lineSize));
            
            Date endDate = new Date();
            timeTaken = endDate.getTime()-startDate.getTime();
            System.out.println("Time taken: "+timeTaken);

        } catch (IOException ioe){
            System.out.println(ioe.getMessage());
            throw ioe;
        }
    }
    
    public byte[] shift(byte[] bArray, int offset){
        int start = offset;
        int end = bArray.length;
        
        for(int i=start;i<end;i++){
            byte b = bArray[i];
            bArray[i-offset] = b;
        }
        return bArray;
    }
            
    
    
    //public int getLineSize()
    
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
