package app.controller;


import app.entity.Task;
import app.service.RegisterService;
import app.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Optional;

@Log4j2
@Controller
@RequestMapping("/")
public class DashController {

    private final TaskService taskService;
    private final RegisterService registerService;

    public DashController(TaskService taskService, RegisterService registerService) {
        this.taskService = taskService;
        this.registerService = registerService;
    }

    @GetMapping("delete/{id}")
    public RedirectView delete_task(@PathVariable int id){
        log.info(String.format("GET -> deleting task with id: %d", id));
        taskService.deleteTask(id);
        return new RedirectView("/tasks-dashboard");

    }

    @GetMapping("add/{id}")
    public RedirectView add_important(@PathVariable int id, Model model){
        taskService.addToimportant(id);
        log.info(String.format("Element with id %d added to importants", id));
        return new RedirectView("/tasks-dashboard");

    }

    @GetMapping("show/{id}")
    public void showImageDB(@PathVariable("id") Integer taskId, HttpServletResponse response) throws IOException {

        Optional<Task> res = taskService.findTaskById(taskId);

        if (res.isPresent()) {
            Task task = res.get();

            if (task.getImage() != null) {
                byte[] byteArray = new byte[task.getImage().length];

                int i = 0;
                for (Byte imgByte : task.getImage()) {
                    byteArray[i++] = imgByte;
                }
                response.setContentType("image/jpeg");
                InputStream is = new ByteArrayInputStream(byteArray);
                IOUtils.copy(is, response.getOutputStream());
            }
        }
    }




    @PostMapping("edit")
    public RedirectView edit_post(@RequestParam("name-task")String edited_title,
                                  @RequestParam("task-card-date") Date edited_date,
                                  @RequestParam("unvisible_id")int id
                                  ){
        taskService.updateTitleAndDate(id, edited_date, edited_title);

        log.info(String.format("Edited id %d, title %s ", id, edited_title));
        return new RedirectView("/tasks-dashboard");

    }



}
