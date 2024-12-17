package org.roleonce.examensarbete_3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Size(min = 5, max = 30, message = "Title must have at least 5 chars")
    private String title;
    private int price;
    @NotBlank
    @Size(min = 10, max = 120, message = "Description must be between 10-120 chars")
    private String description;
    private String type;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "listing_images", joinColumns = @JoinColumn(name = "listing_id"))
    @Lob
    @Column(name = "image")
    private List<byte[]> images;

    private List<String> base64Image;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private CustomUser owner;

    public Listing() {

    }
    public Listing(String title, int price, String description, String type, List<byte[]> images) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.type = type;
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> image) {
        this.images = image;
    }

    public List<String> getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(List<String> base64Image) {
        this.base64Image = base64Image;
    }

    public CustomUser getOwner() {
        return owner;
    }

    public void setOwner(CustomUser customUser) {
        this.owner = customUser;
    }

}
