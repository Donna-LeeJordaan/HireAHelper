package za.co.hireahelper.domain;

import jakarta.persistence.*;

@Entity
public class Area {

    @Id
    private String areaId;

    private String name;

    @OneToOne(mappedBy = "area", fetch = FetchType.LAZY)
    private User user;

    protected Area() {
    }

    private Area(Builder builder) {
        this.areaId = builder.areaId;
        this.name = builder.name;
        this.user = builder.user;
    }

    public String getAreaId() {
        return areaId;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Area{" +
                "areaId='" + areaId + '\'' +
                ", name='" + name + '\'' +
                ", user=" + (user != null ? user.getUserId() : "null") +
                '}';
    }

    public static class Builder {
        private String areaId;
        private String name;
        private User user;

        public Builder setAreaId(String areaId) {
            this.areaId = areaId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder copy(Area area) {
            this.areaId = area.areaId;
            this.name = area.name;
            this.user = area.user;
            return this;
        }

        public Area build() {
            return new Area(this);
        }
    }
}
