package org.roleonce.examensarbete_3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Size(min = 5, max = 30, message = "Title must have at least 5 chars")
    private String title;
    @NotBlank
    private int price;
    @NotBlank
    @Size(min = 10, max = 120, message = "Description must be between 10-120 chars")
    private String description;
    private String type;
    @Lob
    private byte[] image;
    private String base64Image;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private CustomUser owner;

    public Listing() {

    }
    public Listing(String title, int price, String description, String type, byte[] image) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.type = type;
        this.image = image;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public CustomUser getOwner() {
        return owner;
    }

    public void setOwner(CustomUser customUser) {
        this.owner = customUser;
    }

}
