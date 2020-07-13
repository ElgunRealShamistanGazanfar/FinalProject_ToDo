package app.service;


import app.entity.Task;
import app.exception.TaskNotFoundEx;
import app.repo.TaskRepo;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TaskService {

    @Autowired
    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public Collection<Task> fetchAll() {
        return taskRepo.findAll();
    }

    public Optional<Task> findTaskById(int id) {

        return taskRepo.findById(id);
    }

    public void addTask(Task task) {
        taskRepo.save(task);
    }

    public Boolean isDone(int task_id) {
        Optional<Task> byId = this.findTaskById(task_id);
        return byId.orElse(new Task(false)).getComplement_status();

    }

    public static Boolean isOverdue(LocalDate curr, Date deadline) {
        Date curr_date = java.sql.Date.valueOf(curr);
        return curr_date.compareTo(deadline) > 0;
    }

    public List<Task> done() {
        return fetchAll().stream().filter(e -> isDone(e.getId())).collect(Collectors.toList());
    }

    public List<Task> overdue() {
        return fetchAll().stream().filter(e -> isOverdue(LocalDate.now(), e.getDeadline())).collect(Collectors.toList());

    }

    public List<Task> today() {
        return fetchAll().stream().filter(e -> e.getDeadline().equals(java.sql.Date.valueOf(LocalDate.now()))).collect(Collectors.toList());
    }

    public void deleteTask(int id) {
            taskRepo.deleteById(id);
    }

    public void addToimportant(int id) {
        Task task = findTaskById(id).orElse(new Task(false));
        task.setStatus("important");
        taskRepo.deleteById(id);
        taskRepo.save(task);


    }

    public List<Task> important() {
        List<Task> imp = taskRepo.findAll().stream().filter(t -> t.getStatus().equals("important")).collect(Collectors.toList());
        if (imp.isEmpty()){
            imp.add(0,new Task(false));
        }
        return imp;
    }

    @SneakyThrows
    public void createImage(byte[] data) {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage bImage2 = ImageIO.read(bis);
        ImageIO.write(bImage2, "jpg", new File("output.jpg") );
        log.info("image created");
    }



    // Save image to a DB
    @SneakyThrows
    public void addImageAndSaveToDB(Task task, MultipartFile imageFile) {
        try {
            byte [] byteObj = new byte[imageFile.getBytes().length];
            int i = 0;
            for (byte b : imageFile.getBytes()) {
                byteObj[i++] = b;
            }
                task.setImage(byteObj);
                taskRepo.save(task);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void updateTitleAndDate(int id, java.sql.Date edited_date, String edited_title) {

        Optional<Task> taskById = findTaskById(id);
        taskRepo.deleteById(id);
        Task tsk  = taskById.get();
        tsk.setTitle(edited_title);
        tsk.setDeadline(edited_date);
        taskRepo.save(tsk);
    }
}
