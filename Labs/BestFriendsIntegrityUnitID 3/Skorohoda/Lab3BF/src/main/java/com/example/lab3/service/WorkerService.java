package com.example.lab3.service;

import com.example.lab3.dto.WorkerRequestDto;
import com.example.lab3.entity.Plan;
import com.example.lab3.entity.User;
import com.example.lab3.entity.Worker;
import com.example.lab3.exception.AlreadyRegisteredException;
import com.example.lab3.exception.NotFoundException;
import com.example.lab3.repository.UserRepository;
import com.example.lab3.repository.WorkerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;

@Service
public class WorkerService {
    private final WorkerRepository workerRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public WorkerService(WorkerRepository workerRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.workerRepository = workerRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public Long create(WorkerRequestDto workerRequestDto) {
        if (!userRepository.existsByEmail(workerRequestDto.getUserRequestDto().getEmail())) {
            throw new AlreadyRegisteredException("There is no registered user with email: "
                    + workerRequestDto.getUserRequestDto().getEmail());
        }
        Worker worker = modelMapper.map(workerRequestDto, Worker.class);
        worker.setUser(userRepository.findByEmail(workerRequestDto.getUserRequestDto().getEmail()).orElseThrow(() ->
                new NotFoundException("There is no such user with email " + workerRequestDto.getUserRequestDto()
                        .getEmail())));
        worker.setPlanList(new ArrayList<>());
        workerRepository.save(worker);
        return worker.getId();
    }

    public Long update(WorkerRequestDto workerRequestDto) {
        if (!userRepository.existsByEmail(workerRequestDto.getUserRequestDto().getEmail())) {
            throw new NotFoundException("There is no such worker with email: " + workerRequestDto.getUserRequestDto().getEmail());
        }

        Worker worker = workerRepository.findByUser(userRepository.findByEmail(workerRequestDto.getUserRequestDto().getEmail())
                .get()).orElseThrow(() -> new NotFoundException("There is no such worker"));
        worker.setName(workerRequestDto.getName());
        worker.setPhoneNumber(workerRequestDto.getPhoneNumber());
        worker.setSurname(workerRequestDto.getSurname());
        workerRepository.save(worker);
        return worker.getId();
    }

    public void delete(Long id) {
        if (!workerRepository.existsById(id)) {
            throw new NotFoundException("There is no such worker with id: " + id);
        }
    }

    public Worker findOneById(Long id) {
        if (!workerRepository.existsById(id)) {
            throw new NotFoundException("There is no such worker with id: " + id);
        }
        return workerRepository.findById(id).get();
    }

    public List<Worker> findAllFreeWorkers() {
        List<Worker> freeWorkers = new ArrayList<>();
        for (Worker worker : workerRepository.findAll()) {
            if (!worker.isBusy()) {
                freeWorkers.add(worker);
            }
        }
        return freeWorkers;
    }

    public List<Worker> findAll() {
        return workerRepository.findAll();
    }

    public List<Plan> findAllWorkerPlans(Long id) {
        if (!workerRepository.existsById(id)) {
            throw new NotFoundException("There is no such worker with id: " + id);
        }
        return workerRepository.findById(id).get().getPlanList();
    }

    public Long changeStatus(Long workerId, boolean status) {
        if (!workerRepository.existsById(workerId)) {
            throw new NotFoundException("There is no such worker with id: " + workerId);
        }

        Worker worker = workerRepository.findById(workerId).get();
        worker.setBusy(status);
        workerRepository.save(worker);
        return worker.getId();
    }
}
