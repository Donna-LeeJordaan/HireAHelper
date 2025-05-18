package za.co.hireahelper.domain;

//Ameeruddin Arai 230190839
import java.util.Objects;

public class Area extends User{

    private String areaId;
    private String name;

    private Area() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Area)) return false;
        Area area = (Area) o;
        return Objects.equals(areaId, area.areaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaId);
    }
}
