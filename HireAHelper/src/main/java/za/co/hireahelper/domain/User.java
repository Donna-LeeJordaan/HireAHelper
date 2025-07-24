/* User.java

   Author: S Hendricks (221095136)

   Date: 18 May 2025, updated on 24 July 2025
*/

package za.co.hireahelper.domain;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class User {

    @Id
    private String userId;
    private String name;
    private String email;
    private String password;
    private String mobileNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "area_id", referencedColumnName = "areaId")
    private Area area;

    protected User() {
    }

    protected User(Builder<?> builder) {
        this.userId = builder.userId;
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.mobileNumber = builder.mobileNumber;
        this.area = builder.area;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public Area getArea() {
        return area;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", area=" + (area != null ? area.getName() : "null") +
                '}';
    }

    public static abstract class Builder<T extends Builder<T>> {
        private String userId;
        private String name;
        private String email;
        private String password;
        private String mobileNumber;
        private Area area;

        public T setUserId(String userId) {
            this.userId = userId;
            return self();
        }

        public T setName(String name) {
            this.name = name;
            return self();
        }

        public T setEmail(String email) {
            this.email = email;
            return self();
        }

        public T setPassword(String password) {
            this.password = password;
            return self();
        }

        public T setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return self();
        }

        public T setArea(Area area) {
            this.area = area;
            return self();
        }

        public T copy(User user) {
            this.userId = user.userId;
            this.name = user.name;
            this.email = user.email;
            this.password = user.password;
            this.mobileNumber = user.mobileNumber;
            this.area = user.area;
            return self();
        }

        protected abstract T self();

        public abstract User build();
    }
}
