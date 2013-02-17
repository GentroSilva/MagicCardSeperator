/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.magicseperator;

/**
 *
 * @author bdickie
 */
public enum MagicColor {
    Black("B"),
    Blue("U"),
    Green("G"),
    Red("R"),
    White("W"),
    Colorless("X") {
        @Override
        public boolean includeInDefaultSets() {
            return true;
        }
        
    },
    BlackGreen("BG"),
    WhiteBlue("WU"),
    GreenWhite("GW"),
    BlackRed("BR"),
    BlueRed("UR");
    
    private String code;

    private MagicColor(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    
    public boolean isBaseColor() {
        switch(this) {
            case Red:
            case Black:
            case Blue:
            case Green:
            case White:
                return true;
                
            default:
                return false;
        }
    }
    
    public boolean includeInDefaultSets() {
        return isBaseColor();
    }
}
