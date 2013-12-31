/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.oldventures;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;

/**
 *
 * @author vincent.a.lee
 */
public class SelectNumOfSequence {
    List<String> numOfSequence;
    
    public SelectNumOfSequence(){
        numOfSequence = new ArrayList<String>();
        int total = 10;
        for(int i=1; i<=total; i++){
            String line = i+" Sequence";
            if(i>1) line += "s";
            numOfSequence.add(line);
        }
        
    }
}
