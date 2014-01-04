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
public class NewClass1 {
    
    public static void main(String[] arg){
        long timeTaken;
        int lineSize = 31;
        long fileSize;
        int numLines;
        int initialbBufferSize = lineSize*2;
        //int[] positions = {0,2,5,13,29,31}; //sorted list of positions of the lines that are to be removed
        int[] positions = {93,94,95,96,97,98};
        int numPositions = positions.length;
        try {
            RandomAccessFile raf = new RandomAccessFile("C:\\Users\\KH\\Documents\\test2 copy.txt","rwd");
            FileChannel fc = raf.getChannel();
            fileSize = raf.length();
            double exactLines = fileSize/(double)lineSize; //must cast denom to double before an exact double value can be produced by division
            numLines = (int) Math.ceil(exactLines);
            Date startDate = new Date();
            MappedByteBuffer map = fc.map(FileChannel.MapMode.READ_WRITE, 0, lineSize*numLines);
            
            //for each position to be removed, 
            //for(int x=0;x<positions.length;x++){
                //int nextPosition = positions[x];
                int position = 0;
                byte[] bArray = new byte[initialbBufferSize];
                int bufferSize = initialbBufferSize;
                int start = positions[0]*lineSize;
                int end = map.limit()-initialbBufferSize;
                map.position(start);
                //Loops through the entire map with buffer size = initialBufferSize
                //We need to increase the bufferSize if map contains the position of the next line to be removed
                //But, if the next line to be removed is the last line, stop.
                while(map.position()<=end){
                    //Check if the last line of the bArray is the next line to be removed
                    int linePosition = start/lineSize;
                    int bufferLineSize = bufferSize/lineSize;
                    int lastLineOfArray = linePosition+bufferLineSize-1;
                    if(position+1 < numPositions && lastLineOfArray == positions[position+1]){
                        //if yes, increase buffer size by 1 lineSize and decrement buffer end position by 1 lineSize
                        bufferSize += lineSize;
                        bArray  = new byte[bufferSize];
                        end -= lineSize;
                        if(map.position()>end) 
                            break;
                        if(map.position()==end)
                            System.out.println();
                        position++;
                    }
                    try{
                        map.get(bArray, 0, bufferSize);
                    }catch (java.nio.BufferOverflowException bex){
                        System.out.println("Position: "+map.position());
                    }catch (java.nio.BufferUnderflowException undx){
                        System.out.println("Position: "+map.position());
                    }catch(java.lang.IndexOutOfBoundsException iobex){
                        System.out.println("Position: "+map.position());
                    }
                    
                    //Swap first line and last line
                    int i = 0;
                    int j = bufferSize-lineSize;
                    while(j<bufferSize){ //Don't swap the last byte, as it is assumed to be always \n but can be empty as well
                        byte b = bArray[i];
                        bArray[i++] = bArray[j];
                        bArray[j++] = b;
                    }
                    map.position(start);//each time map.get() gets called, the pointer increments!
                    try{
                        map.put(bArray, 0, bufferSize);
                    } 
                    catch (java.nio.BufferOverflowException bex){
                        
                        System.out.println("Position: "+map.position());
                    }
                    start += i;
                    map.position(start);

                    //String fileLine = new String(bArray);
                    //System.out.println("map position: "+map.position());
                    //System.out.println(fileLine);
                }
            //}
            Date endDate = new Date();
            timeTaken = endDate.getTime()-startDate.getTime();
            System.out.println("Time taken: "+timeTaken);

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }
}
