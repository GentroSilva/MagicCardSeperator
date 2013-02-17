/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.magicseperator;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 *
 * @author bdickie
 */
public class TextSymbolDrawer implements SymbolDrawer {
    private static final String FONT_NAME = "Planewalker";
    
    private String text;

    public TextSymbolDrawer(String text) {
        this.text = text;
    }

    public int fontSize(Graphics2D g2d, double height) {
        for(int i = 2; i < 100; i++) {
            
            FontMetrics fm = g2d.getFontMetrics(new Font(FONT_NAME, Font.PLAIN, i));
            if (fm.getHeight() > height)
                return i - 1;
        }
        return 100;
    }
    
    public double getWidth(Graphics2D g2d, double height) {
        int fontSize = fontSize(g2d, height);
        Font f = new Font(FONT_NAME, Font.PLAIN, fontSize);
        FontMetrics fm = g2d.getFontMetrics(f);
        return fm.stringWidth(text);
    }

    public void draw(Graphics2D g2d, double height) {
        AffineTransform transform = g2d.getTransform();
        try {
            int fontSize = fontSize(g2d, height);
            Font f = new Font(FONT_NAME, Font.PLAIN, fontSize);
            FontMetrics fm = g2d.getFontMetrics(f);
            
            g2d.translate(0, Math.min((height - fm.getAscent()) / 2, height - fm.getDescent()));

            g2d.setFont(f);
            g2d.setColor(Color.BLACK);

            g2d.drawString(text, 0, fm.getAscent());
        } finally {
            g2d.setTransform(transform);
        }
    }
    
    
}
