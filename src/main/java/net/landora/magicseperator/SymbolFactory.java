/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.magicseperator;

import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author bdickie
 */
public class SymbolFactory {
    private static final String PATTERN_SET_RARITY = "http://gatherer.wizards.com/Handlers/Image.ashx?type=symbol&set=%s&size=large&rarity=%s";
    private static final String PATTERN_COLOR = "http://gatherer.wizards.com/Handlers/Image.ashx?size=large&name=%s&type=symbol";

    private SymbolFactory() {
    }
    
    public static ImageIcon getIcon(Release release, Rarity rarity) throws MalformedURLException {
        String url = String.format(PATTERN_SET_RARITY, release.getSymbolCode(), rarity.getCode());
        return new ImageIcon(new URL(url));
    }
    public static ImageIcon getIcon(MagicColor color) throws MalformedURLException {
        String url = String.format(PATTERN_COLOR, color.getCode());
        return new ImageIcon(new URL(url));
    }
}
