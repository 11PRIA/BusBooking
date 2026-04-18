package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cg.dto.BookingRequest;
import com.cg.entity.*;
import com.cg.service.BookingService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService service;

    // ✅ LOGIN
    @PostMapping("/login")
    public Customer login(@RequestBody Customer c) {
        return service.login(c.getEmail(), c.getPassword());
    }

    // ✅ SEARCH SCHEDULE
    @GetMapping("/schedule")
    public List<Schedule> search(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam String date) {

        return service.searchSchedule(source, destination, LocalDate.parse(date));
    }

    // ✅ BOOK SEATS
    @PostMapping("/book")
    public Booking book(@RequestBody BookingRequest request) {
        return service.bookSeats(
                request.getCustomerId(),
                request.getScheduleId(),
                request.getPassengers()
        );
    }

    // ✅ VIEW BOOKINGS
    @GetMapping("/bookings/{customerId}")
    public List<Booking> getBookings(@PathVariable int customerId) {
        return service.getBookingsByCustomer(customerId);
    }

    // ✅ VIEW PASSENGERS
    @GetMapping("/passengers/{bookingId}")
    public List<Passenger> getPassengers(@PathVariable int bookingId) {
        return service.getPassengersByBooking(bookingId);
    }
}
