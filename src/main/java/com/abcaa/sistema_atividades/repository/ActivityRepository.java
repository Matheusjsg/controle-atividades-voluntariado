package com.abcaa.sistema_atividades.repository;

import com.abcaa.sistema_atividades.domain.entity.Activity;
import com.abcaa.sistema_atividades.domain.enums.ActivityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface ActivityRepository extends JpaRepository <Activity, Long> {
    
List<Activity> findByVolunteerId(Long volunteerId);
List<Activity> findByActivityStatus(ActivityStatus status);
List<Activity> findByVolunteerIdAndActivityStatus(Long volunteerId, ActivityStatus status);
List<Activity> findByVolunteerIdAndActivityStatusAndDateBetween(Long volunteerId, ActivityStatus status, LocalDate startDate, LocalDate endDate);
List<Activity> findByVolunteerIdAndActivityStatusAndDateGreaterThanEqual(Long volunteerId, ActivityStatus status, LocalDate startDate);
List<Activity> findByVolunteerIdAndActivityStatusAndDateLessThanEqual(Long volunteerId, ActivityStatus status, LocalDate endDate);


    Page<Activity> findAll(Pageable pageable);
    Page<Activity> findByVolunteerId(Long volunteerId, Pageable pageable);
    Page<Activity> findByActivityStatus(ActivityStatus status, Pageable pageable);



// QUERY Soma mensal em lote (usada no findAll de voluntários)
// Retorna [volunteerId, totalMinutes] para uma lista de IDs.
// Estratégia: 1 query para toda a página → evita N queries.
    @Query("""
    SELECT a.volunteer.id, SUM(a.durationMinutes)
    FROM Activity a
    WHERE a.volunteer.id IN :volunteerIds
      AND a.activityStatus = 'APPROVED'
      AND MONTH(a.date) = :month
      AND YEAR(a.date)  = :year
    GROUP BY a.volunteer.id
    """)
    List<Object[]> findMonthlyMinutesByVolunteerIds(
            @Param("volunteerIds") List<Long> volunteerIds,
            @Param("month") int month,
            @Param("year") int year
    );



// QUERY Soma mensal para um único voluntário (usada no findById)
// Retorna Integer ou null se não houver atividades no mês.
// ─────────────────────────────────────────────────────────────
    @Query("""
    SELECT SUM(a.durationMinutes)
    FROM Activity a
    WHERE a.volunteer.id  = :volunteerId
      AND a.activityStatus = 'APPROVED'
      AND MONTH(a.date)   = :month
      AND YEAR(a.date)    = :year
    """)
    Integer findMonthlyMinutesByVolunteerId(
            @Param("volunteerId") Long volunteerId,
            @Param("month") int month,
            @Param("year") int year
    );




// QUERY: Ranking — agrega horas por voluntário no período
// startDate e endDate são opcionais:
//   - null em ambos  → acumulado geral
//   - só startDate   → a partir daquela data
//   - só endDate     → até aquela data
//   - ambos          → intervalo fechado
// Resultado já vem ordenado do maior para o menor (DESC).
    @Query("""
    SELECT a.volunteer.id,
           v.name,
           d.name,
           SUM(a.durationMinutes),
           COUNT(a)
    FROM Activity a
    JOIN a.volunteer v
    JOIN v.department d
    WHERE a.activityStatus = 'APPROVED'
      AND a.date >= COALESCE(:startDate, a.date)
      AND a.date <= COALESCE(:endDate, a.date)
    GROUP BY a.volunteer.id, v.name, d.name
    ORDER BY SUM(a.durationMinutes) DESC
    """)
    List<Object[]> findRanking(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );





}
