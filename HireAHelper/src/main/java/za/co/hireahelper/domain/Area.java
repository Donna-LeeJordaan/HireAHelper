//Ameeruddin Arai student no: 230190839

package za.co.hireahelper.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "areas")
public class Area {

    @Id
    private String areaId;
    private String name;

    // Optional: Link to service providers in this area
    @OneToMany(mappedBy = "area")
    private List<ServiceProvider> serviceProviders;

    protected Area() {
        // Default constructor for JPA
    }

    private Area(Builder builder) {
        this.areaId = builder.areaId;
        this.name = builder.name;
        this.serviceProviders = builder.serviceProviders;
    }

    public String getAreaId() {
        return areaId;
    }

    public String getName() {
        return name;
    }

    public List<ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }

    @Override
    public String toString() {
        return "Area{" +
                "areaId='" + areaId + '\'' +
                ", name='" + name + '\'' +
                ", serviceProviders=" + serviceProviders +
                '}';
    }

    public static class Builder {
        private String areaId;
        private String name;
        private List<ServiceProvider> serviceProviders;

        public Builder setAreaId(String areaId) {
            this.areaId = areaId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setServiceProviders(List<ServiceProvider> serviceProviders) {
            this.serviceProviders = serviceProviders;
            return this;
        }

        public Builder copy(Area area) {
            this.areaId = area.areaId;
            this.name = area.name;
            this.serviceProviders = area.serviceProviders;
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
