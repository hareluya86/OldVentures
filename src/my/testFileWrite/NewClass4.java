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
        int maxBufferSize = 51*lineSize; //must be twice the number of positions 
        //int[] positions = {0}; //TC 1
        //int[] positions = {0,1,5,10}; //TC 2
        //int[] positions = {1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35,37,39}; //TC 3
        //int[] positions = {0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38}; //TC 4
        //int[] positions = {24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39}; //TC 5
        //int[] positions = {7,8,9,10,11,12,13}; //TC 6
        long[] positions = {7,8,9,10,11,12,13,3614}; //TC 7
        
        List<Long> positionsList = new ArrayList<>();
        for(long i:positions){
            positionsList.add(i);
        }
        RandomAccessFile raf = new RandomAccessFile("C:\\Users\\KH\\Documents\\tc_7.txt","rwd");
        //RandomAccessFile raf = new RandomAccessFile("C:\\Users\\vincent.a.lee\\Desktop\\example.txt","rwd");
        FileChannel fc = raf.getChannel();
        handler.removeSequencesFromFile2(positionsList, fc, lineSize, maxBufferSize);
    }
}
