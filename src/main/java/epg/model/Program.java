package epg.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "programs")
public class Program {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull
    private String channelId;

    @NotNull
    private String imageUrl;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime startTime;

    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime endTime;

    public Program() {
    }

    public Program(Builder builder) {
        this.channelId = builder.channelId;
        this.imageUrl = builder.imageUrl;
        this.title = builder.title;
        this.description = builder.description;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
    }

    public String getId() {
        return id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public static class Builder {
        private String channelId;
        private String imageUrl;
        private String title;
        private String description;
        private LocalDateTime startTime;
        private LocalDateTime endTime;

        public static Builder programWith() {
            return new Builder();
        }

        public Builder withChannelId(String channelId) {
            this.channelId = channelId;

            return this;
        }

        public Builder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;

            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;

            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;

            return this;
        }

        public Builder withStartTime(LocalDateTime startTime) {
            this.startTime = startTime;

            return this;
        }

        public Builder withEndTime(LocalDateTime endTime) {
            this.endTime = endTime;

            return this;
        }

        public Program build() {
            return new Program(this);
        }
    }
}
