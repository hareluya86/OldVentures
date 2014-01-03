/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.oldventures;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KH
 */
public class SequenceFormatHandler {
    
    public enum FORMAT{
        CSV,
        TEXT,
        EXCEL
    }
    
    public String changeSequenceFormat(List<String> sequence, FORMAT format){
        switch(format){
            case CSV    :   return toCSV(sequence);
            case TEXT   :   return toText(sequence);
            case EXCEL  :   return toExcel(sequence);
        }
        return "";
    }
    
    /**
     * Gets a "clean" list of numbers from the rawSequence
     * <p>
     * The raw sequence is assumed to be separated by the tab character and each
     * number is covered in double-inverted commas. Eg.
     * <p>
     *  " 1"    "18"    "23"    "30"    "40"    "41"
     * 
     * @param  text  a String object that is in the raw format mentioned
     * 
     */
    public List<String> fromText(String text){
        String[] tempList = text.split("\t");
        List<String> result = new ArrayList<String>();
        for(String t:tempList){
            //remove inverted commas
            result.add(t.replaceAll("\"", ""));
        }
        
        return result;
    }
    
    public String toCSV(List<String> sequence){
        String result = "";
        boolean first = true;
        for(String s:sequence){
            if(!first){
                result += ",";
            }
            first = false;
            result += s;
        }
        return result;
    }
    
    public String toText(List<String> sequence){
        String result = "";
        boolean first = true;
        for(String s:sequence){
            if(!first){
                result += '\t';
            }
            first = false;
            result += "\""+s+"\"";
        }
        return result;
    }
    
    public String toExcel(List<String> sequence){
        String textFormat = toText(sequence);
        textFormat = textFormat.replace("\"", "");
        
        return textFormat;
    }
    
    public List<String> fromCSV(String csv){
        String[] baseFormat = csv.split(",");
        List<String> result = new ArrayList<>();
        result.addAll(Arrays.asList(baseFormat));
        return result;
    }
    
    public List<String> fromExcel(String excel){
        String[] baseFormat = excel.split("\t");
        List<String> result = new ArrayList<>();
        result.addAll(Arrays.asList(baseFormat));
        return result;
    }
}
