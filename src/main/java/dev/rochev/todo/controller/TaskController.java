package dev.rochev.todo.controller;

import dev.rochev.todo.domain.Task;
import dev.rochev.todo.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String getTasks(Model model,
                               @RequestParam(value = "page",required = false, defaultValue = "1") int page,
                               @RequestParam(value = "limit",required = false,defaultValue = "10")int limit) {
        List<Task> tasks =  taskService.getAll((page-1)*limit, limit);
        model.addAttribute("tasks", tasks);

        return "tasks";
    }

    @PostMapping("/{id}")
    public void editTask(@PathVariable int id, Model model,@RequestBody TaskInfo taskInfo) {
        if(isNull(id) || id <=0){
            throw new IllegalArgumentException("Task id is invalid");
        }

        Task task =  taskService.edit(id,taskInfo.getDescription(),taskInfo.getStatus());
    }

    @PostMapping("/")
    public void add(Model model, @RequestBody TaskInfo taskInfo) {
        taskService.create(taskInfo.getDescription(),taskInfo.getStatus());
    }

    @DeleteMapping("/{id}")
    public String  deleteTask(Model model,@PathVariable int id) {
        return "tasks";
    }
}
