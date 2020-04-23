/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.tts;

import Action.FSMAction;
import FSM.FSM;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author jimi
 */
public class TTSFsm {
    private String text;
    private String curState="STATE_MULAI";
    private String trace="";
    private List<String> result=new ArrayList();
    
    private final String specialCons="NKSGBYHPR";
    private String tmpRes="";
    
    public String getResult(){
        if(result.size()==0)return"";
        return this.result.toString();
    }
    
    public String getIteration(){
        return this.trace;
    }
    
    public TTSFsm(String text){
        this.text=text.toUpperCase();
        this.testFSM();
        System.out.println(this.result);
        System.out.println(this.trace);
    }
    private void addToResult(){
        if(!this.tmpRes.equals("")){
            this.result.add(this.tmpRes);
            this.tmpRes="";
        }
    }
    
    private FSM newFsm1(){
        this.addToResult();
        try {
            FSM f = new FSM("xmls/tingkat1.xml", new FSMAction() {
                @Override
                public boolean action(String curState, String message, String nextState, Object args) {
                    //javax.swing.JOptionPane.showMessageDialog(null, curState + ":" + message + " : " + nextState);
                    TTSFsm.this.trace+="\n"+curState + " => " + message + " : " + nextState;
                    TTSFsm.this.tmpRes+=message;
                    return true;
                }
            });
            //FSM f = new FSM("xmls/tingkat1.xml", null);
            return f;
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(TTSFsm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TTSFsm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TTSFsm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    private void testFSM() {
        FSM f=this.newFsm1();
        if(f==null)return;
        for(int i=0;i<this.text.length();i++){
            this.curState=f.getCurrentState();
            String trans=String.valueOf(this.text.charAt(i));
            if(f.ProcessFSM(trans)==null){
                f=this.newFsm1();
                this.tmpRes=String.valueOf(this.text.charAt(i));
                this.trace+="\n"+curState + " => " + this.tmpRes + " : STATE_MULAI (RESTART)";
            }
        }
        this.addToResult();
    }
}
