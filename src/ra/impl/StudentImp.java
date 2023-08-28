package ra.impl;

import ra.entity.Student;
import ra.color.Color;

import java.io.Serializable;
import java.util.*;

public class StudentImp implements Serializable {
    public static List<Student> studentList = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    // in menu
    public static void main(String[] args) {
        while (true) {
            System.out.println("***************************** MENU *****************************");
            System.out.println("1. Nhập thông tin các sinh viên");
            System.out.println("2. Tính tuổi các sinh viên");
            System.out.println("3. Tính điểm trung bình và xếp loại sinh viên");
            System.out.println("4. Sắp xếp sinh viên theo tuổi tăng dần");
            System.out.println("5. Thống kê sinh viên theo xếp loại sinh viên");
            System.out.println("6. Cập nhập thông tin sinh viên theo mã sinh viên");
            System.out.println("7. Tìm kiếm sinh viên theo tên sinh viên");
            System.out.println("8. Thoát");
            System.out.println("****************************************************************");
            System.out.print("Nhập lựa chọn: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    StudentImp.inputData();
                    break;
                case 2:
                    StudentImp.calAge();
                    StudentImp.displayData();
                    break;
                case 3:
                    StudentImp.rank();
                    break;
                case 4:
                    StudentImp.sortedDescAge();
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    System.exit(0);
                    break;
                default:
                    System.err.println("Nhập lại lựa chọn từ (1 - 9).");
                    break;
            }
        }
    }

    // methods
    public static void inputData() {
        System.out.print("Nhập số lượng sinh viên muốn thêm: ");
        int numStudent = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < numStudent; i++) {
            System.out.println("Sinh viên thứ " + (i + 1) + " : ");
            Student student = new Student();
            student.inputData(scanner, studentList);
            studentList.add(student);
        }
        System.out.println("Đã thêm sinh viên thành công!");
    }

    public static void displayData() {
        System.out.println("************************************** Student List **************************************");
        System.out.printf("%-15s%-25s%-25s%-10s%-15s%-15s%-15s%-15s%-15s%-20s\n", "Student ID", "Student Name", "Date of Birth", "Age", "Sex", "HTML Score", "CSS Score", "JS Score", "Average Score", "Rank");
        for (Student student : studentList) {
            student.displayData();
        }
        System.out.println("******************************************************************************************");
    }

    // tính tuổi dựa trên ngày tháng năm sinh đã nhập
    public static void calAge() {
        for (Student student : studentList) {
            student.calAge();
        }
        System.out.println("Đã hoàn thành tính tuổi!");
    }

    // tính avgMark và xếp loại tương ứng từng sinh viên
    public static void rank() {
        for (Student student : studentList) {
            student.calAvgMark_Rank();
        }
        System.out.println("Đã hoàn thành đánh giá xếp loại!");
    }

    // sắp xếp sinh viên theo tuổi tăng dần
    public static void sortedDescAge() {
        List<Student> studentsDesc = new ArrayList<>(studentList);
        Collections.sort(studentsDesc, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Integer.compare(o1.getAge(), o2.getAge());
            }
        });
        System.out.println("************************************** Student List **************************************");
        System.out.printf("%-15s%-25s%-25s%-10s%-15s%-15s%-15s%-15s%-15s%-20s\n", "Student ID", "Student Name", "Date of Birth", "Age", "Sex", "HTML Score", "CSS Score", "JS Score", "Average Score", "Rank");
        for (Student student : studentsDesc) {
            student.displayData();
        }
        System.out.println("******************************************************************************************");
    }

    // thống kê sinh viên theo xếp loại
    public static void rankStats() {
        if (studentList.isEmpty()) {
            System.out.println("Không có sinh viên nào trong danh sách.");
            return;
        }
        List<String> rankList = new ArrayList<>();
        List<Integer> studentsByRank = new ArrayList<>();

        for (Student student : studentList) {
            String studentRank = student.getRank();
            int index = rankList.indexOf(studentRank);
            if (index != -1) {
                studentsByRank.set(index, studentsByRank.get(index) + 1);
            } else {
                rankList.add(studentRank);
                studentsByRank.add(1);
            }
        }
        System.out.println("Thống kê sinh viên theo xếp loại");
        for (int i = 0; i < rankList.size(); i++) {
            System.out.printf("%s: %d sinh viên\n", rankList.get(i), studentsByRank.get(i));
        }
    }
}
