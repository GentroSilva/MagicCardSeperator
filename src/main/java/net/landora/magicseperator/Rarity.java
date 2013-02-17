/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.magicseperator;

/**
 *
 * @author bdickie
 */
public enum Rarity {
    Common("C"),
    Uncommon("U"),
    Rare("R"),
    Mystic("M");
    
    private String code;

    private Rarity(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
