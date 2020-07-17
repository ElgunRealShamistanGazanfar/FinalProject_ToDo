package app.controller;

import app.entity.Task;
import app.service.PaginationService;
import app.service.RegisterService;
import app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    RegisterService registerService;

    @Autowired
    PaginationService paginationService;

    @Autowired
    TaskService taskService;


    @GetMapping("/table/paged")
    public String getPaged(Model model, Pageable pageable) {
      Page<Task> pageForToday = paginationService.pageForToday(pageable);
       model.addAttribute("tasks",pageForToday);
        return "tasks-dashboard";
    }



}
