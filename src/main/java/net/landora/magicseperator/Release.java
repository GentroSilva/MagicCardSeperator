/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.magicseperator;

/**
 *
 * @author bdickie
 */
public enum Release {
    Gatecrash("GTC"),
    ReturnToRavnica("RTR", "Return to Ravnica"),
    Magic2013("M13", "Magic 2013"),
    AvacynRestored("AVR", "Avacyn Restored");
    
    private String symbolCode;
    private String name;

    private Release(String symbolCode, String name) {
        this.symbolCode = symbolCode;
        this.name = name;
    }

    private Release(String symbolCode) {
        this.symbolCode = symbolCode;
        name = name();
    }

    public String getSymbolCode() {
        return symbolCode;
    }

    public String getName() {
        return name;
    }
    
}
