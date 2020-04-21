package javaSimple3d;

import java.util.Vector;

public class Keyboard {
  private Vector<Key> m_keys;
  
  public Keyboard() {
    this.m_keys = new Vector<Key>();
  }
  
  public void add(int code) {
    Key key = this.find(code);
    if (key==null) {
      key = new Key();
      key.setCode(code);
      this.m_keys.add(key);
    }
  }
  
  public Key find(int code) {
    Key key;
    for(int i=0;i<this.m_keys.size();i++) {
      key = this.m_keys.elementAt(i);
      if (key.getCode()==code) {
        return key;
      }
    }
    return null;
  }
  
  public void setState(int code, boolean state) {
    Key key = this.find(code);
    if (key!=null) {
      key.setState(state);
    }
  }
  
  public boolean getState(int code) {
    Key key = this.find(code);
    if (key!=null) {
      return key.getState();
    }
    return false;
  }

  public class Key {
    private int m_code;
    private boolean m_state;
    
    public Key() {
      this.setCode(-1);
      this.setState(false);
    }
    
    public void setCode(int i) {this.m_code=i;}
    public int getCode() {return this.m_code;}
    public void setState(boolean b) {this.m_state=b;}
    public boolean getState() {return this.m_state;}
  }
}
