package gui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Assets {

    public static BufferedImage
            pawnw, knightW, rookW, bishopW, queenW, kingW,
            pawnB, knightB, rookB, bishopB, queenB, kingB;

    public Assets() {
        try {
            pawnw = ImageIO.read(new FileInputStream("assets/pawnw.png"));
            knightW = ImageIO.read(new FileInputStream("assets/knightw.png"));
            rookW = ImageIO.read(new FileInputStream("assets/rookw.png"));
            bishopW = ImageIO.read(new FileInputStream("assets/bishopw.png"));
            queenW = ImageIO.read(new FileInputStream("assets/queenw.png"));
            kingW = ImageIO.read(new FileInputStream("assets/kingw.png"));
            pawnB = ImageIO.read(new FileInputStream("assets/pawnb.png"));
            knightB = ImageIO.read(new FileInputStream("assets/knightb.png"));
            rookB = ImageIO.read(new FileInputStream("assets/rookb.png"));
            bishopB = ImageIO.read(new FileInputStream("assets/bishopb.png"));
            queenB = ImageIO.read(new FileInputStream("assets/queenb.png"));
            kingB = ImageIO.read(new FileInputStream("assets/kingb.png"));
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
