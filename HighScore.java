package View;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import model.HighScoreList;

public class HighScore implements Paintable {
    private Button close;
    private int width, height, posX, posY, X, Y;
    private BufferedImage background;
    private HighScoreList highScoreList;
    private String winnerName;
    private int winnerScore;

    //	Dòng 30-48: Phương thức khởi tạo HighScore, nhận vào thông số về kích thước (width, height) của cửa sổ.
//	Trong phương thức này, hình nền được tải từ tệp "HighScoreSplash.png", winnerName được đặt là null,
//	và một đối tượng HighScoreList được tạo.
//	Các thông số khác được khởi tạo dựa trên kích thước cửa sổ, và nút close được tạo.
    public HighScore(int width, int height) {
        try {
            // load the background image and the overlay.
            InputStream is = this.getClass().getClassLoader()
                    .getResourceAsStream("HighScoreSplash.png");
            background = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.winnerName = null;
        this.highScoreList = new HighScoreList();
        this.width = width;
        this.height = height;
        this.posX = Math.round((width / 2) - (WIDTH / 2));
        this.posY = Math.round((height / 2) - (HEIGHT / 2));
        close = new Button(this.width, "Close", "CloseHighScore", posY + HEIGHT
                - (BUTTON_HEIGHT + 24));
    }

    //Dòng 57-100: Phương thức paint(Graphics g) được ghi đè từ giao diện Paintable
// và được sử dụng để vẽ danh sách điểm cao lên giao diện người dùng.
// Trong phương thức này, một đối tượng Graphics2D được tạo từ đối tượng Graphics để vẽ các thành phần.
// Hình nền được vẽ lên đầu tiên, sau đó là các thông tin về người chiến thắng (nếu có).
// Tiếp theo là tiêu đề của các cột và danh sách điểm cao được lặp qua và vẽ lên giao diện.
// Cuối cùng, nút close được vẽ.
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        Font font = new Font("Arial", Font.PLAIN, 22);
        g2d.setFont(font);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        AlphaComposite ac = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.5f);
        g2d.setComposite(ac);
        g2d.setColor(new Color(0, 0, 0));
        g2d.fillRect(0, 0, width, height);
        ac = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 1.0f);
        g2d.setComposite(ac);

        g2d.drawImage(background, null, posX, posY);

        g2d.setColor(new Color(0, 0, 0));

        //
        if (winnerName != null) {
            //
            g2d.drawString(winnerName + " Wins! Score " + winnerScore, (this.posX + 200), this.posY + 80);
        }

        // cột thông tin
        font = new Font("Arial", Font.BOLD, 22);
        g2d.setFont(font);
        g2d.drawString("Place", (this.posX + 90), this.posY + 145);
        g2d.drawString("Name", (this.posX + 175), this.posY + 145);
        g2d.drawString("Score", (this.posX + 400), this.posY + 145);

        font = new Font("Arial", Font.PLAIN, 22);
        g2d.setFont(font);
        // Hiện tất cả điểm cao
        for (int i = 0; i < HighScoreList.ENTRIES - 1 && Integer.parseInt(highScoreList.getScore(i)) > 0; i++) {
            g2d.drawString((i + 1) + ".", (this.posX + 90), this.posY + 175 + (22 * i));
            g2d.drawString(highScoreList.getName(i), (this.posX + 175), this.posY + 175 + (22 * i));
            g2d.drawString(highScoreList.getScore(i), (this.posX + 400), this.posY + 175 + (22 * i));
        }

        close.paint(g2d, X, Y);
    }

    //Dòng 105-107: Phương thức updateHighScore(String name, int score)
// được sử dụng để cập nhật danh sách điểm cao với thông tin về người chiến thắng mới.
// Phương thức này gọi phương thức update() của đối tượng HighScoreList để cập nhật danh sách điểm cao.
    public void updateHighScore(String name, int score) {
        this.highScoreList.update(name, score);
    }

    //Dòng 111-115: Phương thức update(int X, int Y)
// được ghi đè từ giao diện Paintable và được sử dụng để cập nhật vị trí chuột trên giao diện.
    @Override
    public boolean update(int X, int Y) {
        this.X = X;
        this.Y = Y;
        return false;
    }

    //Dòng 122-127: Phương thức getButton(int X, int Y)
    //được ghi đè từ giao diện Paintable và được sử dụng để kiểm tra xem người dùng đã nhấp vào nút nào trên giao diện hay chưa.
    //Nếu người dùng nhấp vào nút close, phương thức trả về giá trị của nút close.
    @Override
    public String getButton(int X, int Y) {
        if (close.isInside(X, Y)) {
            return close.getValue();
        }
        return null;
    }

//  Dòng 129-132: Phương thức clearWinner() được sử dụng để xóa thông tin về người chiến thắng
    public void clearWinner() {
        this.winnerName = null;
    }

    private static int WIDTH = 600;
    private static int HEIGHT = 558;
    private static int BUTTON_HEIGHT = 70;
//Dòng 119-122: Phương thức setWinner(String whoseTurn, int winnerScore)
// được sử dụng để đặt thông tin về người chiến thắng và điểm số của người chiến thắng mới.
    public void setWinner(String whoseTurn, int winnerScore) {
        this.winnerName = whoseTurn;
        this.winnerScore = winnerScore;
    }
}
