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

    @Override
    public Booking bookSeats(int customerId, int scheduleId, List<Passenger> passengers) {

        Customer customer = customerRepo.findById(customerId).orElseThrow();
        Schedule schedule = scheduleRepo.findById(scheduleId).orElseThrow();

        if (schedule.getAvailableSeats() < passengers.size()) {
            throw new RuntimeException("Seats not available");
        }

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setSchedule(schedule);
        booking.setBookingDate(LocalDateTime.now());
        booking.setTotalAmount(passengers.size() * schedule.getPrice());

        // Set booking reference in passengers
        for (Passenger p : passengers) {
            p.setBooking(booking);
        }

        booking.setPassengers(passengers);

        // Save booking (cascade saves passengers)
        Booking savedBooking = bookingRepo.save(booking);

        // Update seats
        schedule.setAvailableSeats(schedule.getAvailableSeats() - passengers.size());
        scheduleRepo.save(schedule);

        return savedBooking;
    }

    @Override
    public List<Booking> getBookingsByCustomer(int customerId) {
        return bookingRepo.findByCustomer_CustomerId(customerId);
    }

    @Override
    public List<Passenger> getPassengersByBooking(int bookingId) {
        return passengerRepo.findByBooking_BookingId(bookingId);
    }
}
