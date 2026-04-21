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
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {

    @Autowired
    private BookingService service;

    @GetMapping("/schedule")
    public List<Schedule> search(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
            LocalDate date) {

        System.out.println("SOURCE: " + source);
        System.out.println("DEST: " + destination);
        System.out.println("DATE: " + date);

        return service.searchSchedule(source, destination, date);
    }

    @PostMapping("/booking")
    public Booking book(@RequestBody BookingRequest request) {
        return service.bookSeats(
                request.getCustomerId(),
                request.getScheduleId(),
                request.getPassengers()
        );
    }

    @GetMapping("/bookings/{customerId}")
    public List<Booking> getBookings(@PathVariable int customerId) {
        return service.getBookingsByCustomer(customerId);
    }

    @GetMapping("/passengers/{bookingId}")
    public List<Passenger> getPassengers(@PathVariable int bookingId) {
        return service.getPassengersByBooking(bookingId);
    }
}