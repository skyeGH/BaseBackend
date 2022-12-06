package com.skye.libra.dto;

import com.skye.libra.model.GhMenu;

import java.io.Serializable;
import java.util.Date;

/**
 * 传输类
 * TODO
 * @author gaih
 * @since 2022-12-06 18:30:19
 */
public class GhMenuPageDto implements Serializable {

    private static final long serialVersionUID = 422320149912521966L;
    protected GhMenu ghMenu;
    protected long size;
    protected long current;
   
    public GhMenu getGhMenu(){
        return ghMenu;
    }
    public void setGhMenu(GhMenu ghMenu){
        this.ghMenu = ghMenu;
    }
    
    public long getSize(){
        return size;
    }
    public void setSize(long size){
        this.size = size;
    }
    
        public long getCurrent(){
        return current;
    }
    public void setCurrent(long current){
        this.current = current;
    }
   
}
