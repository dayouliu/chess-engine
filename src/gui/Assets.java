package gui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Assets {

    public static BufferedImage[] pieces = new BufferedImage[13];

    public Assets() {
        try {
            pieces[1] = ImageIO.read(new FileInputStream("assets/pawnw.png"));
            pieces[2] = ImageIO.read(new FileInputStream("assets/knightw.png"));
            pieces[3] = ImageIO.read(new FileInputStream("assets/bishopw.png"));
            pieces[4] = ImageIO.read(new FileInputStream("assets/rookw.png"));
            pieces[5] = ImageIO.read(new FileInputStream("assets/queenw.png"));
            pieces[6] = ImageIO.read(new FileInputStream("assets/kingw.png"));
            pieces[7] = ImageIO.read(new FileInputStream("assets/pawnb.png"));
            pieces[8] = ImageIO.read(new FileInputStream("assets/knightb.png"));
            pieces[9] = ImageIO.read(new FileInputStream("assets/bishopb.png"));
            pieces[10] = ImageIO.read(new FileInputStream("assets/rookb.png"));
            pieces[11] = ImageIO.read(new FileInputStream("assets/queenb.png"));
            pieces[12] = ImageIO.read(new FileInputStream("assets/kingb.png"));
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
