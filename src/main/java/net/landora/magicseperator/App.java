package net.landora.magicseperator;

import java.awt.Color;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.Icon;

/**
 * Hello world!
 *
 */
public class App {
    

    public static void main(String[] args) throws Exception {
        List<SeperatorDefinition> seps = new ArrayList<SeperatorDefinition>();
        
        buildCoreSet(Release.ReturnToRavnica, seps);
        buildBasicSet(Release.Magic2013, seps);
        buildBasicMultiColor(Release.Magic2013, seps);
        buildBasicSet(Release.AvacynRestored, seps);
        buildBasicMultiColor(Release.AvacynRestored, seps);
        
        for(MagicColor c: MagicColor.values())
            if (c.isBaseColor())
                buildSpecialSeperator(seps, Arrays.asList(c), Arrays.asList("Basic Land"));
        
        
        for(MagicColor c: Arrays.asList(
                MagicColor.BlackGreen,
                MagicColor.BlackRed,
                MagicColor.BlueRed,
                MagicColor.GreenWhite,
                MagicColor.WhiteBlue
                ))
            buildSpecialSeperator(Release.ReturnToRavnica, seps, c);
        
        buildSpecialSeperator(seps, Arrays.asList(MagicColor.Colorless), 
                Arrays.asList(SymbolFactory.getIcon(Release.Gatecrash, Rarity.Common)));
        buildSpecialSeperator(seps, Arrays.asList(MagicColor.Colorless), 
                Arrays.asList(SymbolFactory.getIcon(Release.Gatecrash, Rarity.Uncommon)));
        buildSpecialSeperator(seps, Arrays.asList(MagicColor.Colorless), 
                Arrays.asList(SymbolFactory.getIcon(Release.Gatecrash, Rarity.Rare), SymbolFactory.getIcon(Release.Gatecrash, Rarity.Mystic)));
        
        buildSpecialSeperator(seps, Arrays.asList("Unsorted"), 
                Arrays.asList("Misc"));
        
        Paper paper = new Paper();
        paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight());
        PageFormat format = new PageFormat();
        format.setPaper(paper);
        
        Book pages = new Book();
        pages.append(new MagicPaperPrintable(seps), format, (int)Math.ceil(seps.size() / 6d));
        
        
        
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(pages);
        if (!job.printDialog())
            return;
        
        job.print();
        
        

    }
    
    
    private static void buildCoreSet(Release set, List<SeperatorDefinition> seps) throws MalformedURLException {
        
        for(MagicColor c: MagicColor.values()) {
            if (!c.includeInDefaultSets())
                continue;
            for(Rarity r: Rarity.values()) {
                if (r == Rarity.Mystic)
                    continue;
                SeperatorDefinition def = new SeperatorDefinition();
                def.addLeftSymbol(new ImageIconDrawer(SymbolFactory.getIcon(c)));
                def.addRightSymbol(new ImageIconDrawer(SymbolFactory.getIcon(set, r)));
                if (r == Rarity.Rare)
                    def.addRightSymbol(new ImageIconDrawer(SymbolFactory.getIcon(set, Rarity.Mystic)));
                seps.add(def);
            }
        }
        
        SeperatorDefinition def = new SeperatorDefinition();
        def.addLeftSymbol(new TextSymbolDrawer("Lands"));
        for(Rarity r: Rarity.values())
            def.addRightSymbol(new ImageIconDrawer(SymbolFactory.getIcon(set, r)));
        seps.add(def);
    }
    
    private static void buildBasicSet(Release set, List<SeperatorDefinition> seps) throws MalformedURLException {
        
        for(MagicColor c: MagicColor.values()) {
            if (!c.includeInDefaultSets())
                continue;
            
            SeperatorDefinition def = new SeperatorDefinition();
            def.addLeftSymbol(new ImageIconDrawer(SymbolFactory.getIcon(c)));
            for(Rarity r: Rarity.values())
                def.addRightSymbol(new ImageIconDrawer(SymbolFactory.getIcon(set, r)));
            seps.add(def);
        
        }
        
        SeperatorDefinition def = new SeperatorDefinition();
        def.addLeftSymbol(new TextSymbolDrawer("Lands"));
        for(Rarity r: Rarity.values())
            def.addRightSymbol(new ImageIconDrawer(SymbolFactory.getIcon(set, r)));
        seps.add(def);
    }
    
    private static void buildBasicMultiColor(Release set, List<SeperatorDefinition> seps) throws MalformedURLException {
        SeperatorDefinition def = new SeperatorDefinition();
        for(MagicColor c: MagicColor.values())
            if (c.isBaseColor())
                def.addLeftSymbol(new ImageIconDrawer(SymbolFactory.getIcon(c)));
        for(Rarity r: Rarity.values())
            def.addRightSymbol(new ImageIconDrawer(SymbolFactory.getIcon(set, r)));
        seps.add(def);
    }
    
    public static void buildSpecialSeperator(Release set, List<SeperatorDefinition> seps, Object...leftIcons) throws MalformedURLException {
        buildSpecialSeperator(seps, Arrays.asList(leftIcons), Arrays.asList(set));
    }
    
    public static void buildSpecialSeperator(List<SeperatorDefinition> seps, Collection<?> leftIcons, Collection<?> rightIcons) throws MalformedURLException {
        SeperatorDefinition def = new SeperatorDefinition();
        for(Object obj: leftIcons) {
            for(SymbolDrawer drawer: getDrawer(obj))
                def.addLeftSymbol(drawer);
        }
        for(Object obj: rightIcons) {
            for(SymbolDrawer drawer: getDrawer(obj))
                def.addRightSymbol(drawer);
        }
        seps.add(def);
    }
    
    public static List<? extends SymbolDrawer> getDrawer(Object obj) throws MalformedURLException {
        if (obj == null)
            return Collections.EMPTY_LIST;
        else if (obj instanceof SymbolDrawer)
            return Collections.singletonList((SymbolDrawer)obj);
        else if (obj instanceof Icon)
            return Collections.singletonList(new ImageIconDrawer((Icon)obj));
        else if (obj instanceof MagicColor)
            return Collections.singletonList(new ImageIconDrawer(SymbolFactory.getIcon((MagicColor)obj)));
        else if (obj instanceof String)
            return Collections.singletonList(new TextSymbolDrawer((String)obj));
        else if (obj instanceof Release) {
            List<SymbolDrawer> icons = new ArrayList<SymbolDrawer>();
            Release set = (Release)obj;
            for(Rarity r: Rarity.values())
                icons.add(new ImageIconDrawer(SymbolFactory.getIcon(set, r)));
            return icons;
        } else
            throw new IllegalArgumentException("Unknown symbol object: " + obj.getClass());
    }
}
