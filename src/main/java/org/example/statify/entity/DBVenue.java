package org.example.statify.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.statify.model.VenueModel;

@Entity
@Getter
@Setter
@Table(name = "venues")
@Accessors(chain = true)
public class DBVenue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "api_id", unique = true, nullable = false)
  private Integer apiId;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "address")
  private String address;

  @Column(name = "city")
  private String city;

  @Column(name = "country")
  private String country;

  @Column(name = "capacity")
  private Integer capacity;

  @Column(name = "surface")
  private String surface;

  @Column(name = "image")
  private String image;

  public static DBVenue fromVenueIdOnly(VenueModel venueModel) {
    return new DBVenue().setId(venueModel.getId());
  }
}
