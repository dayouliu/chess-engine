package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class Assets {

    public static BufferedImage
            pawnW, knightW, rookW, bishopW, queenW, kingW,
            pawnB, knightB, rookB, bishopB, queenB, kingB;

    public Assets() {
        try {
            pawnW = ImageIO.read(new FileInputStream("assets/w_pawn.png"));
            knightW = ImageIO.read(new FileInputStream("assets/w_knight.png"));
            rookW = ImageIO.read(new FileInputStream("assets/w_rook.png"));
            bishopW = ImageIO.read(new FileInputStream("assets/w_bishop.png"));
            queenW = ImageIO.read(new FileInputStream("assets/w_queen.png"));
            kingW = ImageIO.read(new FileInputStream("assets/w_king.png"));

            pawnB = ImageIO.read(new FileInputStream("assets/b_pawn.png"));
            knightB = ImageIO.read(new FileInputStream("assets/b_knight.png"));
            rookB = ImageIO.read(new FileInputStream("assets/b_rook.png"));
            bishopB = ImageIO.read(new FileInputStream("assets/b_bishop.png"));
            queenB = ImageIO.read(new FileInputStream("assets/b_queen.png"));
            kingB = ImageIO.read(new FileInputStream("assets/b_king.png"));
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
