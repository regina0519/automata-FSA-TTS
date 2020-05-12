/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.tts;

/**
 *
 * @author regina
 */
public class SyllableWrapper {
    private String chars;
    private String pattern;

    public SyllableWrapper(String chars){
        this.chars=chars;
        this.pattern=this.getPattern(this.chars);
    }
    private String getPattern(String param){
        if(param.replaceAll(" ", "").equals(""))return "SPASI";
        if(param.length()==1){
            if(this.isVowel(param.charAt(0))){
                return "V";
            }else return "K";
        }
        String ret="";
        for(int i=0;i<param.length();i++){
            if(this.isVowel(param.charAt(i))){
                ret+="V";
            }else ret+="K";
        }
        if(!ret.equals("KV") && !ret.equals("KKV") && !ret.equals("KK"))ret="UNKNOWN";
        return ret;
    }
    private boolean isVowel(char c){
        String vowel="AIUEO";
        for(int i=0;i<vowel.length();i++){
            if(vowel.charAt(i)==c){
                return true;
            }
        }
        return false;
    }
    public String getChars() {
        return chars;
    }
    public String getPattern() {
        return pattern;
    }
    
}
