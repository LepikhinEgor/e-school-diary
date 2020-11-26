package com.scw.electronicgradebook.dao;

import com.scw.electronicgradebook.domain.entities.Attendance;

import java.util.Optional;

public interface AttendanceRepository {

    void create(Attendance attendance);

    void update(Attendance attendance);

    void delete(Attendance attendance);

    Optional<Attendance> getById(Long id);
}
