package org.senla.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "viewing_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewingHistory {
    @EmbeddedId
    private ViewingHistoryId id;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("movieId")
    private Movie movie;

    @Column(name = "watched_at", nullable = false)
    private java.sql.Timestamp watchedAt;

}

