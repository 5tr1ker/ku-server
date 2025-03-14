package com.team.saver.admin.statistics.service;

import com.team.saver.admin.statistics.dto.AdminTodoResponse;
import com.team.saver.admin.statistics.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public AdminTodoResponse findAdminTodo() {
        return statisticsRepository.findAdminTodo();
    }

}
