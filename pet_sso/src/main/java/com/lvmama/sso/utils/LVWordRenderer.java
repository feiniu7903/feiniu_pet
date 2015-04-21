package com.lvmama.sso.utils;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.google.code.kaptcha.text.WordRenderer;
import com.google.code.kaptcha.util.Configurable;
/**
 *  字体虽然是可以配置的，但是字体展示都粗体
 *  通过这个类可以自定义字体的样式
 *  
 * @author gairyoung
 *
 */
public class LVWordRenderer extends Configurable implements WordRenderer{  
  
    public LVWordRenderer()  
    {  
    }  
  
    public BufferedImage renderWord(String word, int width, int height)  
    {  
        int fontSize = getConfig().getTextProducerFontSize();  
        String paramName = "kaptcha.textproducer.font.names";  
        String paramValue = (String)getConfig().getProperties().get(paramName);  
        String fontNames[] = paramValue.split(",");  
        Font[] fonts = new Font[fontNames.length*2];  
        Random random = new Random();  
        for(int i = 0; i < fontNames.length; i++){  
            fonts[i*2] = new Font(fontNames[i], Font.BOLD, fontSize);  
            fonts[i*2+1] = new Font(fontNames[i], Font.ITALIC, fontSize);  
        }  
        java.awt.Color color = getConfig().getTextProducerFontColor();  
        int charSpace = getConfig().getTextProducerCharSpace();  
        BufferedImage image = new BufferedImage(width, height, 2);  
        Graphics2D g2D = image.createGraphics();  
          
        g2D.setColor(color);  
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
        hints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));  
          
        g2D.setRenderingHints(hints);  
        java.awt.font.FontRenderContext frc = g2D.getFontRenderContext();  
        int startPosY = (height - fontSize) / 5 + fontSize;  
        char wordChars[] = word.toCharArray();  
        Font chosenFonts[] = new Font[wordChars.length];  
        int charWidths[] = new int[wordChars.length];  
        int widthNeeded = 0;  
        for(int i = 0; i < wordChars.length; i++)  
        {  
            chosenFonts[i] = fonts[random.nextInt(fonts.length)];  
            char charToDraw[] = {  
                wordChars[i]  
            };  
            GlyphVector gv = chosenFonts[i].createGlyphVector(frc, charToDraw);  
            charWidths[i] = (int)gv.getVisualBounds().getWidth();  
            if(i > 0)  
                widthNeeded += 2;  
            widthNeeded += charWidths[i];  
        }  
  
        int startPosX = (width - widthNeeded) / 2;  
        for(int i = 0; i < wordChars.length; i++)  
        {  
            g2D.setFont(chosenFonts[i]);  
            char charToDraw[] = {  
                wordChars[i]  
            };  
            g2D.drawChars(charToDraw, 0, charToDraw.length, startPosX, startPosY);  
            startPosX = startPosX + charWidths[i] + charSpace;  
        }  
  
        return image;  
    }  
}  
