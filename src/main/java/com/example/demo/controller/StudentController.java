package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List; // Thư viện quan trọng để dùng List

@Controller
public class StudentController {

    @Autowired
    private StudentRepository repo;

    // 1. Trang chủ: Hiển thị danh sách sinh viên
    @GetMapping("/")
    public String viewHomePage(Model model) {
        // Mặc định hiển thị tất cả sinh viên
        model.addAttribute("listStudents", repo.findAll());
        return "index";
    }

    // 2. Chức năng Tìm kiếm
    @GetMapping("/search")
    public String searchStudent(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Student> listStudents;

        if (keyword != null && !keyword.isEmpty()) {
            // Nếu có từ khóa -> Tìm trong database
            listStudents = repo.findByNameContainingIgnoreCase(keyword);
        } else {
            // Nếu từ khóa rỗng -> Lấy tất cả
            listStudents = repo.findAll();
        }

        model.addAttribute("listStudents", listStudents);
        model.addAttribute("keyword", keyword); // Gửi lại từ khóa để hiện lên ô input
        return "index";
    }

    // 3. Hiện form thêm mới
    @GetMapping("/new")
    public String showNewStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "new_student";
    }

    // 4. Lưu (Dùng chung cho cả Thêm mới và Sửa)
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") Student student) {
        // === LOGIC TẠO ID NGẪU NHIÊN 4 SỐ ===
        // Nếu ID chưa có (hoặc bằng 0) nghĩa là đang Thêm mới
        if (student.getId() == null || student.getId() == 0) {
            // Tạo số ngẫu nhiên từ 1000 đến 9999
            long randomId = (long) (Math.random() * 9000) + 1000;
            student.setId(randomId);
        }
        // ======================================

        repo.save(student);
        return "redirect:/";
    }

    // 5. Hiện form sửa
    @GetMapping("/edit/{id}")
    public String showEditStudentForm(@PathVariable("id") Long id, Model model) {
        // Tìm sinh viên theo ID, dùng orElse(null) để tránh lỗi nếu không tìm thấy
        Student student = repo.findById(id).orElse(null);
        model.addAttribute("student", student);

        // Trả về file giao diện sửa (bạn cần đảm bảo có file edit_student.html hoặc
        // dùng chung new_student)
        return "new_student"; // Mình trỏ tạm về new_student để tái sử dụng form
    }

    // 6. Xóa
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable(name = "id") Long id) {
        repo.deleteById(id);
        return "redirect:/";
    }
}