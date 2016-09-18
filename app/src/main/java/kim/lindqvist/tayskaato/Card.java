package kim.lindqvist.tayskaato;

public class Card {

    private String Title, Description, Image;

    public String getTitle() {
        return this.Title;
    }

    public String getDescription() {
        return this.Description;
    }

    public String getImage() {
        return this.Image;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public void setImage(String image) {
        this.Image = image;
    }
}
