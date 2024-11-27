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
@Table(name = "watching_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WatchingList {
    @EmbeddedId
    private WatchingListId id;

    @ManyToOne
    @MapsId("user_id")
    private User user;

    @ManyToOne
    @MapsId("movieId")
    private Movie movie;

    @Column(name = "added_at", nullable = false)
    private java.sql.Timestamp addedAt;
}
