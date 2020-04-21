package javaSimple3d;

public class Input {
  private Keyboard m_keyboard;
  
  public Input() {
    this.m_keyboard = new Keyboard();
  }
  
  public Keyboard getKeyboard() {return this.m_keyboard;}
}
