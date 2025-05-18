/* User.java

   Author: S Hendricks (221095136)

   Date: 18 May 2025 */

package za.co.hireahelper.domain;

public abstract class User {

    private String userId;
    private String name;
    private String email;
    private String password;
    private String mobileNumber;

    protected User() {
    }

    protected User(Builder<?> builder) {     //Builder<?> necessary to make the constructor flexible enough to work with any subclass.
        this.userId = builder.userId;
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.mobileNumber = builder.mobileNumber;
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

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
    }

    public static abstract class Builder<T extends Builder<T>> {  //Generic <T> pattern in the abstract User is better for inheritance and fluent chaining in subclasses.
        private String userId;
        private String name;
        private String email;
        private String password;
        private String mobileNumber;

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

        public T copy(User user) {
            this.userId = user.userId;
            this.name = user.name;
            this.email = user.email;
            this.password = user.password;
            this.mobileNumber = user.mobileNumber;
            return self();
        }

        protected abstract T self();

        public abstract User build();
    }
}

