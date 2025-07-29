// Gabriel Kiewietz
// 230990703
// 18 May 2024

package za.co.hireahelper.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class ServiceType {

    @Id
    private String typeId;

    private String typeName;

    @OneToMany(mappedBy = "serviceType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ServiceProvider> serviceProviders;

    protected ServiceType() {}

    private ServiceType(Builder builder) {
        this.typeId = builder.typeId;
        this.typeName = builder.typeName;
        this.serviceProviders = builder.serviceProviders;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public List<ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }

    @Override
    public String toString() {
        return "ServiceType{" +
                "typeId='" + typeId + '\'' +
                ", typeName='" + typeName + '\'' +
                ", serviceProviders=" + (serviceProviders != null ? serviceProviders.size() : 0) +
                '}';
    }

    public static class Builder {
        private String typeId;
        private String typeName;
        private List<ServiceProvider> serviceProviders;

        public Builder setTypeId(String typeId) {
            this.typeId = typeId;
            return this;
        }

        public Builder setTypeName(String typeName) {
            this.typeName = typeName;
            return this;
        }

        public Builder setServiceProviders(List<ServiceProvider> serviceProviders) {
            this.serviceProviders = serviceProviders;
            return this;
        }

        public Builder copy(ServiceType serviceType) {
            this.typeId = serviceType.typeId;
            this.typeName = serviceType.typeName;
            this.serviceProviders = serviceType.serviceProviders;
            return this;
        }

        public ServiceType build() {
            return new ServiceType(this);
        }
    }
}

