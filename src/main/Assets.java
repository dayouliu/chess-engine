package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class Assets {

    public static BufferedImage
            pawnW, knightW, rookW, bishopW, queenW, kingW,
            pawnB, knightB, rookB, bishopB, queenB, kingB;

    public Assets() {
        try {
            pawnW = ImageIO.read(Map.class.getResource("/w_pawn.png"));
            knightW = ImageIO.read(Map.class.getResource("/w_knight.png"));
            rookW = ImageIO.read(Map.class.getResource("/w_rook.png"));
            bishopW = ImageIO.read(Map.class.getResource("/w_bishop.png"));
            queenW = ImageIO.read(Map.class.getResource("/w_queen.png"));
            kingW = ImageIO.read(Map.class.getResource("/w_king.png"));

            pawnB = ImageIO.read(Map.class.getResource("/b_pawn.png"));
            knightB = ImageIO.read(Map.class.getResource("/b_knight.png"));
            rookB = ImageIO.read(Map.class.getResource("/b_rook.png"));
            bishopB = ImageIO.read(Map.class.getResource("/b_bishop.png"));
            queenB = ImageIO.read(Map.class.getResource("/b_queen.png"));
            kingB = ImageIO.read(Map.class.getResource("/b_king.png"));
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
