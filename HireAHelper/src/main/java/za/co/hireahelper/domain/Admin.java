// Ameeruddin Arai  student number: 230190839

package za.co.hireahelper.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin extends User {

    protected Admin() {
        super();
    }

    private Admin(Builder builder) {
        super(builder);
    }

    @Override
    public String toString() {
        return "Admin{" + super.toString() + "}";
    }

    public static class Builder extends User.Builder<Builder> {

        public Builder() {
            this.setRole(Role.ADMIN); //  automatically assign ADMIN role
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public Admin build() {
            return new Admin(this);
        }

        public Builder copy(Admin admin) {
            super.copy(admin);
            return this;
        }
    }
}
