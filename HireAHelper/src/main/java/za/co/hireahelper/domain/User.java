/* User.java
   Author: S Hendricks (221095136)
   Date: 18 May 2025
*/

package za.co.hireahelper.domain;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    private String userId;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String mobileNumber;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "area_id", referencedColumnName = "areaId", nullable = false)
    private Area area;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;  // system-managed, not user-chosen

    protected User() {}

    protected User(Builder<?> builder) {
        this.userId = builder.userId;
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.mobileNumber = builder.mobileNumber;
        this.area = builder.area;
        this.role = builder.role;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getMobileNumber() { return mobileNumber; }
    public Area getArea() { return area; }
    public Role getRole() { return role; }

    //SETTER for password (to enable hashing)
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", area=" + getArea() +
                ", role=" + role +
                '}';
    }

    // Enum for roles
    public enum Role {
        CLIENT,
        SERVICE_PROVIDER,
        ADMIN
    }

    // Generic builder pattern for subclassing
    public static abstract class Builder<T extends Builder<T>> {
        private String userId;
        private String name;
        private String email;
        private String password;
        private String mobileNumber;
        private Area area;
        private Role role;

        public T setUserId(String userId) { this.userId = userId; return self(); }
        public T setName(String name) { this.name = name; return self(); }
        public T setEmail(String email) { this.email = email; return self(); }
        public T setPassword(String password) { this.password = password; return self(); }
        public T setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; return self(); }
        public T setArea(Area area) { this.area = area; return self(); }
        public T setRole(Role role) { this.role = role; return self(); }

        public T copy(User user) {
            this.userId = user.userId;
            this.name = user.name;
            this.email = user.email;
            this.password = user.password;
            this.mobileNumber = user.mobileNumber;
            this.area = user.area;
            this.role = user.role;
            return self();
        }

        protected abstract T self();

        public abstract User build();
    }
}
