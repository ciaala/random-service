package com.ffi.service.randomGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by cryptq on 9/15/16.
 */
class RandomResultImageGenerator {
    private RandomResult result;
    private BufferedImage image;
    private Graphics2D g2d;

    private enum XAlignment {
        RIGHT,
        LEFT,
        CENTERED;
    }

    private enum YAlignment {
        TOP,
        BOTTOM,
        CENTERED;
    }
    
    public RandomResultImageGenerator(RandomResult result) {
        this.result = result;
        this.image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
        this.g2d = image.createGraphics();
    }


    public BufferedImage getResponseImage() {
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());

        g2d.setColor(Color.red);
        g2d.setFont(new Font("Serif", Font.BOLD, 20));

        writeText(g2d, image, String.format("Rnd: %s", result.value), XAlignment.CENTERED, YAlignment.CENTERED);
        writeText(g2d, image, String.format("# %d", result.views), XAlignment.RIGHT, YAlignment.BOTTOM);
        writeText(g2d, image, String.format("ID: %s", result.identifier), XAlignment.LEFT, YAlignment.TOP);

        g2d.dispose();
        image.flush();
        return image;
    }

    private void writeText(Graphics2D g2d, BufferedImage image, String text, XAlignment xAlignment, YAlignment yAlignment) {
        final int PADDING = 4;
        FontMetrics fm = g2d.getFontMetrics();
        int x;
        int y;
        switch (xAlignment) {
            case CENTERED: {
                x = (image.getWidth() / 2) - (fm.stringWidth(text) / 2);
                break;
            }
            case RIGHT: {
                x = (image.getWidth() - fm.stringWidth(text) - PADDING);
                break;
            }
            case LEFT:
            default: {
                x = PADDING;
            }
        }

        switch (yAlignment) {
            case CENTERED: {
                y = (image.getHeight() / 2) - (fm.getHeight() / 2);
                break;
            }
            case TOP: {
                y = fm.getHeight() + PADDING;
                break;
            }
            case BOTTOM:
            default: {
                y = (image.getHeight() - fm.getHeight() - PADDING);
            }
        }
        g2d.drawString(text, x, y);
    }
}
