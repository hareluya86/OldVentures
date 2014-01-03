/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.oldventures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    
    public String changeSequencesFormat(List<List<String>> sequences, FORMAT format){
        String result = new String();
        for(List<String> s:sequences){
            result = result.concat(changeSequenceFormat(s,format)).concat("\n");
        }
        return result;
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
    public List<String> lineFromText(String text){
        String[] tempList = text.split("\t");
        List<String> result = new ArrayList<>();
        for(String t:tempList){
            //remove inverted commas
            result.add(t.replaceAll("\"", ""));
        }
        
        return result;
    }
    
    public List<List<String>> linesFromText(String text){
        String[] tempList = text.split("\n");
        ArrayList<List<String>> result = new ArrayList<>();
        for(String t:tempList){
            result.add(lineFromText(t));
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
    
    
}
