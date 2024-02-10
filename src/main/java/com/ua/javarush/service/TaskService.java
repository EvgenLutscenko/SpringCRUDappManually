package com.ua.javarush.service;

import com.ua.javarush.dao.TaskDAO;
import com.ua.javarush.domain.Status;
import com.ua.javarush.domain.Task;
import com.ua.javarush.exceprion.TaskNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskDAO taskDAO;

    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public List<Task> getAll(int offset, int limit){
        return taskDAO.getAll(offset, limit);
    }

    public int getAllCount(){
        return taskDAO.getAllCount();
    }

    @Transactional
    public Task edit(int id, String description, Status status){
        Task task = taskDAO.getById(id).
                orElseThrow(()-> new TaskNotFoundException("task with id -" + id + " is not found"));

        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdate(task);
        return task;
    }

    public Task create(String description, Status status){
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdate(task);
        return task;
    }

    @Transactional
    public void delete(int id){
        Task task = taskDAO.getById(id).
                orElseThrow(()-> new TaskNotFoundException("task with id -" + id + " is not found"));

        taskDAO.delete(task);
    }
}

