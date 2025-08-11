// Ameeruddin Arai  student number: 230190839

package za.co.hireahelper.domain;

import jakarta.persistence.*;

@Table(name = "areas")
@Entity
public class Area {

    @Id
    private String areaId;

    private String name;

    protected Area() {
    }

    private Area(Builder builder) {
        this.areaId = builder.areaId;
        this.name = builder.name;
    }

    public String getAreaId() {
        return areaId;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "Area{" +
                "areaId='" + areaId + '\'' +
                ", name='" + name + '\'' +
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


        public Builder copy(Area area) {
            this.areaId = area.areaId;
            this.name = area.name;
            return this;
        }

        public Area build() {
            return new Area(this);
        }
    }
}
