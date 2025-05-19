package za.co.hireahelper.domain;

//Ameeruddin Arai  student number:230190839



public class Admin extends User {

    private Admin() {
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
