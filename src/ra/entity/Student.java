package ra.entity;

import ra.color.Color;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Student implements IEntity<Student> {
    private String studentId;
    private String studentName;
    private String birthday;
    private int age;
    private boolean sex;
    private float mark_html;
    private float mark_css;
    private float mark_js;
    private float avgMark;
    private String rank;

    public Student() {
    }

    public Student(String studentId, String studentName, String birthday, int age, boolean sex, float mark_html, float mark_css, float mark_js, float avgMark, String rank) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.birthday = birthday;
        this.age = age;
        this.sex = sex;
        this.mark_html = mark_html;
        this.mark_css = mark_css;
        this.mark_js = mark_js;
        this.avgMark = avgMark;
        this.rank = rank;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public float getMark_html() {
        return mark_html;
    }

    public void setMark_html(float mark_html) {
        this.mark_html = mark_html;
    }

    public float getMark_css() {
        return mark_css;
    }

    public void setMark_css(float mark_css) {
        this.mark_css = mark_css;
    }

    public float getMark_js() {
        return mark_js;
    }

    public void setMark_js(float mark_js) {
        this.mark_js = mark_js;
    }

    public float getAvgMark() {
        return avgMark;
    }

    public void setAvgMark(float avgMark) {
        this.avgMark = avgMark;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public void inputData(Scanner scanner, List<Student> studentList) {
        System.out.print("Nhập mã sinh viên: ");
        boolean checkStudentId;
        do {
            this.studentId = scanner.nextLine();
            checkStudentId = uniqueStudentId(studentList);
            if (checkStudentId) {
                System.err.println("Mã sinh viên đã tồn tại, vui lòng nhập lại!");
                checkStudentId = false;
            } else {
                if (this.studentId.length() == 4 && this.studentId.charAt(0) == 'S') {
                    break;
                } else {
                    System.err.println("Mã sinh viên bắt đầu bằng 'S', bắt buộc phải 4 ký tự");
                }
            }
        } while (!checkStudentId);

        System.out.print("Nhập tên sinh viên: ");
        this.studentName = scanner.nextLine();
        System.out.print("Nhập ngày sinh (trước năm 2005): "); // tính ra tuổi sinh viên
        boolean checkBirthday = false;
        do {
            this.birthday = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {
                LocalDate inputDate = LocalDate.parse(this.birthday, formatter);
//                System.out.println("Ngày tháng đã nhập: " + inputDate.getYear());
                checkBirthday = false;
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng ngày tháng không hợp lệ. (dd/MM/yyyy)");
            }
        } while (checkBirthday);
        System.out.print("Nhập giới tính: ");
        this.sex = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("Nhập điểm HTML: ");
        this.mark_html = Float.parseFloat(scanner.nextLine());
        System.out.print("Nhập điểm CSS: ");
        this.mark_css = Float.parseFloat(scanner.nextLine());
        System.out.print("Nhập điểm JavaScript: ");
        this.mark_js = Float.parseFloat(scanner.nextLine());
    }

    @Override
    public void displayData() {
        String displaySex = sex ? "Nam" : "Nữ";
        System.out.printf("%-15s%-25s%-25s%-10d%-15s%-15.1f%-15.1f%-15.1f%-15.1f%-20s\n", this.studentId, this.studentName, this.birthday, this.age, displaySex, this.mark_html, this.mark_css, this.mark_js, this.avgMark, this.rank);
    }

    @Override
    public void calAge() {
        // tính tuổi sinh viên từ birthday
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int currentDate = LocalDate.now().getYear();
        int birthDate = LocalDate.parse(getBirthday(), formatter).getYear();
        this.age = currentDate - birthDate;
    }

    @Override
    public void calAvgMark_Rank() {
        // tính điểm trung bình và xếp loại sinh viên
        this.avgMark = (mark_html + mark_css + mark_js) / 3;
        if (avgMark < 5) {
            this.rank = "Yếu";
        } else if (avgMark < 7) {
            this.rank = "Trung bình";
        } else if (avgMark < 8) {
            this.rank = "Khá";
        } else if (avgMark < 9) {
            this.rank = "Giỏi";
        } else {
            this.rank = "Xuất sắc";
        }
    }

    public boolean uniqueStudentId(List<Student> studentList) {
        for (Student student : studentList) {
            if (student.studentId.equals(this.studentId)) {
                return true;
            }
        }
        return false;
    }
}
