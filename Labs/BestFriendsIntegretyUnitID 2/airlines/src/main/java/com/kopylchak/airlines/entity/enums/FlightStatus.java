package com.kopylchak.airlines.entity.enums;

import com.kopylchak.airlines.entity.Flight;
import java.util.Arrays;
import java.util.List;

/**
 * Statuses for {@link Flight}.
 * <p>
 * CREATED - created by Administrator
 * PLANNED - after determining crew by Dispatcher
 * ONGOING - for flights, that are happening now
 * COMPLETED - flight successfully finished
 * POSTPONED - flight was postponed
 * CANCELED - flight was canceled
 */
public enum FlightStatus {
    CREATED,
    PLANNED,
    ONGOING,
    COMPLETED,
    POSTPONED,
    CANCELED;

    public static final List<FlightStatus> UPDATABLE_STATUSES =
        Arrays.asList(ONGOING, COMPLETED, POSTPONED, CANCELED);
}
