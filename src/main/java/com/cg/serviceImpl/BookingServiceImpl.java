package com.cg.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.*;
import com.cg.repository.*;
import com.cg.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private ScheduleRepository scheduleRepo;

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private PassengerRepository passengerRepo;

    @Override
    public Customer login(String email, String password) {
        return customerRepo.findByEmailAndPassword(email, password);
    }

    @Override
    public List<Schedule> searchSchedule(String source, String destination, LocalDate date) {
        return scheduleRepo.findByRoute_SourceAndRoute_DestinationAndTravelDate(source, destination, date);
    }
}