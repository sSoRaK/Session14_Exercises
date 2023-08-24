package ra.impl;

import ra.entity.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
                    StudentImp.displayData();
                    break;
                case 3:
                    break;
                case 4:
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
        System.out.printf("%-15s%-25s%-25s%-10s%-15s%-15s%-15s%-15s%-15s%-20s", "Student ID", "Student Name", "Date of Birth", "Age", "Sex", "HTML Score", "CSS Score", "JS Score", "Average Score", "Rank");
        System.out.println("******************************************************************************************");
    }
    // Đọc file demo.txt và in ra các sách có giá trong khoảng 10000 đến 20000
//    public static void
}
