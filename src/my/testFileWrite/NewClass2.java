/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.testFileWrite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

/**
 *
 * @author KH
 */
public class NewClass2 {

    public static void main(String[] arg){
        long timeTaken;
        int lineSize = 31;
        long fileSize;
        int numLines;
        int initialbBufferSize = lineSize;
        //int[] positions = {0,2,5,13,29,31}; //sorted list of positions of the lines that are to be removed
        //int[] positions = {89,94,95,96,97,98};
        int[] positions = {1,3,5,7,9,11,13};
        //int[] positions = {0};
        //int[] positions = {7,8,9,10,11,12,13};
        int numPositions = positions.length;
        try {
            RandomAccessFile raf = new RandomAccessFile("C:\\Users\\vincent.a.lee\\Desktop\\example.txt","rwd");
            FileChannel fc = raf.getChannel();
            fileSize = fc.size();//raf.length();
            double exactLines = fileSize/(double)lineSize; //must cast denom to double before an exact double value can be produced by division
            numLines = (int) Math.ceil(exactLines);
            Date startDate = new Date();
            MappedByteBuffer map = fc.map(FileChannel.MapMode.READ_WRITE, 0, lineSize*numLines);
            
            //Byte counters/pointers
            int bufferStart = positions[0]*lineSize; //start straight from the first file position. this points to where the buffer starts
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
                while(position<positions.length 
                        && positions[position] >= bufferLineStart 
                        && positions[position] <= bufferLineEnd){
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
            fc.close();
            fc = raf.getChannel();
            fc.truncate(fileSize-(positions.length*lineSize));
            Date endDate = new Date();
            timeTaken = endDate.getTime()-startDate.getTime();
            System.out.println("Time taken: "+timeTaken);

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        } catch (IOException ioe){
            System.out.println(ioe.getMessage());
            ioe.printStackTrace(System.out);
        }
    }
}
