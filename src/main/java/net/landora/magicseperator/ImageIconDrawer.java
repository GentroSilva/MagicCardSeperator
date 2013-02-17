/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.magicseperator;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.Icon;

/**
 *
 * @author bdickie
 */
public class ImageIconDrawer implements SymbolDrawer {
    private Icon icon;

    public ImageIconDrawer(Icon icon) {
        this.icon = icon;
    }
    
    private double calculateScale(double height) {
        return height / ((double)icon.getIconHeight());
    }

    public double getWidth(Graphics2D g2d, double height) {
        return ((double)icon.getIconWidth()) * calculateScale(height);
    }

    public void draw(Graphics2D g2d, double height) {
        AffineTransform transform = g2d.getTransform();
        try {
            double scale = calculateScale(height);
            g2d.scale(scale, scale);
            icon.paintIcon(null, g2d, 0, 0);
            
        } finally {
            g2d.setTransform(transform);
        }
    }
    
    
}
