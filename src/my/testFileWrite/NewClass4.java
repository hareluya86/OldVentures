/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.testFileWrite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import my.oldventures.OpenFileHandler;

/**
 *
 * @author KH
 */
public class NewClass4 {
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        OpenFileHandler handler = new OpenFileHandler();
        
        int lineSize = 31;
        int maxBufferSize = 1024*1024*500;
        int[] positions = {0,1,5,13,29,31}; //sorted list of positions of the lines that are to be removed
        //int[] positions = {89,94,95,96,97,98};
        //int[] positions = {1,3,5,7,9,11,13};
        //int[] positions = {0,2,4,6,8,10,12};
        //int[] positions = {0};
        //int[] positions = {7,8,9,10,11,12,13};
        
        List<Integer> positionsList = new ArrayList<>();
        for(int i:positions){
            positionsList.add(i);
        }
        RandomAccessFile raf = new RandomAccessFile("C:\\Users\\KH\\Documents\\test3.txt","rwd");
        FileChannel fc = raf.getChannel();
        handler.removeSequencesFromFile2(positionsList, fc, lineSize, maxBufferSize);
    }
}
