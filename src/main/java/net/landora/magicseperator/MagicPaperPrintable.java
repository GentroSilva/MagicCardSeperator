/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.magicseperator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.net.MalformedURLException;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author bdickie
 */
public class MagicPaperPrintable implements Printable {

    private List<SeperatorDefinition> seperators;
    
    private static final double DPI = 72d;
    
    private static final double dividerWidth = (3 + 11d/16d) * DPI;
    private static final double dividerHeight = 2.75 * DPI;
    private static final double headerArea = 0.20 * DPI;
    private static final double headerTopMargin = 0.025 * DPI;
    
    
    private static final double topMargin = 0.5d * DPI;
    private static final double bottomMargin = 0.5d * DPI;
    private static final double rightMargin = 0.5d * DPI;
    private static final double leftMargin = rightMargin;

    public MagicPaperPrintable(List<SeperatorDefinition> seperators) {
        this.seperators = seperators;
    }
    
    
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        
        Graphics2D g2d = (Graphics2D)graphics;
        ImageIcon icon;
        Shape clip = g2d.getClip();
        AffineTransform savedTransform = g2d.getTransform();
        try {
            double width = pageFormat.getWidth() - (rightMargin + leftMargin);
            double height = pageFormat.getHeight() - (bottomMargin + topMargin);
            
            g2d.translate(leftMargin, topMargin);

            int columns = (int)Math.floor(width / dividerWidth);
            int rows = (int)Math.floor(height / dividerHeight);

            double topMargin = (height - rows * dividerHeight) / 2d;
            double sideMargin = (width - columns * dividerWidth) / 2d;

            g2d.setColor(Color.LIGHT_GRAY);
            g2d.setStroke(new BasicStroke(0.25f));

            for(int c = 0; c <= columns; c++) {
                Line2D line = new Line2D.Double(
                        sideMargin + c * dividerWidth, 0, 
                        sideMargin + c * dividerWidth, height);
                g2d.draw(line);
            }

            for(int r = 0; r <= rows; r++) {
                Line2D line = new Line2D.Double(
                        0, topMargin + r * dividerHeight,
                        width, topMargin + r * dividerHeight);
                g2d.draw(line);
            }

            g2d.setColor(Color.BLACK);

            int cardsPerpage = columns * rows;
            
            for(int c = 0; c < columns; c++) {
                for(int r = 0; r < rows; r++) {
                    int index = (r + (c * rows)) + (pageIndex) * cardsPerpage;
                    if (index >= seperators.size())
                        continue;

                    SeperatorDefinition def = seperators.get(index);

                    Graphics2D part = (Graphics2D)g2d.create();

                    part.translate(
                            sideMargin + c * dividerWidth, 
                            topMargin + r * dividerHeight);
                    part.clip(new Rectangle2D.Double(0, 0, dividerWidth, dividerHeight));

                    drawCard(def, part);

                    part.dispose();
                }
            }


            return PAGE_EXISTS;
        
        } finally {
            g2d.setTransform(savedTransform);
            g2d.setClip(clip);
        }
    }
    
    
    private void drawCard(SeperatorDefinition sep, Graphics2D g2d) {
        AffineTransform transform = g2d.getTransform();
        try {
            
            double width = dividerWidth;
            double height = headerArea;
            
            double leftWidth = totalWidth(g2d, sep.getLeftSymbols(), height);
            
            double leftStart = dividerWidth / 2 - headerArea / 2 - leftWidth;
            
            g2d.translate(leftStart, headerTopMargin);
            drawSymbols(g2d, sep.getLeftSymbols(), height);
            
            g2d.setTransform(transform);
            
            g2d.translate(dividerWidth / 2 + headerArea / 2, headerTopMargin);
            drawSymbols(g2d, sep.getRightSymbols(), height);



        } finally {
            g2d.setTransform(transform);
        }
        
        
//        
//            ImageIcon icon = SymbolFactory.getIcon(sep.getColor());
//            
//            double iconHeight = icon.getIconHeight();
//            
//            double scale = headerArea / iconHeight;
//            
//            AffineTransform saved = g2d.getTransform();
//            
//            g2d.scale(scale, scale);
//            
//            icon.paintIcon(null, g2d, 0, 0);
//            
//            g2d.setTransform(saved);
//            
//            icon = SymbolFactory.getIcon(sep.getRelease(), sep.getRarity());
//            scale = headerArea / icon.getIconHeight();
//            
//            
//            g2d.translate(dividerWidth - scale * icon.getIconWidth(), 0);
//            g2d.scale(scale, scale);
//            
//            icon.paintIcon(null, g2d, 0, 0);
            
    }
    
    
    private static final double interSymbolSpace = 0.2d;
    
    private double totalWidth(Graphics2D g2d, List<SymbolDrawer> drawers, double height) {
        double totalWidth = 0d;
        for (int i = 0; i < drawers.size(); i++) {
            SymbolDrawer drawer = drawers.get(i);
            if (i > 0)
                totalWidth += interSymbolSpace * height;
            totalWidth += drawer.getWidth(g2d, height);
        }
        
        return totalWidth;
    }
    
    private void drawSymbols(Graphics2D g2d, List<SymbolDrawer> drawers, double height) {
        AffineTransform transform = g2d.getTransform();
        try {
            
            for (int i = 0; i < drawers.size(); i++) {
                SymbolDrawer drawer = drawers.get(i);
                if (i > 0)
                    g2d.translate(interSymbolSpace * height, 0);
                drawer.draw(g2d, height);
                g2d.translate(drawer.getWidth(g2d, height), 0);
            }
            
            
        } finally {
            g2d.setTransform(transform);
        }
    }
    
}
