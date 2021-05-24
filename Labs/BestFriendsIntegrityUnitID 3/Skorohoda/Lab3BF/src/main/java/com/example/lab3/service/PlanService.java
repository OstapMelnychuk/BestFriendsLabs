package com.example.lab3.service;

import com.example.lab3.dto.PlanRequestDto;
import com.example.lab3.entity.Plan;
import com.example.lab3.exception.NotFoundException;
import com.example.lab3.exception.WorkerIsAlreadyBusyException;
import com.example.lab3.repository.PlanRepository;
import com.example.lab3.repository.RequestRepository;
import com.example.lab3.repository.WorkerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanService {
    private final PlanRepository planRepository;
    private final RequestRepository requestRepository;
    private final WorkerRepository workerRepository;
    private final WorkerService workerService;

    public PlanService(PlanRepository planRepository, RequestRepository requestRepository,
                       WorkerRepository workerRepository, WorkerService workerService) {
        this.planRepository = planRepository;
        this.requestRepository = requestRepository;
        this.workerRepository = workerRepository;
        this.workerService = workerService;
    }

    public List<Plan> findAll() {
        return planRepository.findAll();
    }

    public Plan findById(Long id) {
        if (!planRepository.existsById(id)) {
            throw new NotFoundException("There is no such plan with id: " + id);
        }
        return planRepository.findById(id).get();
    }

    public Long createPlan(PlanRequestDto planRequestDto) {
        if (!requestRepository.existsById(planRequestDto.getRequestId())){
            throw new NotFoundException("There is no such request with id: " + planRequestDto.getRequestId());
        }

        for (Long workerId : planRequestDto.getWorkersId()) {
            if (!workerRepository.existsById(workerId)) {
                throw new NotFoundException("There is no such worker with id: " + workerId);
            }
        }

        Plan plan = new Plan();
        plan.setRequest(requestRepository.findById(planRequestDto.getRequestId()).get());
        plan.setWorkers(new ArrayList<>());
        for (Long workerId : planRequestDto.getWorkersId()) {
            if (workerRepository.findById(workerId).get().isBusy()){
                throw new WorkerIsAlreadyBusyException("Worker with id: " + workerId + " is already busy");
            }
            workerService.changeStatus(workerId, true);
            plan.getWorkers().add(workerRepository.findById(workerId).get());
        }

        planRepository.save(plan);
        return plan.getId();
    }

    public Long updatePlan(Long planId, PlanRequestDto planRequestDto) {
        if (!planRepository.existsById(planId)) {
            throw new NotFoundException("There is no such plan with id: " + planId);
        }
        if (!requestRepository.existsById(planRequestDto.getRequestId())){
            throw new NotFoundException("There is no such request with id: " + planRequestDto.getRequestId());
        }
        for (Long workerId : planRequestDto.getWorkersId()) {
            if (!workerRepository.existsById(workerId)) {
                throw new NotFoundException("There is no such worker with id: " + workerId);
            }
        }

        Plan plan = new Plan();
        plan.setId(planId);
        plan.setRequest(requestRepository.findById(planRequestDto.getRequestId()).get());
        plan.setWorkers(new ArrayList<>());
        for (Long workerId : planRequestDto.getWorkersId()) {
            if (workerRepository.findById(workerId).get().isBusy()){
                throw new WorkerIsAlreadyBusyException("Worker with id: " + workerId + " is already busy");
            }
            workerService.changeStatus(workerId, true);
            plan.getWorkers().add(workerRepository.findById(workerId).get());
        }

        planRepository.save(plan);
        return plan.getId();
    }

    public void deleteById(Long id) {
        if (!planRepository.existsById(id)) {
            throw new NotFoundException("There is no such request with id: " + id);
        }
        planRepository.deleteById(id);
    }


    public Long changeStatus(Long id, boolean status) {
        if (requestRepository.existsById(id)) {
            throw new NotFoundException("There is no such request with id: " + id);
        }

        Plan plan = planRepository.findById(id).get();
        plan.setDone(status);
        planRepository.save(plan);
        return plan.getId();
    }
}
