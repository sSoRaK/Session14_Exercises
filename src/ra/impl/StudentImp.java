package ra.impl;

import ra.entity.Student;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class StudentImp implements Serializable {
    public static List<Student> studentList = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    // in menu
    public static void main(String[] args) {
        // đọc dữ liệu từ file
        List<Student> readData = StudentImp.readFromFileAndPrintInRange("studentList.txt");
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
                    StudentImp.rankStats();
                    break;
                case 6:
                    // chưa cập nhật được thông tin sinh viên
                    StudentImp.updateInfoStudent();
                    break;
                case 7:
                    StudentImp.searchStudentName();
                    break;
                case 8:
                    StudentImp.writeDataToFile("studentList.txt");
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
            if (index != -1) { // khi đã có giá trị rank trong List, thì sẽ tăng thêm 1 để đếm
                studentsByRank.set(index, studentsByRank.get(index) + 1);
            } else { // lần đầu chưa có kiểu rank trong rankList, nên sẽ được thêm vào ngay sau đó. Thêm giá trị 1, để lần tiếp theo tăng thêm giá trị
                rankList.add(studentRank);
                studentsByRank.add(1);
            }
        }
        System.out.println("Thống kê sinh viên theo xếp loại");
        for (int i = 0; i < rankList.size(); i++) {
            System.out.printf("%s: %d sinh viên\n", rankList.get(i), studentsByRank.get(i));
        }
    }

    // cập nhật thông tin sinh viên theo mã sinh viên
    public static void updateInfoStudent() {
        System.out.print("Nhập mã sinh viên để cập nhật: ");
        String inputStudentId = scanner.nextLine();
        int index = -1;
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getStudentId().equals(inputStudentId)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            studentList.get(index).setStudentName(scanner.nextLine());
            studentList.get(index).setBirthday(scanner.nextLine());
            studentList.get(index).setSex(Boolean.parseBoolean(scanner.nextLine()));
            studentList.get(index).setMark_html(Float.parseFloat(scanner.nextLine()));
            studentList.get(index).setMark_css(Float.parseFloat(scanner.nextLine()));
            studentList.get(index).setMark_js(Float.parseFloat(scanner.nextLine()));
        } else {
            System.err.println("Không tồn tại mã sinh viên [ " + inputStudentId + " ] trong danh sách.");
        }
    }

    // tìm kiếm tương đối theo tên sinh viên
    public static void searchStudentName() {
        System.out.print("Nhập tên cần tìm: ");
        String searchName = scanner.nextLine();
        for (Student student : studentList) {
            if (student.getStudentName().toLowerCase().contains(searchName.toLowerCase())) {
                System.out.println("************************************** Student List **************************************");
                System.out.printf("%-15s%-25s%-25s%-10s%-15s%-15s%-15s%-15s%-15s%-20s\n", "Student ID", "Student Name", "Date of Birth", "Age", "Sex", "HTML Score", "CSS Score", "JS Score", "Average Score", "Rank");
                student.displayData();
                System.out.println("******************************************************************************************");
            } else {
                System.out.println("************************************** Student List **************************************");
                System.err.println("Không tìm thấy [ " + searchName + " ] sinh viên trong danh sách.");
                System.out.println("******************************************************************************************");
            }
        }
    }

    // ghi file
    private static void writeDataToFile(String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName, true)) { // true: để ghi chèn tiếp data, false: để ghi đè
            for (Student student : studentList) {
                String studentInfo = student.getStudentId() + "," + student.getStudentName() + "," + student.getBirthday() + "," + student.getAge() + "," + student.isSex() + "," + student.getMark_html() + "," + student.getMark_css() + "," + student.getMark_js() + "," + student.getAvgMark() + "," + student.getRank() + "\n";
                fos.write(studentInfo.getBytes());
            }
            System.out.println("Ghi thông tin sinh viên vào file thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    // đọc file
    private static List<Student> readFromFileAndPrintInRange(String fileName) {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            StringBuilder content = new StringBuilder();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                content.append(new String(buffer, 0, bytesRead));
            }

            String[] lines = content.toString().split("\n");
            System.out.println("************************************** Student List **************************************");
            for (String line : lines) {
                String[] parts = line.split(",");
                String studentId = parts[0];
                String studentName = parts[1];
                String birthDate = parts[2];
                int age = Integer.parseInt(parts[3]);
                boolean sex = Boolean.parseBoolean(parts[4]);
                Float score_HTML = Float.parseFloat(parts[5]);
                Float score_CSS = Float.parseFloat(parts[6]);
                Float score_JS = Float.parseFloat(parts[7]);
                Float score_Avg = Float.parseFloat(parts[8]);
                String rank = parts[9];

                System.out.printf("%-15s%-25s%-25s%-10s%-15s%-15s%-15s%-15s%-15s%-20s\n", "Student ID", "Student Name", "Date of Birth", "Age", "Sex", "HTML Score", "CSS Score", "JS Score", "Average Score", "Rank");
                System.out.println("******************************************************************************************");
            }
        } catch (IOException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return null;
    }
}
