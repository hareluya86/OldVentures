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
import org.apache.commons.io.input.NullInputStream;
import org.apache.commons.io.output.NullOutputStream;

/**
 *
 * @author vincent.a.lee
 */
public class NewClass3 {
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        
        RandomAccessFile raf = new RandomAccessFile("C:\\Users\\vincent.a.lee\\Desktop\\7 liner sample for Vincent.txt","rwd");
        FileChannel fc = raf.getChannel();
        long size = fc.size();
        System.out.println("Size was: "+size);
        MappedByteBuffer map = fc.map(FileChannel.MapMode.READ_WRITE, 0, size);
        
        //Unmapping
        byte[] temp = new byte[map.capacity()];
        NullInputStream nullInput = new NullInputStream(map.capacity());
        map = fc.map(FileChannel.MapMode.PRIVATE, 0, 0);
        System.gc();
        size--;
        fc.truncate(size);
        System.out.println("Size is now: "+size);
        
        
    }
}
