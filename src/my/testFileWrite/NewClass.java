/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.testFileWrite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KH
 */
public class NewClass {
    
    public static void main(String[] arg) throws FileNotFoundException, IOException{
        RandomAccessFile raf = new RandomAccessFile("C:\\Users\\KH\\Documents\\New Text Document.txt","rws");
        
        int lines = 80;
        int[] line = {11,12,13,14,15,10,10};
        for(int i=0; i<lines; i++){
            line[6] += 1;
            String temp = "";
            boolean first = true;
            for(int j=0; j<line.length; j++){
                if(!first) temp += "\t";
                temp += "\""+line[j]+"\"";
                first = false;
            }
            temp += System.getProperty("line.separator");
            
            raf.write(temp.getBytes("US-ASCII"));
            
        }
    }
}
