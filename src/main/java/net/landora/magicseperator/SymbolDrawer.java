/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.magicseperator;

import java.awt.Graphics2D;

/**
 *
 * @author bdickie
 */
public interface SymbolDrawer {
    public double getWidth(Graphics2D g2d, double height);
    public void draw(Graphics2D g2d, double height);
}
