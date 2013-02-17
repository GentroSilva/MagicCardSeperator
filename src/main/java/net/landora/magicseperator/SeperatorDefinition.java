/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.magicseperator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bdickie
 */
public class SeperatorDefinition {
    private List<SymbolDrawer> leftSymbols;
    private List<SymbolDrawer> rightSymbols;

    public SeperatorDefinition() {
        leftSymbols = new ArrayList<SymbolDrawer>();
        rightSymbols = new ArrayList<SymbolDrawer>();
    }

    public List<SymbolDrawer> getLeftSymbols() {
        return leftSymbols;
    }

    public List<SymbolDrawer> getRightSymbols() {
        return rightSymbols;
    }

    public void addLeftSymbol(SymbolDrawer drawer) {
        leftSymbols.add(drawer);
    }

    public void addRightSymbol(SymbolDrawer drawer) {
        rightSymbols.add(drawer);
    }
    
    
}
