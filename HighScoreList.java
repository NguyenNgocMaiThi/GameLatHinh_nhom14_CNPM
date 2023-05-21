package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HighScoreList {
    private int[] score;
    private String[] name;

    //Dòng 14-19: Phương thức khởi tạo HighScoreList được gọi khi lớp được tạo.
// Phương thức này khởi tạo mảng score và mảng name với số lượng phần tử là ENTRIES (giá trị cố định là 10),
// sau đó gọi phương thức load() để tải dữ liệu điểm cao từ tệp.
    public HighScoreList() {
        score = new int[ENTRIES];
        name = new String[ENTRIES];
        this.load();
    }

    //Dòng 24-26: Phương thức getScore(int index) trả về giá trị của điểm số ở vị trí index dưới dạng chuỗi.
    public String getScore(int index) {
        return score[index] + "";
    }

    //Dòng 34-42: Phương thức getName(int index) trả về tên người chơi ở vị trí index trong danh sách điểm cao dưới dạng chuỗi.
// Nếu tên là null, phương thức trả về chuỗi rỗng.
    public String getName(int index) {
        if (name[index] != null) {
            return name[index];
        } else return "";
    }

    //	Dòng 42-67: Phương thức load() đọc dữ liệu điểm cao từ tệp được chỉ định bởi FILENAME ("Memory-HighScore")
//	và lưu trữ nó trong mảng score và mảng name.
//	Phương thức sử dụng FileReader và BufferedReader để đọc từng dòng trong tệp. Dòng đầu tiên được lưu vào mảng name,
//	sau đó dòng thứ hai (nếu có) được chuyển đổi thành số nguyên và lưu vào mảng score. Sau khi đọc xong, phương thức đóng tệp.
    public void load() {
        String line = null;
        int i = 0;

        try {
            // trỏ tới tệp
            FileReader fileReader = new FileReader(FILENAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
//
            while ((line = bufferedReader.readLine()) != null && line != "") {
                name[i] = line;

                if ((line = bufferedReader.readLine()) != null) {
                    score[i] = Integer.parseInt(line);
                }
                i++;
            }
            // Đóng tệp
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            //
        } catch (IOException e) {
            //
        }
    }

    //	Dòng 70-89: Phương thức save() ghi dữ liệu điểm cao vào tệp được chỉ định bởi FILENAME.
//	Phương thức sử dụng FileWriter và BufferedWriter để ghi từng dòng dữ liệu vào tệp.
//	Chỉ những mục có tên khác null mới được ghi vào tệp. Sau khi ghi xong, phương thức đóng tệp.
    public void save() {
        try {
            FileWriter fileWriter = new FileWriter(FILENAME);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (int i = 0; i < ENTRIES; i++) {
                if (name[i] != null) {
                    bufferedWriter.write(name[i]);
                    bufferedWriter.newLine();
                    bufferedWriter.write(String.valueOf(score[i]));
                    bufferedWriter.newLine();
                }
            }

            // đóng tệp. :(
            bufferedWriter.close();
        } catch (IOException ex) {
        }
    }

    //Dòng 94-99: Phương thức pack(int index) di chuyển tất cả các mục từ vị trí index trở về sau trong mảng name
// và score sang phải một vị trí.
// Phương thức này được sử dụng để tạo chỗ trống cho một người chơi mới có điểm số cao hơn.
// Người chơi có điểm số thấp nhất (ở vị trí cuối cùng) sẽ bị ghi đè.
    private void pack(int index) {
        for (int i = ENTRIES - 1; i > index; i--) {
            name[i] = name[i - 1];
            score[i] = score[i - 1];
        }
    }

    //Dòng 106-124: Phương thức update(String name, int score) sẽ cập nhật tên và điểm số của người chiến thắng mới.
// Phương thức này tìm vị trí thích hợp trong danh sách điểm cao để chèn điểm mới.
// Nếu điểm mới cao hơn hoặc bằng điểm cao tại vị trí đó, phương thức sẽ gọi phương thức pack() để tạo chỗ trống
// và sau đó ghi đè tên và điểm số của người chiến thắng vào danh sách.
// Cuối cùng, phương thức gọi save() để lưu danh sách điểm cao mới vào tệp.
    public void update(String name, int score) {
        int i = -1;

        // loop through the highscores, finding an entry point.
        do {
            i++;
        } while (i < ENTRIES && score < this.score[i]);

        if (i < ENTRIES && score >= this.score[i]) {
            this.pack(i);
            this.name[i] = name;
            this.score[i] = score;
            this.save();
        }
    }

    public static final int ENTRIES = 10;
    private static final String FILENAME = "Memory-HighScore";
}
