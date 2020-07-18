package epg.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "channels")
public class Channel {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private Integer position;
    private String category;

    public Channel() {
    }

    public Channel(Builder builder) {
        this.name = builder.name;
        this.position = builder.position;
        this.category = builder.category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static class Builder {
        private String name;
        private Integer position;
        private String category;

        public static Builder channelWith() {
            return new Builder();
        }

        public Builder withName(String name) {

            this.name = name;

            return this;
        }

        public Builder withPosition(Integer position) {
            this.position = position;

            return this;
        }
        
        public Builder withCategory(String category) {
            this.category = category;

            return this;
        }

        public Channel build() {
            return new Channel(this);
        }
    }
}
