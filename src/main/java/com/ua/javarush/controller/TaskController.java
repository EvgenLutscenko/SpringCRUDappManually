package com.ua.javarush.controller;

import com.ua.javarush.domain.Task;
import com.ua.javarush.dto.TaskInfo;
import com.ua.javarush.exceprion.InvalidRequestArgumentException;
import com.ua.javarush.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/")
    public String tasks(Model model,
                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "limit", required = false, defaultValue = "10") int limit){
        List<Task> tasks = taskService.getAll((page - 1) * limit, limit);
        model.addAttribute("tasks", tasks);
        model.addAttribute("current_page", page);
        int pages = (int) Math.ceil(1.0 * taskService.getAllCount() / limit);

        if(pages > 1){
            List<Integer> list = IntStream.rangeClosed(1, pages).boxed().toList();
            model.addAttribute("page_numbers", list);
        }

        return "tasks";
    }

    @PostMapping("/{id}")
    public void edit(Model model,
                     @PathVariable("id") Integer id,
                     @RequestBody TaskInfo taskInfo){

        if(id == null || id <= 0){
            throw new InvalidRequestArgumentException("Argument id is invalid");
        }

        Task task = taskService.edit(id, taskInfo.getDescription(), taskInfo.getStatus());

    }

    @PutMapping("/")
    public void add(Model model,
                    @RequestBody TaskInfo taskInfo){
        Task task = taskService.create(taskInfo.getDescription(), taskInfo.getStatus());
    }

    @DeleteMapping("/{id}")
    public String delete(Model model,
                         @PathVariable("id") Integer id){

        if(id == null || id <= 0){
            throw new InvalidRequestArgumentException("Argument id is invalid");
        }

        taskService.delete(id);
        return "tasks";
    }
}
