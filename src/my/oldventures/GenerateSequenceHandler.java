/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.oldventures;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KH
 */
public class GenerateSequenceHandler {
    
    
    public String generateSequence(RandomAccessFile file, int numSequences){
        try {
            //Step 1. Get length of line and size of file in lines
            String headerRow = file.readLine();
            int lineSizeInBytes = headerRow.getBytes().length;
            long sizeOfFileInBytes = file.length();
            long sizeOfFileInLines = sizeOfFileInBytes/lineSizeInBytes;
            
            //Step 2. For each sequence, randomly choose a line number and store it in the result
            
            //Step 3. As soon as a sequence is generated, remove it from the file in the fastest possible way
        } catch (IOException ex) {
            Logger.getLogger(GenerateSequenceHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return "Not implemented yet!";
    }
}
