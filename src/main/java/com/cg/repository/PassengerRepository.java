package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cg.entity.Passenger;
import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

    List<Passenger> findByBooking_BookingId(int bookingId);
}